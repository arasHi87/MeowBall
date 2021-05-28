package main.panel;

import javax.swing.*;
import main.Content;
import main.base.Utils;

public class Start extends Base {
    public JButton play;
    private int width, height; // setting for button

    public Start() {
        this.width = 200;
        this.height = 50;
        this.backgroundImage = Utils.getImage("background/start.jpg");

        this.play = new JButton("Play");
        this.play.setBounds(Content.FRAME_WIDTH / 2 - this.width / 2, Content.FRAME_HEIGHT / 2 - this.height / 2,
                this.width, this.height);
        this.play.setOpaque(true);
        this.play.setBorder(null);
        this.play.setContentAreaFilled(false);
    }
}
