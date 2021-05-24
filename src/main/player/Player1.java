package main.player;

import main.Content;
import main.base.Utils;
import java.awt.event.KeyEvent;

public class Player1 extends Player {
    public Player1() {
        super((int) Content.FRAME_WIDTH / 4 - Content.ELEMENT_SIZE / 2, Content.GROUND_Y);
        this.image = Utils.getImage("player1.png");
        this.up = KeyEvent.VK_W;
        this.left = KeyEvent.VK_A;
        this.right = KeyEvent.VK_D;
        this.leftBorder = 0;
        this.rightBorder = Content.STICK_X - 100;
    }

}
