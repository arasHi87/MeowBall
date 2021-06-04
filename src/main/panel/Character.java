package main.panel;

import java.security.SecureRandom;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.MessageFormat;
import javax.swing.*;
import javax.swing.border.*;
import main.Content;
import main.Main;
import main.base.Utils;

public class Character extends Base {
    private int width, height; // for every character block
    private int leftBorder, upBorder; // border for left side and up side
    private int blockWidth; // block width for every character block
    private String gameMode; // mode for game, have single and multiple
    public int[] playerChoose; // player choose character for showing, 0 for player1, 1 for player2
    public JButton start; // button to start the game
    private JLabel[] playerChooseLabel, characterLabel;// use to showing player choose and all character
    private TitledBorder[] playerChooseBorder;

    public Character(Main frame, String gameMode) throws FontFormatException, IOException {
        super(frame);

        // basic setting
        width = 150;
        height = 150;
        leftBorder = 100;
        upBorder = 50;
        blockWidth = (Content.FRAME_WIDTH - (leftBorder * 2)) / 5;
        playerChoose = new int[] { 0, 4 };
        playerChooseLabel = new JLabel[2];
        characterLabel = new JLabel[10];
        backgroundImage = Utils.getImage(getBackgroundImage("background/start"));
        playerChooseBorder = new TitledBorder[2];

        // setting game mode, and check if is single then random 2p character choose
        if ((this.gameMode = gameMode) == "single") {
            SecureRandom random = new SecureRandom();
            playerChoose[1] = 1 + random.nextInt(9);
        }

        // other setting
        setFocusable(true);
        addKeyListener(new meowAdapter());
        setBorder();
        paintAllCharacter();
        paintChooseLabel();

        // setting button
        start = new JButton("Start");
        start.addActionListener(new startListener());
        start.setBounds(Content.FRAME_WIDTH / 2 - 150, Content.FRAME_HEIGHT / 8 - 100, 300, 200);
        start.setOpaque(true);
        start.setBorder(null);
        start.setContentAreaFilled(false);

        // add componet
        add(start);
        setVisible(true);
    }

    public class startListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == start) {
                String player1Image = MessageFormat.format("characters/{0}/right.png", playerChoose[0] + 1);
                String player2Image = MessageFormat.format("characters/{0}/left.png", playerChoose[1] + 1);
                switchPanel(new Game(frame, gameMode, player1Image, player2Image));
            }
        }
    }

    private void setBorder() throws FontFormatException, IOException {
        Color colors[] = { new Color(233, 51, 55), new Color(80, 136, 247) };
        String title[] = { "P1", "P2" };

        // init player choose border
        for (int i = 0; i <= 1; i++) {
            playerChooseBorder[i] = new TitledBorder(new LineBorder(colors[i], 5), title[i]);
            playerChooseBorder[i].setTitleFont(Utils.getFont("8bit16.ttf", 30));
            playerChooseBorder[i].setTitleColor(colors[i]);
        }
    }

    private void paintAllCharacter() {
        // setting all character JLabel
        for (int i = 1; i <= 2; i++) {
            for (int j = 1, index, x, y; j <= 5; j++) {
                index = (i - 1) * 5 + j;
                x = leftBorder + (j - 1) * blockWidth;
                y = Content.FRAME_HEIGHT / 4 + upBorder + (i - 1) * (blockWidth + upBorder);

                characterLabel[index - 1] = new JLabel();
                characterLabel[index - 1].setIcon(Utils.getResizeImage(
                        MessageFormat.format("characters/{0}/right.png", index), width - 20, height - 20));
                characterLabel[index - 1].setBounds(x, y, width, height);

                add(characterLabel[index - 1]);
            }
        }

        // paint player choose label's border
        characterLabel[playerChoose[0]].setBorder(playerChooseBorder[0]);
        characterLabel[playerChoose[1]].setBorder(playerChooseBorder[1]);
    }

    private void paintChooseLabel() {
        // setting choose character JLabel
        for (int i = 0, x, y; i <= 1; i++) {
            x = (i == 0 ? leftBorder : Content.FRAME_WIDTH - leftBorder - blockWidth);
            y = Content.FRAME_HEIGHT / 8 - height / 2;

            playerChooseLabel[i] = new JLabel();
            playerChooseLabel[i].setIcon(Utils.getResizeImage(
                    MessageFormat.format("characters/{0}/right.png", playerChoose[i] + 1), width, height));
            playerChooseLabel[i].setBounds(x, y, width, height);
            add(playerChooseLabel[i]);
        }
    }

    private void moveBorder(int contentIndex, int showIndex, TitledBorder addBorder) {
        characterLabel[contentIndex].setBorder(null);
        characterLabel[showIndex].setBorder(addBorder);
    }

    private class meowAdapter extends KeyAdapter {
        // keyborad listener
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            int temp = 0;
            int originChoose[] = new int[] { playerChoose[0], playerChoose[1] };

            // if game mode is single then 2p cant choose character
            if (gameMode == "single" && (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_LEFT
                    || code == KeyEvent.VK_RIGHT))
                return;

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

            if (playerChoose[0] == playerChoose[1]) {
                // 2 player can't choose same character, reverse the operator
                playerChoose = originChoose;
            } else {
                for (int i = 0; i <= 1; i++) {
                    playerChooseLabel[i].setIcon(Utils.getResizeImage(
                            MessageFormat.format("characters/{0}/right.png", playerChoose[i] + 1), width, height));
                    moveBorder(originChoose[i], playerChoose[i], playerChooseBorder[i]);
                }
            }

            revalidate();
            repaint();
        }
    }
}
