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

    public void MoveByBall(int ball_x, int ball_y, float speed_x, float speed_y){
        float slope,predict_x;
        int hit_x = this.x;
        
        if (ifStart == true) 
        {

            if(speed_x==0)
                slope = 9999;
            else 
                slope = speed_y/speed_x;
            
            if(speed_y>0 && ball_x<550){
                predict_x = (580-ball_y+slope*ball_x)/slope;
                if(speed_x>0){
                    if(ball_x<410)
                        hit_x = this.x+150;
                    else
                        hit_x = this.x+(int)(Math.random()*40+40);
                }
                else
                    hit_x = this.x+65;
            }
            else{
                predict_x = 225;
            }
                
            
            if(hit_x - predict_x > 10){
                ifLeft = true;
                ifRight = false;
            }
            else if(hit_x - predict_x < -15){
                ifRight = true;
                ifLeft = false;
            }
            else{
                ifLeft = false;
                ifRight = false;
                dx = 0;
            }

            if(ball_x>500 && Math.abs(predict_x-hit_x)<15)
                dx = 0;        
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
