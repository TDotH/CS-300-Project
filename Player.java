//

package fruPack;

//May be changed to player.java later on

import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Player {
	
    //integers that hold the current position coordinates, as well as the map boundaries
    private int posX, posY, upBoundX, upBoundY;
    
    //Energy and money
    private int energy, money;

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
        if ((posX + 1) >= 0) {
            --posX;
            return 0;
        }
        return 1;
    }
    
    //Draws the player according to the given tile size; offsets movement by tile size
    public void draw( Graphics2D g, int tile_size ) {
    	
    	int playerSz = (tile_size/2);
    	int offSetX = (tile_size/4) + tile_size * posX;
    	int offSetY = (tile_size/4) + tile_size * posY;
   
    	g.setColor(Color.RED);
    	
    	g.fillRect( offSetX, offSetY, playerSz, playerSz);
    	
    }
    
    public int getPosX() {
    	
    	return posX;
    }
    
    public int getPosY() {
    	
    	return posY;
    }
}
