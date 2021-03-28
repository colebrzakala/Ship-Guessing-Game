import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        int debug = 0;
        Board board1 = new Board();
        System.out.println("Would you like to enter the game in debug mode? (y or n)");
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        while (!input.equals("y") && !input.equals("n")) {      //run until user enters y or n
            System.out.println("Invalid input. Would you like to enter the game in debug mode? (y or n)");
            s = new Scanner(System.in);
            input = s.nextLine();
        }
        if (input.equals("y")) {
            debug = 1;
        }
        System.out.println("Please enter board height and width (must be between 3x3 and 10x10):");     //ask for board dimensions
        s = new Scanner(System.in);
        input = s.nextLine();
        String[] values = input.split(" ");
        board1.createBoard(Integer.parseInt(values[0]),Integer.parseInt(values[1]));    //create board
        board1.placeBoats();        //place boats randomly
        if (debug == 1) {           //differentiate between debug mode and regular mode
            board1.print();
        }
        else {
            board1.display();
        }
        s = new Scanner(System.in);
        System.out.println("Input location x,y to attack on the board, drone to use the drone ability, or missile to use a missile: ");
        while (s.hasNextLine() && board1.shipsLeft != 0) {      //while there are ships left and the user enters input
            String command = s.nextLine();
            if (command.equals("drone")) {          //run drone
                System.out.println("Would you like to scan a row or column? Type in r for row or c for column.");
                String choice = s.nextLine();
                while (!choice.equals("r") && !choice.equals("c")) {
                    System.out.println("Invalid input. Type in r for row or c for column.");
                    choice = s.nextLine();
                }
                System.out.println("Which row or column would you like to scan?");
                int index = Integer.parseInt(s.nextLine());
                while (index < 1 || board1.board.length < index) {
                    System.out.println("Invalid input. Please type in a number within the boundaries of the board.");
                    index = Integer.parseInt(s.nextLine());
                }
                if (choice.equals("r")) {
                    board1.drone(0, index);         //sets direction of drone
                }
                else {
                    board1.drone(1, index);
                }
            }
            else if (command.equals("missile")) {           //runs missile
                System.out.println("Where would you like to launch your missile?");
                String xandy = s.nextLine();
                String[] xandycoords = xandy.split(" ");        //get coords of missile
                int x = Integer.parseInt(xandycoords[0]);
                int y = Integer.parseInt(xandycoords[1]);
                board1.missile(x,y);
            }
            else {
                values = command.split(" ");
                board1.fire(Integer.parseInt(values[0])-1,Integer.parseInt(values[1])-1);   //calls fire on coords in values
            }
            System.out.println("Ships: "+board1.shipsLeft);         //displays stats
            System.out.println("Shots: "+board1.totalShots);
            System.out.println("Turns: "+board1.turns);
            if (debug == 1) {
                board1.print();
            }
            else {
                board1.display();
            }
            if (board1.shipsLeft > 0) {     //while there are ships left, display the prompt
                System.out.println("Input location x,y to attack on the board, drone to use the drone ability, or missile to use a missile: ");
            }
            else {
                System.out.println("Game Over!");       //display game over stats
                System.out.println("Stats:");
                System.out.println("Turns: " + board1.turns);
                System.out.println("Shots Fired: " + board1.totalShots);
                System.out.println("Missiles Used: " +board1.missilesUsed);
                System.out.println("Drones Used: " +board1.dronesUsed);
            }
        }
    }
}
