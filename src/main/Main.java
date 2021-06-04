package main;

import main.base.Sound;
import main.base.Utils;
import main.panel.*;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;

public class Main extends JFrame {
    private Start start;
    public static Sound startMusic;

    Main() {
        // basic setting
        setTitle("喵喵打排球");
        setSize(Content.FRAME_WIDTH, Content.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setResizable(false); // fixed window

        // panel setting
        start = new Start(this);

        // main frame panel setting
        add(start);
        setVisible(true);
        Main.startMusic = new Sound(); // the start music
        Main.startMusic.play("start", true);
    }

    public void switchPanel(JPanel contentPanel, JPanel showPanel) {
        remove(contentPanel);
        contentPanel.setFocusable(false);

        add(showPanel);
        showPanel.requestFocus();

        revalidate();
        repaint();
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