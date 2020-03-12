package com.player;
import com.inventory.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import com.map.*;
import com.obstacles.*;

public class Player implements Objects {

    //integers that hold the current position coordinates, as well as the map boundaries
    private int posX, posY, upBoundX, upBoundY;

    //Player Inventory
    private ArrayList<Item> inventory = new ArrayList<Item>();

    //Player's energy
    private int maxEnergy;
    private int currEnergy;

    //Energy and money; unused until items are ready
    private int maxMoney;
    private int currMoney;

    //Win Flag (player has the jewel)
    boolean winFlag;

    //Lose Flag (player ran out of energy)
    boolean loseFlag;
    
    //Inventory Flag (inventory has changed)
    boolean inventoryFlag;
    
    //Player's vision radius
    private int visionRadius = 1;

    //Empty Constructor
    public Player() {}

    //Constructor: startX, startY, maxX, maxY, minX, minY
    public Player( int posX, int posY, int maxX, int maxY ) {

        setPos( posX, posY );
        setBounds( maxX - 1, maxY - 1);
        winFlag = false;
        loseFlag = false;
        inventoryFlag = false;
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

    		case KeyEvent.VK_A:

    			//Is the player at the bounds?
                if ((posX - 1) >= 0) {

                	//Get the the tile that will be moved to
                	Tile tile = map.get_tile( posX - 1, posY );
                	//Check the tile for energy loss, items/obstacles/shopkeep
            		if ( checkTile( tile ) == true ) {
            			posX = posX - 1;
            		}

                	else {
                		//Do nothing for now
                	}

                } else {
                	//Do nothing
                }
                    break;
			case KeyEvent.VK_D:

	            if ((posX + 1) <= upBoundX) {

	               	//Get the the tile that will be moved to
                	Tile tile = map.get_tile( posX + 1, posY );
                	//Check the tile for energy loss, items/obstacles/shopkeep
            		if ( checkTile( tile ) == true ) {
            			posX = posX + 1; //Move the player
            		}

                	else {
                		//Do nothing for now
                	}

                } else {
                	//Do nothing
	            }
	            break;

				case KeyEvent.VK_W:

		            if ((posY - 1) >= 0) {

		               	//Get the the tile that will be moved to
	                	Tile tile = map.get_tile( posX, posY - 1 );
                		//Move the player and check the tile for energy loss, items/obstacles/shopkeep
                		if ( checkTile( tile ) == true ) {
                			posY = posY - 1; //Move the player
                		}

	                	else {
	                		//Do nothing for now
	                	}

	                } else {
	                	//Do nothing
		            }
		            break;

					case KeyEvent.VK_S:
			            if ((posY + 1 <= upBoundY )) {

			               	//Get the the tile that will be moved to
		                	Tile tile = map.get_tile( posX, posY + 1 );
	                		//Check the tile for energy loss, items/obstacles/shopkeep
	                		if ( checkTile( tile ) == true ) {
	                			posY = posY + 1; //Move the player
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

    //Does all the tile checking, takes away from players energy depending on stuff
    public boolean checkTile( Tile tile ) {

    	//Set the energy cost to the default
		int energyCost = tile.getType().getEnergyCost();
    	
    	boolean passable = true;
    	if ( tile.getType().getPassable() == true ) {

	    	//Is there something there?
	    	if ( tile.getObject() != null ) {

	    		//The object is an item
	    		if ( tile.getObject() instanceof Item ) {

	    			//Since the object is an item treat it as such
	    			Item tempItem = (Item)tile.getObject();
	    			addItem( tempItem );

	    			//The tile no longer has the item
	    			tile.setObject( null );
	    		}

	    		if ( tile.getObject() instanceof Obstacle ) {

	    			//Since the object is an obstacle treat it as such
	    			Obstacle tempObstacle = (Obstacle)tile.getObject();
	    			//Check if the object is possible and augment energy cost if needed
	    			passable = checkObstacle( tile, tempObstacle.getObstacle() );
	    			
	    			if ( tempObstacle.getVisible() == false ) {
	    				tempObstacle.setVisible(true);
	    			}
	    		}
	    	}

	    	//Check if the tile is water
	    	if ( tile.getType() == Types.WATER ) {
	    		//Check if the player has the boat
	    		if ( hasItem( Items.BOAT ) == true ) {
	    			energyCost = 0;
	    		} else {
	    			passable = false;
	    		}
	    	}

	    	//currEnergy -= energyCost;

	    	//Check if the player has any energy left
	    	if ( currEnergy <= 0 ) {
	    		loseFlag = true;
	    	}

    	} else {
    		passable = false;
    	}

    	currEnergy -= energyCost;
    	return passable;
    }

    //Does obstacle checking
    private boolean checkObstacle( Tile tile, Obstacles obstacle ) {

    	boolean passable = true;
    	//Can you pass the obstacle or does the player have the required item?
    	if ( obstacle.getPassable() == true || hasItem( obstacle.requiredItem() ) == true ) {

    		//Does the player have the required item?
    		if ( hasItem( obstacle.requiredItem() ) == true ) {
    			
    			//Keep the halfer tile on the map
    			if ( obstacle != Obstacles.HALFER ) {
    				
        			//Just remove it from the map for now
        			tile.setObject( null );
    			}

    		}
    		else { 
    			
    			//if the obstacle happens to be the halfer tile remove half the player's money
    			if ( obstacle == Obstacles.HALFER ) {
    				currMoney = currMoney / 2;
    			}
    			
    			//Remove additional energy
    			currEnergy -= obstacle.getEnergyCost();
    		}
    	}
    	else {
    		passable = false;
    	}
    	return passable;
    }

    //Checks the inventory for a specific item
    private boolean hasItem( Items item ) {
    	boolean hasItem = false;

    	for( Item aItem : inventory ) {

    		if ( aItem.getItem() == item ) {
    			hasItem = true;
    			break;
    		}
    	}

    	return hasItem;
    }

    //Does item checking and adds it to the inventory
    public void addItem( Item item ) {

    	//Another check since this can be accessed outside of player
    	if ( item != null ) {

    		switch ( item.getItem() ) {

	    		case JEWEL:
	    			winFlag = true;
	    			inventory.add( item );
	    			inventoryFlag = true;
	    			break;

	    		case GOLD:
	    			currMoney += 25;
	    			break;

	    		case POWERBAR:
	    			currEnergy += 3;
	    			break;

	    		case FISH:
	    			currEnergy += 1;
	    			break;
	    			
	    		case BINOCULARS:
	    			visionRadius += 1;
	    			inventory.add(item);
	    			inventoryFlag = true;
	    			break;

    			default:
    				inventory.add(item);
    				inventoryFlag = true;
    				break;
    		}

    		//Add the item to the inventory
    		//System.out.println("Adding: " + item.getName());
    		

    	}
    	else {
    		System.out.println("Error! Null item passed!");
    	}
    }

    //Draws the player according to the given tile size and relative to the camera; offsets movement by tile size
    public void draw( Graphics2D g, int tile_size, Camera camera, int inset ) {

    	int playerSz = (tile_size/2);
    	int offSetX = (tile_size/4);
    	int offSetY = (tile_size/4);

    	g.setColor(Color.RED);

    	//Set the player's position relative to the camera
    	int tempPosX = tile_size * ( posX - camera.getCameraPosX() + camera.gettileMaxWidth()/2 ) + offSetX + inset;
    	int tempPosY = tile_size * ( posY - camera.getCameraPosY() + camera.gettileMaxHeight()/2 ) + offSetY + inset;

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

    final public int getEnergy() { return currEnergy; }
    final public int getMoney() { return currMoney; }
    public int getVisiontRadius() { return visionRadius; }
    final public boolean getWinFlag() { return winFlag; }
    final public boolean getLoseFlag() { return loseFlag; }
    final public ArrayList<Item> getInventory() { return inventory; }
    final public boolean  getInventoryFlag() { return inventoryFlag; }

    //Setters
    public void setEnergy( int currEnergy ) { this.currEnergy = currEnergy; }
    public void setMoney( int currMoney ) { this.currMoney = currMoney; }
    public void setVisionRadius( int visionRadius ) { this.visionRadius = visionRadius; }
    public void setInventoryFlag( boolean inventoryFlag ) { this.inventoryFlag = inventoryFlag; }
}
