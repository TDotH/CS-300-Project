package com.Frupal;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Movement {

    //integers that hold the current position coordinates, as well as the map boundaries
    private int posX, posY, upBoundX, upBoundY, lowBoundX, lowBoundY;

    //initializes position coordinates to specified arguments
    public void initPos(int startX, int startY) {
    posX = startX;
    posY = startY;
    }

    //initializes map boundary coordinates
    public void initBounds(int maxX, int maxY, int minX, int minY) {
        upBoundX = maxX;
        upBoundY = maxY;
        lowBoundX = minX;
        lowBoundY = minY;
    }

    @SuppressWarnings("serial")
    public class KeyboardExample extends JPanel {

        public KeyboardExample() {
            KeyListener listener = new MyKeyListener();
            addKeyListener(listener);
            setFocusable(true);
        }

        public class MyKeyListener implements KeyListener {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            moveWest();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            moveEast();
        if(e.getKeyCode() == KeyEvent.VK_UP)
            moveNorth();
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            moveSouth();
    }

    public int moveNorth() {
        if((posY + 1) <= upBoundY) {
            ++posY;
            return 0;
        }
        return 1;
    }

    public int moveSouth() {
        if((posY - 1) >= lowBoundY) {
            --posY;
            return 0;
        }
        return 1;
    }

    public int moveEast() {
        if((posX + 1) <= upBoundX) {
            ++posX;
            return 0;
        }
        return 1;
    }

    public int moveWest() {
        if ((posX - 1) >= lowBoundX) {
            --posX;
            return 0;
        }
        return 1;
    }
}
