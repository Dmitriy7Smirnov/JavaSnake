package com.nicedev;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Screen extends JPanel implements Runnable {
    public static int WIDTH = 800, HEIGHT = 800;
    private Thread thread;
    private boolean running = false;
    private BodyPart bodyPart;
    private ArrayList<BodyPart> snake;
    private Apple apple;
    private ArrayList<Apple> apples;
    private Random random;
    private int xCoor = 10, yCoord = 10;
    private int size = 5;
    private boolean right = true, left = false, up = false, down = false;
    private int ticks = 0;
    private Key key;

    public Screen() {
        setFocusable(true);
        key = new Key();
        addKeyListener(key);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        snake = new ArrayList<BodyPart>();
        apples = new ArrayList<Apple>();
        random = new Random();
        start();
    }

    public void tick() {
        if (snake.size() == 0) {
            bodyPart = new BodyPart(xCoor, yCoord, 10);
            snake.add(bodyPart);
        }
        if (apples.size() == 0) {
            int xCoor = random.nextInt(79);
            int yCoord = random.nextInt(79);
            apple = new Apple(xCoor, yCoord, 10);
            apples.add(apple);
        }
        for (int i = 0; i < apples.size(); i++) {
            if (xCoor == apples.get(i).getxCoor() && yCoord == apples.get(i).getyCoor()) {
                size++;
                apples.remove(i);
                i--;
            }
         }
        for (int i = 0; i < snake.size(); i++) {
            if (xCoor == snake.get(i).getxCoor() && yCoord == snake.get(i).getyCoor()) {
                if (i != snake.size() - 1) stop();
            }
        }

        if (xCoor < 0 || xCoor > 79 || yCoord < 0 || yCoord > 79) stop();

        ticks++;

        if (ticks > 250000) {
            if (right) xCoor++;
            if (left) xCoor--;
            if (up) yCoord--;
            if (down) yCoord++;

            ticks = 0;

            bodyPart = new BodyPart(xCoor, yCoord, 10);
            snake.add(bodyPart);

            if (snake.size() > size) {
                snake.remove(0);
            }
        }
    }

    public void paint(Graphics graphics) {
        graphics.clearRect(0, 0, WIDTH, HEIGHT);
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
        for (int i = 0; i < apples.size(); i++) {
            apples.get(i).draw(graphics);
        }
    }

    public void start() {
        running = true;
        thread = new Thread(this, "Game loop");
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            tick();
            repaint();
        }
    }

    private class Key implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_RIGHT && !left) {
                up = false;
                down = false;
                right = true;
            }
            if (key == KeyEvent.VK_LEFT && !right) {
                up = false;
                down = false;
                left = true;
            }
            if (key == KeyEvent.VK_UP && !down) {
                left = false;
                right = false;
                up = true;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                left = false;
                right = false;
                down = true;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) { }
    }

}
