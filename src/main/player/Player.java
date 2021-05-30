package main.player;

import java.awt.*;
import java.awt.event.KeyEvent;
import main.Content;
import main.base.*;

public class Player extends Element {
    protected float jumpSpeed; // the speed when jump up and current speed
    public boolean ifJump; // jump or not
    protected int up, left, right; // direction for player
    public boolean ifSmash;
    protected int smash;      
    protected int leftBorder, rightBorder;
    public boolean ifStart;

    /**
     * 
     * @param x
     * @param y
     * @param player    judge if player1 or player2
     * @param imageName image use to paint
     */
    public Player(int x, int y, String player, String imageName) {
        super(x, y, imageName);
        this.ifJump = false;
        this.jumpSpeed = -10;
        this.ifStart = true;

        // different setting for both player
        if (player.equals("player1")) {
            this.up = KeyEvent.VK_W;
            this.left = KeyEvent.VK_A;
            this.right = KeyEvent.VK_D;
            this.smash = KeyEvent.VK_Q;
            this.leftBorder = 0;
            this.rightBorder = Content.STICK_X - 125;
        } else {
            this.up = KeyEvent.VK_UP;
            this.left = KeyEvent.VK_LEFT;
            this.right = KeyEvent.VK_RIGHT;
            this.smash = KeyEvent.VK_SHIFT;
            this.leftBorder = Content.STICK_X + 0;
            this.rightBorder = Content.FRAME_WIDTH - 150;
        }

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

            if (key == this.smash) {
                ifSmash = true;
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

        if (key ==this.smash){
            ifSmash = false;
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
