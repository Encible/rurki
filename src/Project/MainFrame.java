package Project;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public double[][] outcome;
    public MainFrame(double[][] outcome) {

        super("Graph");
        this.outcome = outcome;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 1000);
        setLayout(new GridLayout(this.outcome.length+10 , this.outcome.length, 0, 0));

        for (int i = this.outcome.length - 1; i >= 0; i--) {
            for (int j = 0; j < this.outcome.length; j++) {
                JPanel tile = new Tile(this.outcome[i][j]);
                add(tile);
            }
        }

        for (int i = 0; i<7; i++)
            for (int j = 0; j < this.outcome.length; j++) {
                JPanel tile = new WhiteTile();
                add(tile);
            }

        for (int i = 0; i<3; i++)
            for (int j = 0; j < this.outcome.length; j++) {
                JPanel tile = new Tile((double)j*2/this.outcome.length);
                add(tile);
            }

        setVisible(true);



    }
}