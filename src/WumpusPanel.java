import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class WumpusPanel extends JPanel implements KeyListener
{
    public static final int PLAYING = 0;
    public static final int DEAD = 1;
    public static final int WON = 2;

    private int status;
    private WumpusPlayer player = new WumpusPlayer();
    private WumpusMap map = new WumpusMap();
    private boolean cheats = false;
    private boolean ded = false;

    private BufferedImage  floor = null;
    private BufferedImage  arrow = null;
    private BufferedImage  fog = null;
    private BufferedImage  gold = null;
    private BufferedImage  ladder = null;
    private BufferedImage  pit = null;
    private BufferedImage  breeze = null;
    private BufferedImage  wumpus = null;
    private BufferedImage  deadWumpus = null;
    private BufferedImage  stench = null;
    private BufferedImage  playerUp = null;
    private BufferedImage  playerDown = null;
    private BufferedImage  playerLeft = null;
    private BufferedImage  playerRight = null;

    private BufferedImage buffer = null;
    public WumpusPanel()
    {
        super();
        setSize(600,800);
        //creates the buffer(where the flicker is gone(do this last))


        addKeyListener(this);
        //loads the images
        try
        {
            floor = ImageIO.read((new File("Images\\Floor.gif")));
            arrow = ImageIO.read((new File("Images\\arrow.gif")));
            fog = ImageIO.read((new File("Images\\black.gif ")));
            gold = ImageIO.read((new File("Images\\gold.gif ")));
            ladder = ImageIO.read((new File("Images\\ladder.gif ")));
            pit = ImageIO.read((new File("Images\\pit.gif ")));
            breeze = ImageIO.read((new File("Images\\breeze.gif ")));
            wumpus = ImageIO.read((new File("Images\\wumpus.gif ")));
            deadWumpus = ImageIO.read((new File("Images\\deadwumpus.gif ")));
            stench = ImageIO.read((new File("Images\\stench.gif ")));
            playerUp = ImageIO.read((new File("Images\\playerUp.png ")));
            playerDown = ImageIO.read((new File("Images\\playerDown.png ")));
            playerLeft = ImageIO.read((new File("Images\\playerLeft.png ")));
            playerRight = ImageIO.read((new File("Images\\playerRight.png ")));
        }
        catch(Exception e)
        {
            System.out.println("Error Loading Images: " + e.getMessage());
            e.printStackTrace();
        }
        reset();
    }

    public void reset()
    {
        status = PLAYING;
        map.createMap();
        //places the player at the position of the ladder
        player.setColPosition(map.getLadderC());
        player.setRowPosition(map.getLadderR());
        map.getSquare(map.getLadderR(),map.getLadderC()).setVisited(true);
        player.setArrow(true);
        player.setGold(false );
        cheats = false;
        map.toString();
    }

    public void paint(Graphics g) {
        // Colors Entire Panel Grey
        g.setColor(new Color(111, 111, 111));
        g.fillRect(0, 0, 600, 800);
        // Makes Outlines for inventory and messages
        g.setColor(Color.ORANGE);
        g.drawRect(0, 600, 195, 200);
        g.drawRect(205, 600, 395, 200);
        // Writes both Inventory and Messages
        Font f = new Font("Comic Sans MS", 13, 18);
        g.setFont(f);
        g.drawString("Inventory: ", 10, 625);
        g.drawString("Messages: ", 215, 625);

        //Writes Messages
        int messages = 0; //number of messages
        if (map.getSquare(map.getWumpusR(), map.getWumpusC()).isDeadWumpus() && ded == false) {
            g.drawString("You hear a scream", 215, 650 + (messages * 20));
            ded = true;
            messages++;
        }
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isBreeze() == true) {
            g.drawString("You feel a breeze", 215, 650 + (messages * 20));
            messages++;
        }
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isStench() == true) {
            g.drawString("You smell a stench", 215, 650 + (messages * 20));
            messages++;
        }
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isGold() == true) {
            g.drawString("You see a glimmer", 215, 650 + (messages * 20));
            messages++;
        }
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isLadder() == true) {
            g.drawString("You bump into a ladder", 215, 650 + (messages * 20));
            messages++;
        }
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isPit() == true) {
            g.drawString("You fell to your death", 215, 650 + (messages * 20));
            status = DEAD;
            messages++;
            g.drawString("(Press N for a new Game)", 215, 650 + (messages * 20));
        }
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isWumpus()) {
            g.drawString("You are eaten by the Wumpus", 215, 650 + (messages * 20));
            status = DEAD;
            messages++;
            g.drawString("(Press N for a new Game)", 215, 650 + (messages * 20));
        }
        if (map.getSquare(player.getRowPosition(), player.getColPosition()).isLadder() && status == WON) {
            g.drawString("You Won!", 215, 650 + (messages * 20));
            status = WON;
            messages++;
            g.drawString("(Press N for a new Game)", 215, 650 + (messages * 20));
        }


        //draws arrow in inventory
        if (player.isArrow() == true)
            g.drawImage(arrow, 20, 640, null);
        //draws the gold in inventory
        if (player.isGold() == true)
            g.drawImage(gold, 50, 640, null);
        System.out.print(player.getColPosition());
        System.out.print(player.getRowPosition());
        //draws the map
        if (cheats == false) {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    // prints the floor if you already visited it or fog if u didn't
                    if (map.getSquare(x, y).isVisited())
                        g.drawImage(floor, (y + 1) * 50, (x + 1) * 50, null);
                    else
                        g.drawImage(fog, (y + 1) * 50, (x + 1) * 50, null);

                    // prints gold
                    if (map.getSquare(x, y).isGold() && map.getSquare(x, y).isVisited())
                        g.drawImage(gold, (y + 1) * 50, (x + 1) * 50, null);
                    // prints ladder
                    if (map.getSquare(x, y).isLadder() && map.getSquare(x, y).isVisited())
                        g.drawImage(ladder, (y + 1) * 50, (x + 1) * 50, null);
                    // prints breezes
                    if (map.getSquare(x, y).isBreeze() && map.getSquare(x, y).isVisited())
                        g.drawImage(breeze, (y + 1) * 50, (x + 1) * 50, null);
                    // prints stenches
                    if (map.getSquare(x, y).isStench() && map.getSquare(x, y).isVisited())
                        g.drawImage(stench, (y + 1) * 50, (x + 1) * 50, null);
                    // prints wumpus
                    if (map.getSquare(x, y).isWumpus() && map.getSquare(x, y).isVisited())
                        g.drawImage(wumpus, (y + 1) * 50, (x + 1) * 50, null);
                        // prints dead wumpus
                    else if (map.getSquare(x, y).isDeadWumpus() && map.getSquare(x, y).isVisited())
                        g.drawImage(deadWumpus, (y + 1) * 50, (x + 1) * 50, null);
                    // prints pits
                    if (map.getSquare(x, y).isPit() && map.getSquare(x, y).isVisited())
                        g.drawImage(pit, (y + 1) * 50, (x + 1) * 50, null);


                    // prints the character

                }
            }
        }
        if(cheats == true)
        {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    // prints the floor if you already visited it or fog if u didn't
                    g.drawImage(floor, (y + 1) * 50, (x + 1) * 50, null);
                    // prints gold
                    if (map.getSquare(x, y).isGold())
                        g.drawImage(gold, (y + 1) * 50, (x + 1) * 50, null);
                    // prints ladder
                    if (map.getSquare(x, y).isLadder())
                        g.drawImage(ladder, (y + 1) * 50, (x + 1) * 50, null);
                    // prints breezes
                    if (map.getSquare(x, y).isBreeze())
                        g.drawImage(breeze, (y + 1) * 50, (x + 1) * 50, null);
                    // prints stenches
                    if (map.getSquare(x, y).isStench())
                        g.drawImage(stench, (y + 1) * 50, (x + 1) * 50, null);
                    // prints wumpus
                    if (map.getSquare(x, y).isWumpus())
                        g.drawImage(wumpus, (y + 1) * 50, (x + 1) * 50, null);
                        // prints dead wumpus
                    else if (map.getSquare(x, y).isDeadWumpus())
                        g.drawImage(deadWumpus, (y + 1) * 50, (x + 1) * 50, null);
                    // prints pits
                    if (map.getSquare(x, y).isPit())
                        g.drawImage(pit, (y + 1) * 50, (x + 1) * 50, null);


                    // prints the character

                }
            }
        }
        if(player.getDirection() == player.NORTH)
            g.drawImage(playerUp,(player.getColPosition()+1)*50,(player.getRowPosition()+1)*50,null);
        if(player.getDirection() == player.SOUTH)
            g.drawImage(playerDown,(player.getColPosition()+1)*50,(player.getRowPosition()+1)*50,null);
        if(player.getDirection() == player.WEST)
            g.drawImage(playerLeft,(player.getColPosition()+1)*50,(player.getRowPosition()+1)*50,null);
        if(player.getDirection() == player.EAST)
            g.drawImage(playerRight,(player.getColPosition()+1)*50,(player.getRowPosition()+1)*50,null);
    }
    public void keyPressed(KeyEvent e)
    {
        //not used
    }
    public void keyReleased(KeyEvent e)
    {
        //not used
    }
    public void keyTyped(KeyEvent e)
    {
        //handles all the player inputs
        char key = e.getKeyChar();
        if ((key == 'n' || key == 'N')) {

            if(status == DEAD || status == WON) {
                System.out.println("'N' Pressed: Restarting game...");
                reset();
                repaint();
            }
        }
        if ((key == 'w' || key == 'W')) {

            if(status == PLAYING)
            {
                player.setDirection(player.NORTH);
                if (player.getRowPosition() - 1 > -1)
                    player.setRowPosition(player.getRowPosition() - 1);
            }

            map.getSquare(player.getRowPosition(),player.getColPosition()).setVisited(true);
            repaint();
        }
        if ((key == 'a' || key == 'A')) {

            if(status == PLAYING)
            {
                player.setDirection(player.WEST);
                if (player.getColPosition() - 1 > -1)
                    player.setColPosition(player.getColPosition() - 1);
            }

            map.getSquare(player.getRowPosition(),player.getColPosition()).setVisited(true);
            repaint();
        }
        if ((key == 's' || key == 'S')) {

            if(status == PLAYING)
            {
                player.setDirection(player.SOUTH);
                if ((player.getRowPosition() + 1) < 10)
                    player.setRowPosition(player.getRowPosition() + 1);
            }

            map.getSquare(player.getRowPosition(),player.getColPosition()).setVisited(true);
            repaint();
        }
        if ((key == 'd' || key == 'D')) {

            if(status == PLAYING)
            {
                player.setDirection(player.EAST);
                if (player.getColPosition() + 1 < 10)
                    player.setColPosition(player.getColPosition() + 1);
            }

            map.getSquare(player.getRowPosition(),player.getColPosition()).setVisited(true);
            repaint();
        }
        if ((key == 'i' || key == 'I')) {
            if (player.getRowPosition() > map.getWumpusR() && player.getColPosition() == map.getWumpusC() && player.isArrow() == true) {
                //set wumpus to dead
                map.getSquare(map.getWumpusR(),map.getWumpusC()).setDeadWumpus(true);
                map.getSquare(map.getWumpusR(),map.getWumpusC()).setWumpus(false);
            }
            player.setArrow(false);
            repaint();

        }
        if ((key == 'j' || key == 'J')) {
            if (player.getRowPosition() == map.getWumpusR() && player.getColPosition() > map.getWumpusC() && player.isArrow() == true) {
                //set wumpus to dead
                map.getSquare(map.getWumpusR(),map.getWumpusC()).setDeadWumpus(true);
                map.getSquare(map.getWumpusR(),map.getWumpusC()).setWumpus(false);
            }
            player.setArrow(false);
            repaint();
        }
        if ((key == 'k' || key == 'K')) {
            if (player.getRowPosition() < map.getWumpusR() && player.getColPosition() == map.getWumpusC() && player.isArrow() == true) {
                //set wumpus to dead
                map.getSquare(map.getWumpusR(),map.getWumpusC()).setDeadWumpus(true);
                map.getSquare(map.getWumpusR(),map.getWumpusC()).setWumpus(false);
            }
            player.setArrow(false);
            repaint();
        }
        if ((key == 'l' || key == 'L')) {
            if (player.getRowPosition() == map.getWumpusR() && player.getColPosition() < map.getWumpusC() && player.isArrow() == true) {
                //set wumpus to dead
                map.getSquare(map.getWumpusR(),map.getWumpusC()).setDeadWumpus(true);
                map.getSquare(map.getWumpusR(),map.getWumpusC()).setWumpus(false);
            }
            player.setArrow(false);
            repaint();
        }
        if ((key == 'c' || key == 'C')) {
            if (player.getRowPosition() == map.getLadderR() && player.getColPosition() == map.getLadderC() && player.isGold() == true)
                status = WON;
            repaint();
        }
        if ((key == 'p' || key == 'P')) {
            if (map.getSquare(player.getRowPosition(), player.getColPosition()).isGold() == true) {
                player.setGold(true);
                map.getSquare(player.getRowPosition(), player.getColPosition()).setGold(false);
                repaint();
            }
        }
        if (key == '*') {
           if(cheats == false)
               cheats = true;
           else if(cheats == true)
               cheats = false;
            repaint();
        }

    }

}
