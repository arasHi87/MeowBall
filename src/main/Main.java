package main;

import main.panel.*;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

public class Main extends JFrame {
    private Start start;
    private Game game;

    Main() {
        // basic setting
        this.setTitle("喵喵打排球");
        this.setSize(Content.FRAME_WIDTH, Content.FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false); // fixed window

        // panel setting
        this.start = new Start();

        // start panel setting
        this.start.play.addActionListener(new startListener());
        this.start.add(this.start.play);

        // main frame panel setting
        this.add(this.start);
        this.setVisible(true);
    }

    public class startListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == start.play) {
                // press start button, change to game panel
                add(game = new Game());

                start.setVisible(false);
                game.setVisible(true);
            }
        }
    }

    public static void initGobalFont(Font font) {
        FontUIResource fontResource = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, fontResource);
        }
    }

    public static void main(String[] args) {
        initGobalFont(new Font("Times-Roman", Font.BOLD | Font.ITALIC, 60));
        new Main();
    }
}