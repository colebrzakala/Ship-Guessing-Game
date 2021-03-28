
import java.lang.Math;
import java.util.Scanner;

public class Board {
    Cell[][] board;             //variables
    Boat[] boats;
    int totalShots;
    int turns;
    int shipsLeft;
    int hits;
    int dronesUsed = 0;
    int dronesAllowed = 1;
    int missilesUsed = 0;
    int missilesAllowed = 1;
    int droneCount;
    int lastShips;
    int misses;
    public void createBoard(int rows,int cols) {                //creates the board given the input dimensions
        if ((3<=rows && rows<=10) && ((3<=cols)&&(cols<=10))) {
            board = new Cell[rows][cols];       //new Cell[][] to hold cell info
            for (int i = 0;i<rows;i++) {
                for (int j = 0;j<cols;j++) {
                    board[i][j] = new Cell(i,j,'-');
                }
            }
        }
        else {
            System.out.println("Invalid board dimensions");
        }
    }
    public void placeBoats() {      //places boats randomly
        int rows = board.length;
        int cols = board[0].length;
        int least = rows;
        if (cols < rows) {
            least = cols;
        }
        if (least == 3) {           //setting size of Boat[] and shipsLeft based on size of board
            boats = new Boat[1];
            shipsLeft = 1;
        }
        if (least == 4) {
            boats = new Boat[2];
            shipsLeft = 2;
        }
        if (least == 5 || least == 6) {
            boats = new Boat[3];
            shipsLeft = 3;
        }
        if (least == 7 || least == 8) {
            boats = new Boat[4];
            shipsLeft = 4;
        }
        if (least == 9 || least == 10) {
            boats = new Boat[5];
            shipsLeft = 5;
        }
        if (least >= 3) {                   //adds boats to the Boat[] based on board size
            boats[0] = new Boat();
            boats[0].setLen(2);
            if (least >= 4) {
                boats[1] = new Boat();
                boats[1].setLen(3);
                if (least >= 5) {
                    boats[2] = new Boat();
                    boats[2].setLen(3);
                    if (least >= 7) {
                        boats[3] = new Boat();
                        boats[3].setLen(4);
                        if (least >= 9) {
                            boats[4] = new Boat();
                            boats[4].setLen(5);
                        }
                    }
                }
            }
        }
        for (int i = 0; i<boats.length; i++) {      //randomly sets orientation of each boat
            double orient = Math.random()*2;
            int orientint = (int) Math.floor(orient);
            if (orientint < 1) {
                boats[i].setOrient(true);   //sets orientation to vertical
            }
            else {
                boats[i].setOrient(false);  //sets orientation to horizontal
            }
            if (boats[i].getOrient()) {         //checks to see if random spot is valid for placement
                boolean validspot = false;
                int starty = 0;
                int startx = 0;
                while (!validspot) {
                    validspot = true;
                    starty = (int) Math.floor(Math.random()* board.length);
                    startx = (int) Math.floor(Math.random()* board[i].length);
                    while (starty > (board.length-boats[i].getLen())) {         //while starting y position can fit boat in the board
                        starty = (int) Math.floor(Math.random()* board.length);
                    }
                    for (int k = 0;k<boats[i].getLen();k++) {                   //checks to make sure no boats are in path
                        if (board[starty + k][startx].get_status() == 'B') {
                            validspot = false;
                            break;
                        }
                    }
                }
                Cell[] newparts = new Cell[boats[i].getLen()];
                newparts[0] = board[starty][startx];
                board[starty][startx].set_status('B');              //add boat parts to Cell[] and set their status
                for (int j = 1; j < boats[i].getLen(); j++) {
                    newparts[j] = board[starty+1][startx];
                    board[starty+j][startx].set_status('B');
                }
                boats[i].setParts(newparts);
            }
            else {                              //repeat above method but for horizontal boats
                boolean validspot = false;
                int starty = 0;
                int startx = 0;
                while (!validspot) {
                    validspot = true;
                    starty = (int) Math.floor(Math.random()* board.length);
                    startx = (int) Math.floor(Math.random()* board[i].length);
                    while (startx > (board[i].length-boats[i].getLen())) {
                        startx = (int) Math.floor(Math.random()* board.length);
                    }
                    for (int k = 0;k<boats[i].getLen();k++) {
                        if (board[starty][startx+k].get_status()=='B') {
                            validspot = false;
                        }
                    }
                }
                Cell[] newparts = new Cell[boats[i].getLen()];
                newparts[0] = board[starty][startx];
                board[starty][startx].set_status('B');
                for (int j = 1; j < boats[i].getLen(); j++) {
                    newparts[j] = board[starty][startx+1];
                    board[starty][startx+j].set_status('B');
                }
                boats[i].setParts(newparts);
            }
        }
    }
    public void fire(int x,int y) {         //single space fire method
        char cellstatus;
        if ((x<0 || x> board[0].length) || (y<0 || y> board.length)) {      //check if coords are in the board
            cellstatus = 'X';
        }
        else {
            cellstatus = board[y][x].get_status();      //gets cell status
        }
        switch (cellstatus) {       //changes status based on previous status
            case '-':                               //if not boat present
                System.out.println("Miss");
                board[y][x].set_status('M');
                misses++;
                break;
            case 'B':                               //if boat is present
                System.out.println("Hit");
                board[y][x].set_status('H');
                hits++;
                break;
            case 'H':
            case 'M':
            case 'X':
                System.out.println("penalty");      //if user fires out of bounds or on previously fired upon space
                turns++;
        }
        totalShots++;
        turns++;
        lastShips = shipsLeft;
        shipsLeft = 0;
        for (int i = 0; i< boats.length;i++) {                      //check to see if any boats were sunk in the shot
            int partStatus = 0;
            for (int k = 0; k < boats[i].getLen(); k++) {
                if (boats[i].getParts()[k].get_status() == 'B') {
                    partStatus++;
                }
            }
            if (partStatus > 0) {
                shipsLeft++;
            }
        }
        if (shipsLeft < lastShips) {            //prints sunk if the current amount of boats is less than last round
            System.out.println("Sunk");
        }
    }
    public void display() {         //displays the board for a regular game, hiding the boats
        String total = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char character = (board[i][j].get_status());
                if (character == 'B') {
                    character = '-';
                }
                total += character + " ";
            }
            total += "\n";
        }
        System.out.println("Board: ");
        System.out.println(total);
    }
    public void print() {           //prints the board for debug mode, with the boats revealed
        String total = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                total += (board[i][j].get_status()) + " ";
            }
            total += "\n";
        }
        System.out.println("Board: ");
        System.out.println(total);
    }
    public void missile(int x,int y) {          //launches missile on 9x9 square centered on the input coords
        if (missilesUsed < missilesAllowed) {   //checks if user can use missile
            x = x-1;
            y = y-1;
            if ((x < 0 || x >= board[0].length) || (y < 0 || y >= board.length)) {      //checks if center coords are valid
                System.out.println("Invalid coordinates");
            }
            else {
                for (int i = x - 1; i <= x + 1; i++) {                                          //fires upon all 9 spaces, disregarding those out of bounds
                    for (int j = y - 1; j <= y + 1; j++) {
                        if ((i < board[0].length && i >= 0) && (j < board.length) && j >= 0) {
                            char cellstatus = board[j][i].get_status();
                            switch (cellstatus) {
                                case '-':
                                    board[j][i].set_status('M');
                                    misses++;
                                    break;
                                case 'B':
                                    board[j][i].set_status('H');
                                    System.out.println("Hit");
                                    hits++;
                                    break;
                                case 'H':
                                case 'M':
                            }
                        }
                    }
                }
                turns++;
                totalShots++;
                lastShips = shipsLeft;
                shipsLeft = 0;
                for (int i = 0; i< boats.length;i++) {                      //checks to see if any boats have sunk in the same way as in fire() method
                    int partStatus = 0;
                    for (int k = 0; k < boats[i].getLen(); k++) {
                        if (boats[i].getParts()[k].get_status() == 'B') {
                            partStatus++;
                        }
                    }
                    if (partStatus > 0) {
                        shipsLeft++;
                    }
                }
                if (shipsLeft < lastShips) {
                    System.out.println("Sunk");
                }
                missilesUsed++;
            }
        }
        else {      //if user out of missiles
            System.out.println("No missile uses left.");
        }
    }
    public void drone(int direction,int index) { //launches drone method on input index in input direction
        int count = 0;
        if (dronesUsed < dronesAllowed) {       //checks if drone can be used
            count = 0;
            if (direction == 0) {                   //row drone
                for (int i = 0; i < board[0].length; i++) {
                    if (board[index-1][i].get_status() == 'B' || board[index-1][i].get_status() == 'H') {
                        count++;
                    }
                }
            }
            else {                                  //column drone
                for (int i = 0; i < board.length; i++) {
                    if (board[i][index-1].get_status() == 'B' || board[i][index-1].get_status() == 'H') {
                        count++;
                    }
                }
            }
            System.out.println("Drone has scanned " + count + " targets in the specified area.");
            dronesUsed++;
            turns++;
            droneCount = count;
        }
        else {
            System.out.println("No drone uses left.");
        }

    }
}
