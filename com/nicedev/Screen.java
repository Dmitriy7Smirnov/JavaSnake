package com.nicedev;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Screen extends JPanel implements Runnable {
    public static int WIDTH = 800, HEIGHT = 800;
    private Thread thread;
    private boolean running = false;
    private BodyPart bodyPart;
    private ArrayList<BodyPart> snake;
    private int xCoord = 10, yCoord = 10;
    private int size = 5;
    private boolean right = true, left = false, up = false, down = false;
    private int ticks = 0;

    public Screen() {
        //super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        snake = new ArrayList<BodyPart>();
        start();
    }

    public void tick() {
        if (snake.size() == 0) {
            bodyPart = new BodyPart(xCoord, yCoord, 10);
            snake.add(bodyPart);
        }
        ticks++;

        if (ticks > 250000) {
            if (right) xCoord++;
            if (left) xCoord--;
            if (up) yCoord--;
            if (down) yCoord++;

            ticks = 0;

            bodyPart = new BodyPart(xCoord, yCoord, 10);
            snake.add(bodyPart);
        }
    }

    public void paint(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        for (int i=0; i < WIDTH / 10; i++) {
            graphics.drawLine(i * 10, 0, i * 10, HEIGHT);
        }
        for (int i=0; i < HEIGHT / 10; i++) {
            graphics.drawLine(0, i * 10, WIDTH, i * 10);
        }
        for (int i = 0; i < snake.size(); i++) {
            snake.get(i).draw(graphics);
        }
    }

    public void start() {
        running = true;
        thread = new Thread(this, "Game loop");
        thread.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            tick();
            repaint();
        }
    }
}
