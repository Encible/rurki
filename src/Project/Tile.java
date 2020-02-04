package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import static java.lang.Math.*;

public class Tile extends JPanel {
    double value;

    Tile(double value) {
        this.value = value;
    }
    protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Rectangle2D rectangle = new Rectangle2D.Double(0, 0,  this.getWidth(), this.getHeight());


            int color1 = (int)((this.value)*(200.0/2.0));
            int color2 = (int)(200/2*(Math.cos(value* PI)+1));
            int color3 = (int)(200/2*(Math.sin(value* PI+PI)+1));

            if(value == -1) {
                g2d.setColor(new Color (255, 255, 255));
            }
            else {
                g2d.setColor(new Color(200-color2,color3,200-color1));
            }
            g2d.fill(rectangle);
    }
}
