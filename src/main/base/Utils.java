package main.base;

import javax.swing.ImageIcon;
import main.Content;
import java.awt.Image;

public abstract class Utils {
    /**
     * get the image
     * 
     * @param imageName
     * @return
     */
    public static Image getImage(String imageName) {
        return new ImageIcon(Content.RESOURCE_PATH + "images/" + imageName).getImage();
    }
}
