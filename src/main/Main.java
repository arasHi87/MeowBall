package main;

import main.base.Utils;
import main.panel.*;
import main.panel.Character;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Enumeration;

public class Main extends JFrame {
    private Start start;
    private Game game;
    private Character character;

    Main() {
        // basic setting
        this.setTitle("喵喵打排球");
        this.setSize(Content.FRAME_WIDTH, Content.FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false); // fixed window

        // panel setting
        this.start = new Start();
        this.character = new Character();

        // start panel setting
        this.start.single.addActionListener(new startListener());
        this.start.add(this.start.single);
        this.character.start.addActionListener(new characterListener());
        this.character.add(this.character.start);

        // main frame panel setting
        this.add(this.start);
        this.setVisible(true);
    }

    private void switchPanel(JPanel contentPanel, JPanel showPanel) {
        this.remove(contentPanel);
        contentPanel.setFocusable(false);

        this.add(showPanel);
        showPanel.requestFocus();

        this.revalidate();
        this.repaint();
    }

    public class startListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == start.single) {
                // press start button, change to game panel
                switchPanel(start, character);
            }
        }
    }

    public class characterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == character.start) {
                String player1Image = MessageFormat.format("characters/{0}/right.png", character.playerChoose[0] + 1);
                String player2Image = MessageFormat.format("characters/{0}/left.png", character.playerChoose[1] + 1);
                game = new Game(player1Image, player2Image);
                switchPanel(character, game);
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

    public static void main(String[] args) throws FontFormatException, IOException {
        initGobalFont(Utils.getFont("8bit16.ttf", 60));
        new Main();
    }
}