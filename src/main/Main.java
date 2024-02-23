public class Main {
    public static void main(String[] args){
        boolean finished = false;
        while(!finished){
            try{
                System.out.println("Welcome to Russian Roullete Prototype");
                System.out.println("Main menu: \n   1. Start game\n   2. Exit\n*please input a number*");
                int pick = Integer.parseInt(System.console().readLine());

                switch(pick){
                    case 1:
                        //main program
                        break;
                    case 2:
                        System.out.println("Thank you for participating");
                        finished=true;
                        break;
                    default:
                        System.out.println("Please choose a number");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid, please input a number\n");
            }
        }
    }
}