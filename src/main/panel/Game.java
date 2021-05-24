package main.panel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.util.*;
import javax.swing.JPanel;
import main.Content;
import main.base.*;
import main.base.Record;
import main.player.*;

public class Game extends JPanel {
    private Image backgroundImage;
    private Player1 player1;
    private Player2 player2;
    private Ball ball;
    private Stick stick;
    private Timer timer; // control the game cycle
    private Timer timer2; // control the ball cycle
    private Timer timer3; // control the hide task
    // private int[] leftLine, rightLine, leftStickLine, rightStickLine,
    // upStickLine;
    private Record record;
    private Line2D.Double leftLine, rightLine, leftStickLine, rightStickLine, upStickLine;
    private int roundWin; // if 1 than 1P win, 2 than 2p win

    public Game() {
        this.backgroundImage = Utils.getImage("background.jpg");
        this.addKeyListener(new tempAdapter());
        this.addMouseListener(new testAdapter());
        this.setSize(Content.FRAME_WIDTH, Content.FRAME_HEIGHT);
        this.setFocusable(true); // set for controlling the component on the game board
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // memory associated
        this.setLayout(null);
        this.setOpaque(false);

        this.player1 = new Player1();
        this.player2 = new Player2();
        this.ball = new Ball();
        this.stick = new Stick();
        this.record = new Record();
        this.add(record);

        this.leftLine = new Line2D.Double(0, Content.GROUND_Y + Content.ELEMENT_SIZE, Content.STICK_X,
                Content.GROUND_Y + Content.ELEMENT_SIZE);
        this.rightLine = new Line2D.Double(Content.STICK_X, Content.GROUND_Y + Content.ELEMENT_SIZE,
                Content.FRAME_WIDTH, Content.GROUND_Y + Content.ELEMENT_SIZE);
        this.leftStickLine = new Line2D.Double(Content.STICK_X, Content.STICK_Y, Content.STICK_X,
                Content.STICK_Y + 225);
        this.rightStickLine = new Line2D.Double(Content.STICK_X + 17, Content.STICK_Y, Content.STICK_X + 17,
                Content.STICK_Y + 225);
        this.upStickLine = new Line2D.Double(Content.STICK_X, Content.STICK_Y, Content.STICK_X + 16, Content.STICK_Y);

        timer = new Timer();
        timer2 = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 12);
        timer2.scheduleAtFixedRate(new BallTask(), 1000, 100);

        roundWin = 0;
    }

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

    private class testAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            System.out.print(e.getX());
            System.out.print(' ');
            System.out.println(e.getY());
        }
    }

    private class tempAdapter extends KeyAdapter { // 按鍵的listener
        public void keyPressed(KeyEvent e) {
            player1.keyPressed(e);
            player2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            player1.keyReleased(e);
            player2.keyReleased(e);
        }
    }

    class ScheduleTask extends TimerTask { // 整個遊戲的運行
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

    class RestartTask extends TimerTask { // 球落在右邊，1P贏
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

        if (r1.intersects(r2)) { // pika1撞球
            float d = ((float) ball.getX() + (float) ball.getWidth() / 2f)
                    - ((float) player1.getX() + (float) player1.getWidth() / 2f);
            ball.hit(d, 1);
        } else if (r4.intersects(r2)) { // pika2撞球
            float d = ((float) ball.getX() + (float) ball.getWidth() / 2f)
                    - ((float) player2.getX() + (float) player2.getWidth() / 2f);
            ball.hit(d, 2);
        }
        if (r2.intersectsLine(upStickLine)) { // 撞到柱子上面
            ball.hitUpStick();
        } else if (r2.intersectsLine(rightStickLine) || r2.intersectsLine(leftStickLine)) { // 撞到柱子左邊和右邊
            ball.hitStick();
        }

        if (r2.intersectsLine(leftLine) || r2.intersectsLine(rightLine)) { // 球落地
            if (r2.intersectsLine(leftLine)) { // 球落在左邊，2P贏
                System.out.println("Player2 win");
                record.plusCount2();
                roundWin = 2;
            } else if (r2.intersectsLine(rightLine)) { // 球落在右邊，1P贏
                System.out.println("Player1 win");
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
