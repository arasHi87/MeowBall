package main.panel;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.Timer;
import javax.swing.*;
import main.Content;
import main.base.*;
import main.player.*;

public class Game extends Base {
    private Player player1;
    private Player player2;
    private Ball ball;
    private Stick stick;
    private Timer timer; // control the game cycle
    private Timer timer2; // control the ball cycle
    private Timer timer3; // control the hide task
    private Record record;
    private Line2D.Double leftLine, rightLine, leftStickLine, rightStickLine, upStickLine;
    private int roundWin; // if 1 than 1P win, 2 than 2p win

    public Game(String player1Image, String player2Image) {
        // basic setting
        this.roundWin = 0;
        this.backgroundImage = Utils.getImage(this.getBackgroundImage("background/game"));
        this.addKeyListener(new tempAdapter());

        // setting object
        this.player1 = new Player((int) Content.FRAME_WIDTH / 4 - Content.ELEMENT_SIZE / 2, Content.GROUND_Y, "player1",
                player1Image);
        this.player2 = new Player((int) Content.FRAME_WIDTH / 4 * 3 - Content.ELEMENT_SIZE / 2, Content.GROUND_Y,
                "player2", player2Image);
        this.ball = new Ball();
        this.stick = new Stick();
        this.record = new Record();
        this.add(record);

        // setting border hit border
        this.leftLine = new Line2D.Double(0, Content.GROUND_Y + Content.ELEMENT_SIZE, Content.STICK_X,
                Content.GROUND_Y + Content.ELEMENT_SIZE);
        this.rightLine = new Line2D.Double(Content.STICK_X, Content.GROUND_Y + Content.ELEMENT_SIZE,
                Content.FRAME_WIDTH, Content.GROUND_Y + Content.ELEMENT_SIZE);
        this.leftStickLine = new Line2D.Double(Content.STICK_X, Content.STICK_Y, Content.STICK_X,
                Content.STICK_Y + 225);
        this.rightStickLine = new Line2D.Double(Content.STICK_X + 17, Content.STICK_Y, Content.STICK_X + 17,
                Content.STICK_Y + 225);
        this.upStickLine = new Line2D.Double(Content.STICK_X, Content.STICK_Y, Content.STICK_X + 16, Content.STICK_Y);

        // timer setting
        timer = new Timer();
        timer2 = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 500, 12);
        timer2.scheduleAtFixedRate(new BallTask(), 500, 100);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(this.backgroundImage, 0, 0, 1200, 700, this); // draw background
        super.paint(g);

        Graphics2D g2D = (Graphics2D) g.create();

        // draw player
        g2D.drawImage(player1.getImage(), player1.getX(), player1.getY(), Content.ELEMENT_SIZE, Content.ELEMENT_SIZE,
                this);
        g2D.drawImage(player2.getImage(), player2.getX(), player2.getY(), Content.ELEMENT_SIZE, Content.ELEMENT_SIZE,
                this);

        // draw object
        g2D.drawImage(stick.getImage(), stick.getX(), stick.getY(), 17, 225, this);
        g2D.rotate(Math.toDegrees(ball.getAngle()), ball.getX() + ball.getWidth() / 2,
                ball.getY() + ball.getHeight() / 2);

        g2D.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), this);

        Toolkit.getDefaultToolkit().sync();
        g2D.dispose(); // close the g2D draw
        g.dispose();
    }

    private class tempAdapter extends KeyAdapter {
        // keyborad listener
        public void keyPressed(KeyEvent e) {
            player1.keyPressed(e);
            player2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            player1.keyReleased(e);
            player2.keyReleased(e);
        }
    }

    class ScheduleTask extends TimerTask {
        // matain all game running
        public void run() {
            player1.ifStart = true;
            player2.ifStart = true;
            player1.move();
            player2.move();
            ball.move();
            collision();
            repaint();
        }
    }

    class BallTask extends TimerTask {
        public void run() {
            ball.spin();
            repaint();
        }
    }

    class RestartTask extends TimerTask {
        public void run() {
            if (roundWin == 1) {
                ball.restart(2);
            } else {
                ball.restart(1);
            }
            player1.restart();
            player2.restart();
            timer = new Timer();
            timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 12);
            timer3.cancel();
        }
    }

    class HideTask extends TimerTask {
        public void run() {
            timer3.cancel();
            timer3 = new Timer();
            timer3.schedule(new RestartTask(), 0);
        }
    }

    private void collision() {
        Rectangle r1 = player1.getBounds();
        Rectangle r2 = ball.getBounds();
        Rectangle r4 = player2.getBounds();

        if (r1.intersects(r2)) {
            // player1 hit the ball
            float d = ((float) ball.getX() + (float) ball.getWidth() / 2f)
                    - ((float) player1.getX() + (float) player1.getWidth() / 2f);
            ball.hit(d, 1);
        } else if (r4.intersects(r2)) {
            // player2 hit the ball
            float d = ((float) ball.getX() + (float) ball.getWidth() / 2f)
                    - ((float) player2.getX() + (float) player2.getWidth() / 2f);
            ball.hit(d, 2);
        }
        if (r2.intersectsLine(upStickLine)) {
            // hit the up stick
            ball.hitUpStick();
        } else if (r2.intersectsLine(rightStickLine) || r2.intersectsLine(leftStickLine)) {
            // hit the left/right side stick
            ball.hitStick();
        }

        if (r2.intersectsLine(leftLine) || r2.intersectsLine(rightLine)) {
            // ball hit the ground
            if (r2.intersectsLine(leftLine)) {
                // ball hit left ground, 2P win
                record.plusCount2();
                roundWin = 2;
            } else if (r2.intersectsLine(rightLine)) {
                // ball hit right ground, 1P win
                record.plusCount1();
                roundWin = 1;
            }
            timer.cancel();
            timer3 = new Timer();
            timer3.schedule(new HideTask(), 700, 15);

            player1.ifStart = false;
            player2.ifStart = false;
        }
    }
}
