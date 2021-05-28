package main.player;

import java.awt.*;
import java.awt.event.KeyEvent;
import main.Content;
import main.base.*;

public class Player extends Element {
    protected float jumpSpeed; // the speed when jump up and current speed
    protected boolean ifJump; // jump or not
    protected int up, left, right; // direction for player
    protected int leftBorder, rightBorder;
    public boolean ifStart;

    Player(int x, int y) {
        super(x, y);
        this.ifJump = false;
        this.jumpSpeed = -10;
        this.ifStart = true;
    }

    public void move() {
        if (ifLeft == true) {
            moveLeft();
        }
        if (ifRight == true) {
            moveRight();
        }

        x += dx;
        if (ifJump == true) {
            currentSpeed += 0.2;
            y += currentSpeed;
        }
        if (y == Content.GROUND_Y) {
            ifJump = false;
        }
        if (x < this.leftBorder || x > rightBorder) {
            x -= dx;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (ifStart == true) {
            int key = e.getKeyCode();

            if (key == this.up && ifJump == false) {
                ifJump = true;
                currentSpeed = jumpSpeed;
            }

            if (key == this.left) {
                ifLeft = true;
            }

            if (key == this.right) {
                ifRight = true;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == this.left) {
            ifLeft = false;
            dx = 0;
        }

        if (key == this.right) {
            ifRight = false;
            dx = 0;
        }
    }

    public void restart() {
        x = this.ox;
        y = this.oy;
        ifJump = false;
        ifLeft = ifRight = false;
        ifStart = false;
    }

    public Rectangle getBounds() {
        return new Rectangle(x + 20, y + 20, width - 20, height - 20);
    }
}
