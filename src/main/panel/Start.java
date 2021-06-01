package main.panel;

import javax.swing.*;
import main.Content;
import main.base.Utils;

public class Start extends Base {
    public JButton single;
    private int width, height; // setting for button

    public Start() {
        this.width = 200;
        this.height = 50;
        this.backgroundImage = Utils.getImage(this.getBackgroundImage("background/start"));

        this.single = new JButton("Play");
        this.single.setBounds(Content.FRAME_WIDTH / 2 - this.width / 2, Content.FRAME_HEIGHT / 2 - this.height / 2,
                this.width, this.height);
        this.single.setOpaque(true);
        this.single.setBorder(null);
        this.single.setContentAreaFilled(false);
    }
}
