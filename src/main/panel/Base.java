package main.panel;

import java.awt.*;
import javax.swing.*;
import main.Content;

public class Base extends JPanel {
    protected Image backgroundImage;

    Base() {
        this.setSize(Content.FRAME_WIDTH, Content.FRAME_HEIGHT);
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setLayout(null);
        this.setOpaque(false);
    }

    public void paint(Graphics g) {
        g.drawImage(this.backgroundImage, 0, 0, Content.FRAME_WIDTH, Content.FRAME_HEIGHT, this); // draw background
        super.paint(g);
    }
}
