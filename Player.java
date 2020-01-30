//package fruPack;

/*TODO
 * -Add energy and money
 * -Add inventory
 * -Add a way for the player to know what tile they've stepped on?
 */

import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
	
    //integers that hold the current position coordinates, as well as the map boundaries
    private int posX, posY, upBoundX, upBoundY;
    
    //Energy and money; unused until items are ready
    //private int energy, money;

    //Constructor: startX, startY, maxX, maxY, minX, minY
	public Player( int posX, int posY, int maxX, int maxY ) {
		
		setPos( posX, posY );
		setBounds( maxX - 1, maxY - 1);
		
	}

    //sets position coordinates to specified arguments
    public void setPos(int startX, int startY) {
	    posX = startX;
	    posY = startY;
    }

    //set map boundary coordinates
    public void setBounds(int maxX, int maxY) {
        upBoundX = maxX;
        upBoundY = maxY;
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

    //Checks if player is within bounds before moving
    public int moveNorth() {
        if((posY - 1) >= 0) {
            --posY;
            return 0;
        }
        return 1;
    }

    public int moveSouth() {
        if((posY + 1) <= upBoundY) {
            ++posY;
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
        if ((posX - 1) >= 0) {
            --posX;
            return 0;
        }
        return 1;
    }
    
    //Draws the player according to the given tile size and relative to the camera; offsets movement by tile size
    public void draw( Graphics2D g, int tile_size, Camera camera ) {
    	
    	int playerSz = (tile_size/2);
    	int offSetX = (tile_size/4);
    	int offSetY = (tile_size/4);
   
    	//Default render positions (at camera origin)
    	int tempPosX = camera.getWindowPosX() + offSetX;
    	int tempPosY = camera.getWindowPosY() + offSetY;
    	
    	g.setColor(Color.RED);
    	
    	//Set the player's position relative to the camera
    	tempPosX = camera.getWindowPosX() + tile_size * ( posX - camera.getCameraPosX() + camera.gettileMaxWidth()/2 ) + offSetX;
    	tempPosY = camera.getWindowPosY() + tile_size * ( posY - camera.getCameraPosY() + camera.gettileMaxHeight()/2 ) + offSetY;
    	
    	g.fillRect( tempPosX, tempPosY, playerSz, playerSz );
    	
    }
    
    public int getPosX() {
    	
    	return posX;
    }
    
    public int getPosY() {
    	
    	return posY;
    }
}
