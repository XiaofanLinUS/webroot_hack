import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Don't run into a player's light trail! Use your helper bots at strategic moments or as a last resort to be the last drone standing!
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        boolean firstTime = true;
        int COL = 30;
        int ROW = 15;
        
        int playerCount = in.nextInt(); // the number of at the start of this game
        int myId = in.nextInt(); // your bot's id
        int myX = 0, myY = 0;
        int[][] grid = new int[ROW][COL];
        int L=1, R=2, U=3, D=4;
        int dirct = D;       
        int dx, dy;
        
        
        int nearLeft = -1, nearRight = -1, nearUp = -1, nearBot = -1; // The nearest positions of obstacles around the head
        int[] spaces = new int[5];
        
        // game loop
        while (true) {
            int helperBots = in.nextInt(); // your number of charges left to deploy helper bots
            for (int i = 0; i < playerCount; i++) {
                int x = in.nextInt(); // your bot's coordinates on the grid (0,0) is top-left
                int y = in.nextInt();
                
                if(i == myId) { // Keep track of my location
                    myX = x;
                    myY = y;
                }
                   
                grid[y][x] = 1;
            }
            int removalCount = in.nextInt(); // the amount walls removed this turn by helper bots
            for (int i = 0; i < removalCount; i++) {
                int removeX = in.nextInt(); // the coordinates of a wall removed this turn
                int removeY = in.nextInt();
            
                grid[removeX][removeY] = 0;
            }
            
            //Calculate the distances between our head to other obstacles
              //Column scans
              for(int j = 0; j < COL; j++) {
                  int i = myY; 
                    if(grid[i][j] == 1 && j < myX) {
                        nearLeft = j;
                    }
                    
                    if(grid[i][j] == 1 && j > myX) {
                        nearRight = j;
                        break;
                    }
              }
            //Row scans
              for(int i = 0; i < ROW; i++) {
                  int j = myX; 
                    if(grid[i][j] == 1 && i < myY) {
                        nearUp = i;
                    }
                    
                    if(grid[i][j] == 1 && i > myY) {
                        nearBot = i;
                        break;
                    }
              }
              

              // Calculate the space between nearest obstacles
              for(int i = 1; i <= 4; i++) {
                if(i == L) {
                  if(nearLeft != -1) spaces[i] = myX - nearLeft;
                  else spaces[i] = -1;
                }else if(i == R) {
                  if(nearRight != -1) spaces[i] = nearRight - myX;
                  else spaces[i] = -1;
                }else if(i == U) {
                  if(nearUp != -1) spaces[i] =  myY - nearUp;
                  else spaces[i] = -1;                  
                }else {
                  if(nearBot != -1) spaces[i] = nearBot - myY;
                  else spaces[i] = -1;
                }                
              }
              
              
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // DOWN | LEFT | RIGHT | UP or DEPLOY (to clear walls)
              System.err.println("Debug messages: NL: " + spaces[L]);
              System.err.println("Debug messages: NB: " + spaces[D]);
              System.err.println("Debug messages: NU: " + spaces[U]);
              System.err.println("Debug messages: NR: " + spaces[R]);
              int maxSpace = spaces[1];
            if(firstTime) {
                System.out.println("LEFT");
                firstTime = false;
            }else {
              
              for(int i = 1; i <= 4; i++) {
                if(spaces[i] > maxSpace) {
                  maxSpace = spaces[i];
                  dirct = i;
                }
              }
              for(int i = 1; i <= 4; i++) {
                if(spaces[i] == -1) {
                  maxSpace = spaces[i];
                  dirct = i;
                  
                }
              }

              if(dirct == L) System.out.println("LEFT");
              if(dirct == D) System.out.println("DOWN");
              if(dirct == R) System.out.println("RIGHT");
              if(dirct == U) System.out.println("UP");
                
            }
        }
    }
}
