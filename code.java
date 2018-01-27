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
        int[][] grid = new int[COL + 1][ROW + 1];
        int L=1, R=2, U=3, D=4;
        int dirct = D;       
        int dx, dy;
        
        
        int nearLeft = 0, nearRight = 0, nearUp = 0, nearBot = 0; // The nearest positions of obstacles around the head
        
        
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
                
                grid[x][y] = 1;
            }
            int removalCount = in.nextInt(); // the amount walls removed this turn by helper bots
            for (int i = 0; i < removalCount; i++) {
                int removeX = in.nextInt(); // the coordinates of a wall removed this turn
                int removeY = in.nextInt();
            
                grid[removeX][removeY] = 0;
            }
            
            //Calculate the distances between our head to other obstacles
              //Column scans
              for(int j = 1; j <= COL; j++) {
                  int i = myX; 
                    if(grid[i][j] == 1 && j < myY) {
                        nearLeft = j;
                    }
                    
                    if(grid[i][j] == 1 && j > myY) {
                        nearRight = j;
                        break;
                    }
              }
            //Row scans
              for(int i = 1; i < ROW - 1; i++) {
                  int j = myY; 
                    if(grid[i][j] == 1 && i < myX) {
                        nearUp = i;
                    }
                    
                    if(grid[i][j] == 1 && j > myY) {
                        nearBot = i;
                        break;
                    }
              }
              
              

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // DOWN | LEFT | RIGHT | UP or DEPLOY (to clear walls)
            if(firstTime) {
                System.out.println("DOWN");
            }else {
                
                System.err.println(nearLeft);
                System.out.println("DOWN");
                
            }
        }
    }
}
