import java.util.Collections;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class GameStart {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static ArrayList<Boolean> chambers; // true for bullet, false for blank
    private static boolean playerTurn = random.nextBoolean(); // Randomly decide who starts
    private static int totalShotsFired = 0; //tracks the total shots

    public static void start() {
        setupGame();
        while (true) {
            System.out.println((playerTurn ? "\nPLAYER'S" : "\nAI'S") + " TURN.\n");
            if (takeTurn()) {
                System.out.println((playerTurn ? "\nPlayer" : "\nAI") + " loses!\n");
                Ascii.gameOver();
                break;
            }
            playerTurn = !playerTurn; // Switch turns
        }
    }

    private static void setupGame() {
        int chambersCount = 0;
        while (chambersCount != 6 && chambersCount != 12) {
            System.out.println("Choose chambers (6 or 12):");
            while (!scanner.hasNextInt()) { // Check if input is an integer
                System.out.println("Invalid input. Please enter a number (6 or 12):");
                scanner.next(); // Consume the invalid input
            }
            chambersCount = scanner.nextInt();
            if (chambersCount != 6 && chambersCount != 12) {
                System.out.println("Invalid choice. Please choose from 6 or 12.");
            }
        }
        
        int bulletsCount = chambersCount / 6; // 1 bullet for 6, 2 for 12 chambers
        chambers = new ArrayList<>(Collections.nCopies(chambersCount, false));
        for (int i = 0; i < bulletsCount; i++) {
            chambers.set(i, true);
        }
        Collections.shuffle(chambers); // Mix the chambers
    }

    private static boolean takeTurn() {
        System.out.println("Gripped the gun, points at self's head, pulled the trigger (SHOOT)");
        if(playerTurn == true){
            Delay.waiting();
        }
        
        int shotCounter = 0;
        while (shotCounter < 3){
            if (shoot()) {
                return true; // Bullet fired, game over
            }
            shotCounter++;

           // Check if the player has reached the maximum number of shots
            if (shotCounter >= 3) {
                System.out.println("Turn automatically ends after 3 shots.");
                break; // Exit the loop and end the turn
            }

            if (playerTurn) {
                System.out.println("You can [shoot] again or [end] turn? (Remaining shots: " + (3 - shotCounter) + ")");
                String choice = scanner.next();
                if ("end".equalsIgnoreCase(choice)) {
                    break; // Player chooses to end turn
                }

            } else {
                if (aiDecision(shotCounter)) {
                    System.out.println("AI decides to shoot again.");
                    // Continue the loop to take another shot
                } else {
                    System.out.println("AI decides to end turn.");
                    break; // Exit the loop and end the AI's turn
                };
            }
        }
        return false; // Turn ended without firing a real bullet
    }

    private static boolean shoot() {
        totalShotsFired++;
        int chamberIndex = random.nextInt(chambers.size());
        if (chambers.get(chamberIndex)) {
            System.out.println((playerTurn ? "\nPlayer" : "\nAI") + " fired a real bullet!");
            return true; // Bullet fired
        }

        System.out.println("BANG! it was a blank shot.");
        System.out.println("Total shots so far: " + totalShotsFired + "\n");
        return false; // No bullet fired
    }

    private static boolean aiDecision(int shotCounter) {
        // Calculate risk based on remaining chambers vs. remaining bullets
        int remainingBullets = (int) chambers.stream().filter(b -> b).count();
        int remainingChambers = chambers.size() - shotCounter;
        double risk = (double) remainingBullets / remainingChambers;
    
        // Simple risk thresholds - these can be adjusted based on desired AI risk tolerance
        if (risk < 0.2) {
            return true; // Low risk, decide to shoot
        } else if (risk < 0.5) {
            // Medium risk, random decision to shoot or end
            return random.nextBoolean();
        } else {
            return false; // High risk, decide to end turn
        }
    }
}
