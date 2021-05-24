package main;

import main.panel.*;
import javax.swing.JFrame;

public class Main extends JFrame {
    Main() {
        this.add(new Game());
        this.setTitle("喵喵打排球");
        this.setSize(Content.FRAME_WIDTH, Content.FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false); // fixed window
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}