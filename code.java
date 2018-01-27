import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Don't run into a player's light trail! Use your helper bots at strategic moments or as a last resort to be the last drone standing!
 **/
class Player {
  public static final int L=1, R=2, U=3, D=4;
  public static final int COL = 30;
  public static final int ROW = 15;
  public static final int[][] grid = new int[ROW][COL];
  public static int myX = 0;
  public static int myY = 0;
  
  public static void main(String args[]) {
    
    Scanner in = new Scanner(System.in);
    boolean firstTime = true;
        
    int playerCount = in.nextInt(); // the number of at the start of this game
    int myId = in.nextInt(); // your bot's id



    int dirct = D;       
    int dx, dy;
        
        
    int nearLeft = -1, nearRight = -1, nearUp = -1, nearBot = -1; // The nearest positions of obstacles around the head

    int[] near = new int[5];
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

     
              
      // Write an action using System.out.println()
      // To debug: System.err.println("Debug messages...");

      findNearPointes(near);
      // DOWN | LEFT | RIGHT | UP or DEPLOY (to clear walls)
      System.err.println("Debug messages: NL: " + near[L]);
      System.err.println("Debug messages: NB: " + near[D]);
      System.err.println("Debug messages: NU: " + near[U]);
      System.err.println("Debug messages: NR: " + near[R]);
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
  // public static void calSpaces(int[] spaces, ) {

  //    // Calculate the space between nearest obstacles
  //     for(int i = 1; i <= 4; i++) {
  //       if(i == L) {
  //         if(near[L] != -1) spaces[i] = myX - near[L];
  //         else spaces[i] = -1;
  //       }else if(i == R) {
  //         if(near[R] != -1) spaces[i] = near[R] - myX;
  //         else spaces[i] = -1;
  //       }else if(i == U) {
  //         if(near[U] != -1) spaces[i] =  myY - near[U];
  //         else spaces[i] = -1;                  
  //       }else {
  //         if(near[D] != -1) spaces[i] = near[D] - myY;
  //         else spaces[i] = -1;
  //       }                
  //     }
              



  // }
  public static void findNearPointes(int[] near){
    //Calculate the distances between our head to other obstacles
    //Column scans
    boolean over = false;
    int i = 0, j = 0;
    //Search for nearest right obstacle
    j = myX + 1;
    while(true) {
      i = myY;

      if(!over && j == COL) {
        j = 0;
        over = true;
      }      

      if(grid[i][j] == 1) {
        near[R] = j;
        break;
      }
      j++;
    }
    over = false;
    //Search for nearest left obstatcle
    j = myX - 1;
    while(true) {
      i = myY;

      if(!over && j == -1) {
        j = COL - 1;
        over = true;
      }      

      if(grid[i][j] == 1) {
        near[L] = j;
        break;
      }
      j--;
      
    }
    over = false;
    //Row scans
    // Search for neares down
    i = myY - 1;
    while(true) {
      j = myX;

      if(!over && i == -1) {
        i = ROW - 1;
        over = true;
      }      

      if(grid[i][j] == 1) {
        near[D] = i;
        break;
      }
      i--;

    }

    over = false;
    i = myY + 1;
    while(true) {
      j = myX;

      if(!over && i == ROW) {
        i = 0;
        over = true;
      }      

      if(grid[i][j] == 1) {
        near[U] = i;
        break;
      }
      i++;
    }

  }
  
}
