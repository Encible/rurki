package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class WhiteTile extends JPanel {

    WhiteTile() {
        setPreferredSize(new Dimension(200,200));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle2D rectangle = new Rectangle2D.Double(0, 0,  this.getWidth(), this.getHeight());
        g2d.setColor(new Color (255, 255, 255));
        g2d.fill(rectangle);
    }
}
