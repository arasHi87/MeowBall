package main.base;

import java.awt.Image;
import java.awt.Rectangle;
import main.Content;

public class Stick {
    private Image image;
    private int x, y;
    private int width, height;

    public Stick() {
        image = Utils.getImage("stick.jpg");
        x = Content.STICK_X;
        y = Content.STICK_Y;
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
