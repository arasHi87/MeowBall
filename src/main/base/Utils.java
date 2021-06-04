package main.base;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import main.Content;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Utils {
    /**
     * get the image
     * 
     * @param imageName
     * @return
     */
    public static Image getImage(String imageName) {
        return new ImageIcon(getImagePath(imageName)).getImage();
    }

    public static ImageIcon getResizeImage(String imageName, int width, int height) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(getImagePath(imageName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image resizeImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizeImage);
    }

    public static String getImagePath(String imageName) {
        return Content.RESOURCE_PATH + "images/" + imageName;
    }

    public static String getFontPath(String fontName) {
        return Content.RESOURCE_PATH + "font/" + fontName;
    }

    public static String getSoundPath(String soundName) {
        return Content.RESOURCE_PATH + "sounds/" + soundName + ".wav";
    }

    public static Font getFont(String fontName, int fontSize) throws FontFormatException, IOException {
        Font font = null;
        font = Font.createFont(Font.TRUETYPE_FONT, new File(Utils.getFontPath("8bit16.ttf")));
        font = font.deriveFont(java.awt.Font.PLAIN, fontSize);

        return font;
    }
}
