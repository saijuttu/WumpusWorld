
public class WumpusSquare
{
    private boolean gold;
    private boolean ladder;
    private boolean pit;
    private boolean breeze;
    private boolean wumpus;
    private boolean deadWumpus;
    private boolean stench;
    private boolean visited;
    public WumpusSquare()
    {
        gold = false;
        ladder = false;
        pit = false;
        breeze = false;
        wumpus = false;
        deadWumpus = false;
        stench = false;
        visited = false;
    }

    public boolean isGold() {
        return gold;
    }

    public void setGold(boolean gold) {
        this.gold = gold;
    }

    public boolean isLadder() {
        return ladder;
    }

    public void setLadder(boolean ladder) {
        this.ladder = ladder;
    }

    public boolean isPit() { return pit; }

    public void setPit(boolean pit) {
        this.pit = pit;
    }

    public boolean isBreeze() {
        return breeze;
    }

    public void setBreeze(boolean breeze) {
        this.breeze = breeze;
    }

    public boolean isWumpus() {
        return wumpus;
    }

    public void setWumpus(boolean wumpus) {
        this.wumpus = wumpus;
    }

    public boolean isDeadWumpus() {
        return deadWumpus;
    }

    public void setDeadWumpus(boolean deadWumpus) {
        this.deadWumpus = deadWumpus;
    }

    public boolean isStench() {
        return stench;
    }

    public void setStench(boolean stench) {
        this.stench = stench;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    public String toString()
    {
        if(gold == true)
            return  "G";
        else if(pit == true)
            return "P";
        else if(ladder = true)
            return "L";
        else if(deadWumpus = true)
            return "D";
        else if(wumpus = true)
            return "W";
        else
            return "*";
    }
}
