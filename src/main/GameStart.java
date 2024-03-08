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

    /**
     * <p> This method is the core of the game, using {@code setupGame()}, {@code takeTurn()} and {@code gameOver()}.</p>
     * First, setupGame() sets up the game with bullet and chambers.</p>
     * Next, a {@code WHILE} loop:</P>
     *      playerTurn is a random {@code boolean} that decides if the player or AI starts first.
     *      The IF statement that takes in takeTurn(), if true, it outputs a gameover statement.
     */
    public static void start() {
        setupGame();
        while (true) {
            System.out.println((playerTurn ? "\nPLAYER'S" : "\nAI'S") + " TURN.\n");
            if (takeTurn()) {
                System.out.println((playerTurn ? "\nPlayer" : "\nAI") + " loses!");
                Ascii.gameOver();
                break;
            }
            playerTurn = !playerTurn; // Switch turns
        }
    }

    /**
     * <p>This method asks player how many chambers to start with.</p> 
     * The chambers are turned into an {@code ArrayList collection} with 1 bullet mixed in and shuffled.
     */
    private static void setupGame() {
        int chambersCount = 0;
        while (chambersCount != 6 && chambersCount != 9 && chambersCount != 12) {
            System.out.println("Choose chambers (6 / 9 / 12):");
            while (!scanner.hasNextInt()) { // Check if input is an integer
                System.out.println("Invalid input. Please enter a number (6 / 9 / 12):");
                scanner.next(); // Consume the invalid input
            }
            chambersCount = scanner.nextInt();
            if (chambersCount != 6 && chambersCount != 9 && chambersCount != 12) {
                System.out.println("Invalid choice. Please choose from 6 / 9 / 12.");
            }
        }
        
        int bullet = 1;
        chambers = new ArrayList<>(Collections.nCopies(chambersCount, false));
        for (int i = 0; i < bullet; i++) {
            chambers.set(i, true);
        }
        Collections.shuffle(chambers); // Mix the chambers
    }

    /**
     * <p>Based on {@code playerTurn}, if true, it delays for a player to enter to continue the flow. </p>
     * Each player and AI have up to 3 shots to make judgement, or end their turn.</p>
     * For AI side, uses {@code aiDecision()} method to calculate risk.
     * @return game over if {@code shoot()} turns true
     */
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

           // Check if the player or AI have reached the maximum number of shots
            if (shotCounter >= 3) {
                System.out.println("Turn automatically ends after 3 shots.");
                Line.separator();
                break; // Exit the loop and end the turn
            }

            // if it is Player's turn
            if (playerTurn) {
                System.out.println("You can [shoot] again or [end] turn? (Remaining shots: " + (3 - shotCounter) + ")");
                String choice = scanner.next();
                if ("end".equalsIgnoreCase(choice)) {
                    Line.separator();
                    break; // Player chooses to end turn
                }
            
            // AI's turn
            } else {
                if (aiDecision(shotCounter)) {
                    System.out.println("AI decides to shoot again.");
                    // Continue the loop to take another shot
                } else {
                    System.out.println("AI decides to end turn.");
                    Line.separator();
                    break; // Exit the loop and end the AI's turn
                };
            }
        }
        return false; // Turn ended without firing a real bullet
    }

    /**
     * <p>
     * This method uses boolean, if {@code chambers} returns true, it enters the IF statement tells a real bullet has been fired.</p>
     * Else, it returns with a false statement and tells both side how many bullets has been fired so far.
     * @return if true, a real bullet. Else blank shot.
     */
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

    /**
     * This is a method for AI to make judgement each round.
     * @param shotCounter is the number of shots AI can make in each round
     * @return based on risk, AI will shoot or end turn
     */
    private static boolean aiDecision(int shotCounter) {
        // Calculate risk based on remaining chambers vs. 1 bullet
        int remainingChambers = chambers.size() - shotCounter;
        double risk = 1.0 / remainingChambers;
    
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

    /**
     * This method to reset number of shots fired in every new game
     */
    public static void resetShotsFired(){
        totalShotsFired = 0;
    }
}
