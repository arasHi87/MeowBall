package main.base;

import main.Content;
import java.awt.*;

public class Ball extends Element {
    private float hitSpeedY; // when something intersects the ball, give the speed of Y direction to the ball
    private float hitSpeedX; /// when something intersects the ball, give the speed of X direction to the
                             /// ball
    private float currentSpeedY; // speed of y direction the ball
    private float currentSpeedX; // speed of x direction of the ball
    private int angle; // angle of the ball

    public Ball() {
        super((int) Content.FRAME_WIDTH / 4 - Content.ELEMENT_SIZE / 2, 200, "ball.png");
        this.width = Content.ELEMENT_SIZE / 2; // initialize the x position
        this.height = Content.ELEMENT_SIZE / 2; // initialize the y position
        this.hitSpeedY = -10;
        this.hitSpeedX = 1.1f;
        this.currentSpeedX = 0;
        this.currentSpeedY = 0;
        this.ifLeft = ifRight = false;
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

    public float GetBallSpeedX(){
        return currentSpeedX;
    }

    public float GetBallSpeedY(){
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
    public void hit(float d, int player) {
        currentSpeedY = hitSpeedY - 1; // handle y speed
        if (d != 0) {
            hitSpeedX = d * 0.07f; // x position
            if (d < 0)
                hitSpeedX -= 1;
            else
                hitSpeedX += 1;

            currentSpeedX = hitSpeedX;
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
