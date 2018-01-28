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
  public static final int MAX = 0;
  
  public static int myX = 0;
  public static int myY = 0;
  
  public static int[] dirs;
  public static int[] preXs;
  public static int[] preYs;
  public static int[] nexX;
  public static int[] nexY;
  
  public static void main(String args[]) {
    
    Scanner in = new Scanner(System.in);
    boolean firstTime = true;
        
    int playerCount = in.nextInt(); // the number of at the start of this game    
    dirs = new int[playerCount];
    preXs = new int[playerCount];
    preYs = new int[playerCount];
    nexX = new int[playerCount];
    nexY = new int[playerCount];

    
    int myId = in.nextInt(); // your bot's id
    


    int dirct = D;
    int prevDirect = D;
    int dx, dy;
        
        
    int nearLeft = -1, nearRight = -1, nearUp = -1, nearBot = -1; // The nearest positions of obstacles around the head

    int[] near = new int[5];
    int[] spaces = new int[5];
    int[] nextPos;

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

        
        if(y >= 0 && x >= 0) {          
          grid[y][x] = 1;
          
          if(!firstTime && i != myId) { //Calculate other player's potential directions
            if(x != nexX[i] || y != nexY[i])  grid[nexY[i]][nexX[i]] |= 1;
            
            dirs[i] = calDir(x, y, preXs[i], preYs[i]);
            nextLocation(x, y, dirs[i], i);
            grid[nexY[i]][nexX[i]] = 2;
          }
          preXs[i] = x;
          preYs[i] = y;

          
        }
        firstTime = false;
      }
      int removalCount = in.nextInt(); // the amount walls removed this turn by helper bots
      for (int i = 0; i < removalCount; i++) {
        int removeX = in.nextInt(); // the coordinates of a wall removed this turn
        int removeY = in.nextInt();
        if(removeY >= 0 && removeX >= 0)            
          grid[removeY][removeX] = 0;
      }                          

     
              
      // Write an action using System.out.println()
      // To debug: System.err.println("Debug messages...");

      findNearPointes(near);
      calSpaces(spaces, near);
      dirct = calMaxSpace(spaces);
      
      System.err.println("Debug messages: SL: " + spaces[L]);
      System.err.println("Debug messages: SB: " + spaces[D]);
      System.err.println("Debug messages: SU: " + spaces[U]);
      System.err.println("Debug messages: SR: " + spaces[R]);
      
      System.err.println("Debug messages: NL: " + near[L]);
      System.err.println("Debug messages: NB: " + near[D]);
      System.err.println("Debug messages: NU: " + near[U]);
      System.err.println("Debug messages: NR: " + near[R]);
      
      System.err.println("Debug messages: MX: " + myX);
      System.err.println("Debug messages: MY: " + myY);

      if(spaces[MAX] == 1) System.out.println("DEPLOY");
      else if(dirct == L && prevDirect != R) System.out.println("LEFT");        
      else if(dirct == D && prevDirect != U) System.out.println("DOWN");
      else if(dirct == R && prevDirect != L) System.out.println("RIGHT");
      else if(dirct == U && prevDirect != D) System.out.println("UP");
      else

        prevDirect = dirct;
    }
  }

  public static int calMaxSpace(int[] spaces) {
    int dirct = 1;

    int maxSpace = spaces[1];
    for(int i = 1; i <= 4; i++) {
      if(spaces[i] > maxSpace) {
        maxSpace = spaces[i];
        dirct = i;
      }
    }
    spaces[MAX] = maxSpace;
    
    return dirct;

  }
  public static void calSpaces(int[] spaces, int[] near) {

    // Calculate the space between nearest obstacles
    for(int i = 1; i <= 4; i++) {
      int dl = myX - near[L];
      int dr = near[R] - myX;
      int du = myY - near[U];
      int dd = near[D] - myY;
      if(i == L) {
        if(dl > 0) spaces[i] = dl;
        else if(dl <= 0) spaces[i] = COL + dl;
      }else if(i == R) {
        if(dr > 0) spaces[i] = dr;
        else if(dr <= 0) spaces[i] = COL + dr;

      }else if(i == U) {
        if(du > 0) spaces[i] = du;
        else if(du <= 0) spaces[i] = ROW + du;
      }else if(i == D) {
        if(dd > 0) spaces[i] = dd;
        else if(dd<= 0) spaces[i] = ROW + dd;
      }                
    }
              



  }
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

      if(grid[i][j] >= 1) {
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

      if(grid[i][j] >= 1) {
        near[L] = j;
        break;
      }
      j--;
      
    }
    over = false;
    
    //Row scans
    // Search for neares down
    i = myY + 1;
    while(true) {
      j = myX;

      if(!over && i == ROW) {
        i = 0;
        over = true;
      }      

      if(grid[i][j] >= 1) {
        near[D] = i;
        break;
      }
      i++;
    }
    over = false;
    //Search for nearest left obstatcle
    i = myY - 1;
    while(true) {
      j = myX;

      if(!over && i == -1) {
        i = ROW - 1;
        over = true;
      }      

      if(grid[i][j] >= 1) {
        near[U] = i;
        break;
      }
      i--;
      
    }
    over = false;

  }
  public static int calDir(int x, int y, int px, int py) {
    if(x > px) return R;
    if(x < px) return L;
    if(y > py) return D;
    if(y < py) return U;
    return -1;
  }

  public static void nextLocation(int x, int y, int dir, int index) {
    int[] xy = new int[2];
    if(dir == L) {
      xy[0] = x + 1;
      xy[1] = y;
    }else if(dir == R) {
      xy[0] = x - 1;
      xy[1] = y;
    }else if(dir == U) {
      xy[0] = x;
      xy[1] = y - 1;
    }else {
      xy[0] = x;
      xy[1] = y + 1;
    }
    

    if(xy[0] < 0) {
      xy[0] = COL - 1;
    }else if(xy[0] > COL - 1) {
      xy[0] = 0;
    }

    if(xy[1] < 0) {
      xy[1] = ROW - 1;
    }else if(xy[1] > ROW - 1) {
      xy[1] = 0;
    }
    nexY[index] = xy[1];
    nexX[index] = xy[0];
  }
}

