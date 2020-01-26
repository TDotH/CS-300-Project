package fruPack;

//May be changed to player.java later on

import java.awt.event.KeyEvent;

public class Movement {
	
    //integers that hold the current position coordinates, as well as the map boundaries
    private int posX, posY, upBoundX, upBoundY, lowBoundX, lowBoundY;

    //Constructor: startX, startY, maxX, maxY, minX, minY
	public Movement( int posX, int posY, int maxX, int maxY, int minX, int minY ) {
		
		setPos( posX, posY );
		setBounds( maxX, maxY, minX, minY );
		
	}

    //sets position coordinates to specified arguments
    public void setPos(int startX, int startY) {
	    posX = startX;
	    posY = startY;
    }

    //set map boundary coordinates
    public void setBounds(int maxX, int maxY, int minX, int minY) {
        upBoundX = maxX;
        upBoundY = maxY;
        lowBoundX = minX;
        lowBoundY = minY;
    }

    //Keyboard events from Frupal.java
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
    
    public int getPosX() {
    	
    	return posX;
    }
    
    public int getPosY() {
    	
    	return posY;
    }
}
