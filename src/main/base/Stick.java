package main.base;

import java.awt.Image;
import java.awt.Rectangle;

import main.Content;

public class Stick {
    private Image image;
    private int x, y;
    private int width, height;

    public Stick() {
        this.image = Utils.getImage("stick.jpg");
        this.x = Content.STICK_X;
        this.y = Content.STICK_Y;
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
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
