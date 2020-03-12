package com.player;

/*TODO
 * -Add energy and money
 * -Add inventory
 * -Add a way for the player to know what tile they've stepped on?
 */

import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Graphics2D;
import com.map.*;
import com.eventlog.*;

public class Player implements Objects {

    //integers that hold the current position coordinates, as well as the map boundaries
    private int posX, posY, upBoundX, upBoundY;
    
    //Player's energy
    private int energy;
    private EventLog eventLog;

    //Energy and money; unused until items are ready
    //private int energy, money;
    
    //Empty Constructor
    public Player() {}

    //Constructor: startX, startY, maxX, maxY, minX, minY
    public Player( int posX, int posY, int maxX, int maxY, EventLog eventLog ) {

        setPos( posX, posY );
        setBounds( maxX - 1, maxY - 1);
        energy = 10000;
        this.eventLog = eventLog;//point to eventLog

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

    //returns an int greater than 0 (energy spent) if movement possible, 0 if obstacle is in the way,
    //-1 if shopkeeper tile is encountered, -2 if caverns (jewels, gameOver) are encountered
    //returns -3 if player tries to move out of bounds (no movement occurs)
    public void keyPressed(KeyEvent e, Map map ) {
    	
    	switch ( e.getKeyCode() ) {
    	
    		case KeyEvent.VK_LEFT: 
    			
    			//Is the player at the bounds?
                if ((posX - 1) >= 0) {
                	
                	//Get the type of the tile that will be moved to
                	Types tileType = map.get_tile( posX - 1, posY ).getType();
                	//Is the next tile passable?
                	if ( tileType.getPassable() == true ) {
                		//Move the player and take away from the player's energy count
                		posX = posX - 1;
                		eventLog.update(map.get_tile(this.getPosX(), this.getPosY()), this);
                		energy -= tileType.getEnergyCost();
                	}
                	else {
                		//Do nothing for now
                	}
                	
                } else {
                	//Do nothing
                }
                    break;
			case KeyEvent.VK_RIGHT: 
				
	            if ((posX + 1) <= upBoundX) {
                	
                	//Get the type of the tile that will be moved to
                	Types tileType = map.get_tile( posX + 1, posY ).getType();
                	//Is the next tile passable?
                	if ( tileType.getPassable() == true ) {
                		//Move the player and take away from the player's energy count
                		posX = posX + 1;
						eventLog.update(map.get_tile(this.getPosX(), this.getPosY()), this);
                		energy -= tileType.getEnergyCost();
                	}
                	else {
                		//Do nothing for now
                	}
                	
                } else {
                	//Do nothing
	            }
	            break;
	            
				case KeyEvent.VK_UP: 
					
		            if ((posY - 1) >= 0) {
		            	
		              	//Get the type of the tile that will be moved to
	                	Types tileType = map.get_tile( posX, posY - 1 ).getType();
	                	//Is the next tile passable?
	                	if ( tileType.getPassable() == true ) {
	                		//Move the player and take away from the player's energy count
	                		posY = posY - 1;
							eventLog.update(map.get_tile(this.getPosX(), this.getPosY()), this);
	                		energy -= tileType.getEnergyCost();
	                	}
	                	else {
	                		//Do nothing for now
	                	}
	                	
	                } else {
	                	//Do nothing
		            }
		            break;
		                
					case KeyEvent.VK_DOWN: 
			            if ((posY + 1 <= upBoundY )) {
			            	
			              	//Get the type of the tile that will be moved to
		                	Types tileType = map.get_tile( posX, posY + 1 ).getType();
		                	//Is the next tile passable?
		                	if ( tileType.getPassable() == true ) {
		                		//Move the player and take away from the player's energy count
		                		posY = posY + 1;
								eventLog.update(map.get_tile(this.getPosX(), this.getPosY()), this);
		                		energy -= tileType.getEnergyCost();
		                	}
		                	else {
		                		//Do nothing for now
		                	}
		                	
		                } else {
		                	//Do nothing
			            }
			        break;
	                default:
	                	throw new IllegalStateException("Unexpected value!");
	            }
    }


    //Draws the player according to the given tile size and relative to the camera; offsets movement by tile size
    public void draw( Graphics2D g, int tile_size, Camera camera ) {

    	int playerSz = (tile_size/2);
    	int offSetX = (tile_size/4);
    	int offSetY = (tile_size/4);

    	g.setColor(Color.RED);

    	//Set the player's position relative to the camera
    	int tempPosX = tile_size * ( posX - camera.getCameraPosX() + camera.gettileMaxWidth()/2 ) + offSetX;
    	int tempPosY = tile_size * ( posY - camera.getCameraPosY() + camera.gettileMaxHeight()/2 ) + offSetY;

    	g.fillRect( tempPosX, tempPosY, playerSz, playerSz );

    }

    //Draws at the current location, used for the map editor
    public void draw( Graphics2D g, int tile_size, int centerPosX, int centerPosY ) {

    	int playerSz = (tile_size/2);
    	int offSetX = (tile_size/4);
    	int offSetY = (tile_size/4);

    	g.setColor(Color.RED);

    	//Set the player's position
    	int tempPosX = centerPosX + tile_size * ( this.posX ) + offSetX;
    	int tempPosY = centerPosY + tile_size * ( this.posY ) + offSetY;

    	g.fillRect( tempPosX, tempPosY, playerSz, playerSz );

    }

    //Getters

    public int getPosX() {

        return posX;
    }

    public int getPosY() {

        return posY;
    }
    
    public int getEnergy() { return energy; }
    
    //Setters
    public void setEnergy( int energy ) { this.energy = energy; }

}
