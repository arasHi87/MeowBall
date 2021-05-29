package main.panel;

import java.awt.event.*;
import java.text.MessageFormat;
import javax.swing.*;
import main.Content;
import main.base.Utils;

public class Character extends Base {
    private int width, height; // for every character block
    private int leftBorder, upBorder; // border for left side and up side
    private int blockWidth; // block width for every character block
    public int[] playerChoose; // player choose character for showing, 0 for player1, 1 for player2
    public JButton start; // button to start the game
    private JLabel[] playerChooseLabel, characterLabel; // use to showing player choose and all character

    public Character() {
        this.width = 150;
        this.height = 150;
        this.leftBorder = 100;
        this.upBorder = 50;
        this.blockWidth = (Content.FRAME_WIDTH - (this.leftBorder * 2)) / 5;
        this.start = new JButton("Start");
        this.playerChoose = new int[] { 0, 4 };
        this.playerChooseLabel = new JLabel[2];
        this.characterLabel = new JLabel[10];

        this.setFocusable(true);
        this.addKeyListener(new meowAdapter());
        this.paintAllCharacter();
        this.paintChooseLabel();
        this.setButton();
    }

    private void paintAllCharacter() {
        // setting all character JLabel
        for (int i = 1; i <= 2; i++) {
            for (int j = 1, index, x, y; j <= 5; j++) {
                index = (i - 1) * 5 + j;
                x = this.leftBorder + (j - 1) * this.blockWidth;
                y = Content.FRAME_HEIGHT / 4 + this.upBorder + (i - 1) * (this.blockWidth + this.upBorder);

                this.characterLabel[i - 1] = new JLabel();
                this.characterLabel[i - 1].setIcon(Utils.getResizeImage(
                        MessageFormat.format("characters/{0}/right.png", index), this.width, this.height));
                this.characterLabel[i - 1].setBounds(x, y, width, height);

                this.add(this.characterLabel[i - 1]);
            }
        }
    }

    private void paintChooseLabel() {
        // setting choose character JLabel
        for (int i = 0, x, y; i <= 1; i++) {
            x = (i == 0 ? this.leftBorder : Content.FRAME_WIDTH - this.leftBorder - this.blockWidth);
            y = Content.FRAME_HEIGHT / 8 - this.height / 2;

            this.playerChooseLabel[i] = new JLabel();
            this.playerChooseLabel[i].setIcon(
                    Utils.getResizeImage(MessageFormat.format("characters/{0}/right.png", this.playerChoose[i] + 1),
                            this.width, this.height));
            this.playerChooseLabel[i].setBounds(x, y, width, height);
            this.add(this.playerChooseLabel[i]);
        }
    }

    private void setButton() {
        // setting start button
        this.start.setBounds(Content.FRAME_WIDTH / 2 - 150, Content.FRAME_HEIGHT / 8 - 100, 300, 200);
        this.start.setOpaque(true);
        this.start.setBorder(null);
        this.start.setContentAreaFilled(false);
        // this.add(this.start);
    }

    private class meowAdapter extends KeyAdapter {
        // keyborad listener
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            int temp = 0;

            switch (code) {
                case KeyEvent.VK_W:
                case KeyEvent.VK_S:
                    playerChoose[0] = (playerChoose[0] + 5) % 10;
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    playerChoose[1] = (playerChoose[1] + 5) % 10;
                    break;

                case KeyEvent.VK_A:
                    if (playerChoose[0] > 4)
                        temp = 5;
                    playerChoose[0] = (playerChoose[0] + 4) % 5 + temp;
                    break;
                case KeyEvent.VK_LEFT:
                    if (playerChoose[1] > 4)
                        temp = 5;
                    playerChoose[1] = (playerChoose[1] + 4) % 5 + temp;
                    break;
                case KeyEvent.VK_D:
                    if (playerChoose[0] > 4)
                        temp = 5;
                    playerChoose[0] = (playerChoose[0] + 1) % 5 + temp;
                    break;
                case KeyEvent.VK_RIGHT:
                    if (playerChoose[1] > 4)
                        temp = 5;
                    playerChoose[1] = (playerChoose[1] + 1) % 5 + temp;
                    break;
            }
            playerChooseLabel[0].setIcon(Utils.getResizeImage(
                    MessageFormat.format("characters/{0}/right.png", playerChoose[0] + 1), width, height));
            playerChooseLabel[1].setIcon(Utils.getResizeImage(
                    MessageFormat.format("characters/{0}/right.png", playerChoose[1] + 1), width, height));
            repaint();
        }
    }
}
