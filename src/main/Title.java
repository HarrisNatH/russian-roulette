public class Title {
    public static void printTitleIntro() {
        String title = "RUSSIAN ROULETTE";
        int titleLength = title.length();

        int frameWidth = titleLength + 6;
        String frameLine = "*".repeat(frameWidth);

        System.out.println(frameLine);
        System.out.println("*" + " ".repeat(frameWidth - 2) + "*");
        System.out.println("*  " + title + "  *");
        System.out.println("*" + " ".repeat(frameWidth - 2) + "*");
        System.out.println(frameLine);
    }
}
