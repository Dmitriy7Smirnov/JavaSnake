package com.nicedev;

import java.awt.*;

public class BodyPart {
    private int xCoor, yCoor, weight, height;
    public BodyPart(int xCoor, int yCoor, int tileSize) {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        weight = tileSize;
        height = tileSize;
    }

    public void tick() {

    }

    public void draw(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(xCoor*weight, yCoor*height, weight, height);
        graphics.setColor(Color.GREEN);
        graphics.fillRect(xCoor*weight + 2, yCoor*weight + 2, weight - 4, height - 4);
    }
}
