package main.player;

import main.base.Element;

public class Arrow extends Element {
    public Arrow(int x, int y, String imageName) {
        super(x, y, imageName);
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
