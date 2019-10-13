public class WumpusMap
{
    public static final int NUM_ROWS = 10;
    public static final int NUM_COLUMNS = 10;
    public static final int NUM_PITS = 10;

    private WumpusSquare[][] grid = new WumpusSquare[10][10];
    private int ladderC;
    private int ladderR;
    private int wumpusC;
    private int wumpusR;

    public WumpusMap()
    {
        createMap();
    }

    public void createMap()
    {
        for(int r = 0; r < 10 ; r++) {
            for (int c = 0; c < 10; c++) {
                grid[r][c] = new WumpusSquare();
                grid[r][c].setVisited(false);
            }
        }

        //randomly places pits
        int pitCount = 0;
        do
        {
            int num1 = (int)(Math.random()*10);
            int num2 = (int)(Math.random()*10);
            if(grid[num1][num2].isPit() == true)
                continue;
            else {
                grid[num1][num2].setPit(true) ;
                if(num1+1 < 10)
                    grid[num1+1][num2].setBreeze(true);
                if(num1-1 > -1)
                    grid[num1-1][num2].setBreeze(true);
                if(num2+1 < 10)
                    grid[num1][num2+1].setBreeze(true);
                if(num2-1 > -1)
                    grid[num1][num2-1].setBreeze(true);
                pitCount++;
            }
        }while(pitCount < 10);
        
        //randomly places gold
        boolean gold = false;
        do
        {
            int num1 = (int)(Math.random()*10);
            int num2 = (int)(Math.random()*10);
            if(grid[num1][num2].isPit() == true||grid[num1][num2].isLadder() == true || grid[num1][num2].isWumpus())
                continue;
            else{
                grid[num1][num2].setGold(true);
                gold = true;
            }
        }while(gold == false);
        
        //randomly places wumpus
        boolean wumpus = false;
        do 
        {
            int num1 = (int)(Math.random()*10);
            int num2 = (int)(Math.random()*10);
            if(grid[num1][num2].isPit() == true||grid[num1][num2].isLadder() == true)
                continue;
            else
                grid[num1][num2].setWumpus(true);
            if(num1+1 < 10)
                grid[num1+1][num2].setStench(true);
            if(num1-1 > -1)
                grid[num1-1][num2].setStench(true);
            if(num2+1 < 10)
                grid[num1][num2+1].setStench(true);
            if(num2-1 > -1)
                grid[num1][num2-1].setStench(true);
            wumpus = true;
            wumpusC = num2;
            wumpusR = num1;
        }while(wumpus == false);
        
        //randomly places ladder
        boolean ladder = false;
        do 
        {
            int num1 = (int)(Math.random()*10);
            int num2 = (int)(Math.random()*10);
            if(grid[num1][num2].isPit() == true||grid[num1][num2].isWumpus() == true||grid[num1][num2].isGold() == true)
                continue;
            else{
                grid[num1][num2].setLadder(true);
                ladderR = num1;
                ladderC = num2;
                ladder = true;
            }
        }while(ladder == false);
        for(int r = 0; r < 10 ; r++) {
            for (int c = 0; c < 10; c++) {
                if(grid[r][c].isPit() && grid[r][c].isBreeze())
                    grid[r][c].setBreeze(false);
                if(grid[r][c].isPit() && grid[r][c].isStench())
                    grid[r][c].setStench(false);
            }
        }




    }
    public int getLadderC() {
        return ladderC;
    }

    public void setLadderC(int ladderC) {
        this.ladderC = ladderC;
    }

    public int getLadderR() {
        return ladderR;
    }

    public void setLadderR(int ladderR) {
        this.ladderR = ladderR;
    }

    public int getWumpusR() {
        return wumpusR;
    }

    public void setWumpusR(int ladderR) {
        this.wumpusR = ladderR;
    }

    public int getWumpusC() {
        return wumpusC;
    }

    public void setWumpusC(int ladderR) {
        this.wumpusC = ladderR;
    }

    public WumpusSquare getSquare(int row,int col) {
        if(((col>-1&&col<10)&&(row>-1&&row<10)))
            return grid[row][col];
        return null;
    }
    public String toString()
    {
        String board = "";
        for(int r =0; r < 10;r++)
        {
            for(int c = 0; c < 10;c++)
            {
                if(grid[r][c].isGold() == true)
                    board += "G";
                else if(grid[r][c].isLadder() == true)
                    System.out.print("L");
                else if(grid[r][c].isWumpus() == true)
                    System.out.print("W");
                else if(grid[r][c].isPit() == true)
                    System.out.print("P");
                else
                    System.out.print("*");

            }
            System.out.print("\n");
        }
        return board;
    }
}
