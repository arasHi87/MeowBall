package main.panel;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.*;
import main.Content;
import main.Main;
import main.base.Utils;

public class Base extends JPanel {
    protected Main frame;
    protected Image backgroundImage;

    Base(Main frame) {
        setSize(Content.FRAME_WIDTH, Content.FRAME_HEIGHT);
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setLayout(null);
        setOpaque(false);
        this.frame = frame;
    }

    public void switchPanel(JPanel showPanel) {
        frame.switchPanel(this, showPanel);
    }

    public String getBackgroundImage(String folderName) {
        ArrayList<String> results = new ArrayList<String>();
        File[] files = new File(Utils.getImagePath(folderName)).listFiles();

        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile() && (fileName.endsWith(".jpg") || fileName.endsWith(".png"))) {
                results.add(file.getName());
            }
        }

        String result = results.get((int) (Math.random() * results.size()));
        return Paths.get(folderName, result).toString();
    }

    public void paint(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, Content.FRAME_WIDTH, Content.FRAME_HEIGHT, this); // draw background
        super.paint(g);
    }
}
