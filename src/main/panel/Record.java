package main.panel;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import main.Content;

public class Record extends JPanel {
    private Timer timer;
    private int width, height;
    private int x, y;
    public int count1, count2;
    private JLabel countLabel1, countLabel2;
    JLabel endLabel;

    public Record() {
        countLabel1 = new JLabel("0");
        countLabel2 = new JLabel("1");
        endLabel = new JLabel("", SwingConstants.CENTER);
        width = Content.FRAME_WIDTH;
        height = 500;
        x = 0;
        y = 0;
        count1 = 0;
        count2 = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 0, 100);

        setSize(width, height);
        setLayout(null);
        setOpaque(false);
        setVisible(true);

        countLabel1.setBounds((int) Content.FRAME_WIDTH / 4 - Content.ELEMENT_SIZE / 2 + 50, 100, 100, 80);
        countLabel1.setForeground(new Color(0, 0, 0));

        countLabel2.setBounds((int) Content.FRAME_WIDTH / 4 * 3 - Content.ELEMENT_SIZE / 2 + 50, 100, 100, 80);
        countLabel2.setForeground(new Color(255, 255, 255));

        // endLabel.setPreferredSize(new Dimension(640, 80));
        endLabel.setBounds((Content.FRAME_WIDTH - 640) / 2, 100, Content.FRAME_WIDTH / 2, 400);
        endLabel.setForeground(new Color(0, 230, 118));

        add(countLabel1);
        add(countLabel2);
        validate();
    }

    public int getX() {
        return x;
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

    class ScheduleTask extends TimerTask {
        public void run() {
            countLabel1.setText(Integer.toString(count1));
            countLabel2.setText(Integer.toString(count2));
        }
    }

    public void plusCount1() {
        count1 += 1;
    }

    public void plusCount2() {
        count2 += 1;
    }

    public void showMessage() {
        countLabel1.setText(Integer.toString(count1));
        countLabel2.setText(Integer.toString(count2));
    }

    public void showEndMessage(String winner) {
        if (winner.equals("player1"))
            endLabel.setForeground(new Color(0, 0, 0));
        else
            endLabel.setForeground(new Color(255, 255, 255));

        endLabel.setText(winner);
        add(endLabel);
        revalidate();
    }

    public void restart() {
        count1 = 0;
        count2 = 0;
        endLabel.setText("");
        revalidate();
    }
}
