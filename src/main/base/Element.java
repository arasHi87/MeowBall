package main.base;

import java.awt.*;
import main.Content;

public class Element {
    protected int dx, x, y, ox, oy; // accelerate and position and original point
    protected int width, height; // width and height of image
    protected float currentSpeed; // the speed when jump up and current speed
    protected Image image; // default image
    protected boolean ifLeft, ifRight; // set for true if player click the keyboard

    public Element() {
        width = Content.ELEMENT_SIZE;
        height = Content.ELEMENT_SIZE;
    }

    public Element(int x, int y, String imageName) {
        width = Content.ELEMENT_SIZE;
        height = Content.ELEMENT_SIZE;
        image = Utils.getImage(imageName);
        this.x = ox = x;
        this.y = oy = y;
        ifLeft = ifRight = false;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    public void moveLeft() {
        dx = -3;
    }

    public void moveRight() {
        dx = 3;
    }

    public void move() {
        /**
         * move function, when uses keyboard to control, must be override in child
         */
    }

    public void restart() {
        /**
         * restart game, re init, must override in child
         */
    }
}
