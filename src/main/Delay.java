/**
 * This class is responsible to handle the message output speed
 */
public class Delay {
    private static final int delayed= 1000;
    
    /**
     * This method puts next message on delay by putting a thred to sleep for 1000 milis.
     * giving time to Players to digest the current message before continuing.
     */
    public static void waiting() {
        try {
            Thread.sleep(delayed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Press Enter to pull trigger");
        System.console().readLine();
    }
}
