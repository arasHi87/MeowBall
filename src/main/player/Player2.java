package main.player;

import main.Content;
import main.base.Utils;
import java.awt.event.KeyEvent;

public class Player2 extends Player {
    public Player2() {
        super((int) Content.FRAME_WIDTH / 4 * 3 - Content.ELEMENT_SIZE / 2, Content.GROUND_Y);
        this.image = Utils.getImage("player2.png");
        this.up = KeyEvent.VK_UP;
        this.left = KeyEvent.VK_LEFT;
        this.right = KeyEvent.VK_RIGHT;
        this.leftBorder = Content.STICK_X + 20;
        this.rightBorder = Content.FRAME_WIDTH - 150;
    }
}
