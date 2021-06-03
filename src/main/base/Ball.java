package main.base;

import main.Content;
import java.awt.*;
import java.awt.event.*;

public class Ball extends Element {
    private float hitSpeedY; // when something intersects the ball, give the speed of Y direction to the ball
    private float hitSpeedX; /// when something intersects the ball, give the speed of X direction to the
                             /// ball
    private float currentSpeedY; // speed of y direction the ball
    private float currentSpeedX; // speed of x direction of the ball
    private int angle; // angle of the ball
    private boolean ifUp, ifDown; // set for true if player click the keyboard

    public Ball() {
        super((int) Content.FRAME_WIDTH / 4 - Content.ELEMENT_SIZE / 2, 200, "ball.png");
        this.width = Content.ELEMENT_SIZE / 2; // initialize the x position
        this.height = Content.ELEMENT_SIZE / 2; // initialize the y position
        this.hitSpeedY = -10;
        this.hitSpeedX = 1.1f;
        this.currentSpeedX = 0;
        this.currentSpeedY = 0;
        this.ifLeft = ifRight = ifUp = ifDown = false;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getAngle() {
        return angle;
    }

    public void spin() { // ball spins
        angle += 45;
    }

    public float getBallSpeedX() {
        return currentSpeedX;
    }

    public float getBallSpeedY() {
        return currentSpeedY;
    }

    public void hitGround() {
        currentSpeedY = hitSpeedY - 3.5f; // y position
    }

    public void hitStick() {
        currentSpeedX *= -1;
    }

    public void hitUpStick() {
        y = 275;
        currentSpeedY = Math.abs(currentSpeedY) * (-1) + 3;
    }

    @Override
    public void move() { // ball moves
        currentSpeedY += 0.2; // handle y speed, add gravity
        y += currentSpeedY;
        x += currentSpeedX;
        if (y >= Content.GROUND_Y + Content.ELEMENT_SIZE) {
            hitGround();
        } else if (y < 0) { // hit floor
            y = 0;
            currentSpeedY = 3;
        }

        if (x < 0 || x >= Content.FRAME_WIDTH - 150) {
            // handle x speed
            // if hit side, then reverse direction
            currentSpeedX *= -1;
        }
    }

    // ball hit something
    public void hit(float d, int player, boolean ifSmash) {
        currentSpeedY = hitSpeedY - 1;

        if (d != 0) {
            hitSpeedX = d * 0.07f; // x position
            if (d < 0)
                hitSpeedX -= 1;
            else
                hitSpeedX += 1;

            currentSpeedX = hitSpeedX;
        }

        // basic on player2
        if (ifSmash == true) {
            // press left or right
            if (ifLeft == true) {
                currentSpeedX = -14;
                currentSpeedY = 3;
            } else if (ifRight == true) {
                currentSpeedX = -12;
                currentSpeedY = 3;
            }

            // press up or down
            if (ifDown == true) {
                if (ifLeft == true || ifRight == true) {
                    if (player == 2)
                        currentSpeedX = Math.abs(currentSpeedX + 3) * (-1);
                    else
                        currentSpeedX = Math.abs(currentSpeedX) * (-1);
                } else {
                    if (player == 2)
                        currentSpeedX = Math.abs(currentSpeedX + 9) * (-1);
                    else
                        currentSpeedX = Math.abs(currentSpeedX + 1) * (-1);
                }
                currentSpeedY = 14;
            } else if (ifUp == true) {
                if (ifLeft == true || ifRight == true)
                    currentSpeedX = Math.abs(currentSpeedX) * (-1) - 4;
                else
                    currentSpeedX = Math.abs(currentSpeedX) * (-1) - 3;
                currentSpeedY = -17;
            }

            // press nothing
            if (ifLeft == false && ifRight == false && ifUp == false && ifDown == false) {
                currentSpeedX = -8;
                currentSpeedY = 3;
            }

            // revers direction if is player1
            if (player == 1) {
                currentSpeedX *= -1;
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                ifUp = true;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                ifDown = true;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                ifLeft = true;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                ifRight = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                ifUp = false;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                ifDown = false;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                ifLeft = false;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                ifRight = false;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void restart(int n) {
        if (n == 1) {
            x = (int) Content.FRAME_WIDTH / 4 * 3 - Content.ELEMENT_SIZE / 2;
            y = 200;
        } else {
            x = (int) Content.FRAME_WIDTH / 4 - Content.ELEMENT_SIZE / 2;
            y = 10;
        }
        currentSpeedX = 0;
        currentSpeedY = 0;
    }

}
