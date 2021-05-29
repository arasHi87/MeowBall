package main.panel;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.*;
import main.Content;
import main.base.Utils;

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
        g.drawImage(this.backgroundImage, 0, 0, Content.FRAME_WIDTH, Content.FRAME_HEIGHT, this); // draw background
        super.paint(g);
    }
}
