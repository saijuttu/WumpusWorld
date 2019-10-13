import java.awt.*;
import javax.swing.*;
public class WumpusFrame extends JFrame {
    public WumpusFrame(String frameName)
    {
        super(frameName);
        WumpusPanel panel = new WumpusPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        Insets frameInsets = getInsets();
        int frameWidth = panel.getWidth()
                + (frameInsets.left + frameInsets.right);
        int frameHeight = panel.getHeight()
                + (frameInsets.top + frameInsets.bottom);
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setLayout(null);
        add(panel);
        pack();
        setVisible(true);
    }
}

