package com.player;
import com.inventory.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import com.map.*;
import com.obstacles.*;

public class Player implements Objects {

    //integers that hold the current position coordinates, as well as the map boundaries
    private int posX, posY, upBoundX, upBoundY;

    //Player Inventory
    private ArrayList<Item> inventory = new ArrayList<Item>();

    //Player's energy
    private int energy;

    //Energy and money; unused until items are ready
    private int money;

    //Win Flag (player has the jewel)
    boolean winFlag;

    //Lose Flag (player ran out of energy)
    boolean loseFlag;

    //Empty Constructor
    public Player() {}

    //Constructor: startX, startY, maxX, maxY, minX, minY
    public Player( int posX, int posY, int maxX, int maxY ) {

        setPos( posX, posY );
        setBounds( maxX - 1, maxY - 1);
        winFlag = false;
        loseFlag = false;
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

    	switch ( filterPlayerInput(e)){//e.getKeyCode() ) {

    		case ("LEFT"):

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
			case "RIGHT":

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

			case "UP":

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

			case "DOWN":
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

			case "TALK":
				ArrayList<Tile> surroundingTiles = new ArrayList<Tile>();

				//Get the tiles surrounding the player
				Tile northTile = map.get_tile( posX, posY - 1 );
				Tile southTile = map.get_tile( posX, posY +1);
				Tile westTile = map.get_tile(posX-1, posY);
				Tile eastTile = map.get_tile(posX+1, posY);

				surroundingTiles.add(northTile);
				surroundingTiles.add(southTile);
				surroundingTiles.add(eastTile);
				surroundingTiles.add(westTile);

				speakToNPC(surroundingTiles);


				break;
			default:
				throw new IllegalStateException("Unexpected value!");
		}
    }

    private void speakToNPC(ArrayList<Tile> surroundingTiles){
		for (Tile tile : surroundingTiles){
			if (tile.getType() == Types.SHOPKEEPER ){
				System.out.print("FUCKIN YOLO");
			}
		}

	}

    private String filterPlayerInput(KeyEvent e){

		HashMap<Integer, String> validInput = new HashMap<Integer, String>();
		validInput.put(KeyEvent.VK_A, "LEFT");
		validInput.put(KeyEvent.VK_LEFT, "LEFT");
		validInput.put(KeyEvent.VK_KP_LEFT, "LEFT");

		validInput.put(KeyEvent.VK_S, "DOWN");
		validInput.put(KeyEvent.VK_DOWN, "DOWN");
		validInput.put(KeyEvent.VK_KP_DOWN, "DOWN");

		validInput.put(KeyEvent.VK_D, "RIGHT");
		validInput.put(KeyEvent.VK_RIGHT, "RIGHT");
		validInput.put(KeyEvent.VK_KP_RIGHT, "RIGHT");

		validInput.put(KeyEvent.VK_W, "UP");
		validInput.put(KeyEvent.VK_UP, "UP");
		validInput.put(KeyEvent.VK_KP_UP, "UP");

		validInput.put(KeyEvent.VK_SPACE, "TALK");

		try{
			String command = validInput.get(e.getKeyCode());
			return command;
		} catch (Exception exc){
			return "INVALID";
		}
	}

    //Does all the tile checking, takes away from players energy depending on stuff
    public boolean checkTile( Tile tile ) {

    	boolean passable = true;
    	if ( tile.getType().getPassable() == true ) {

    		//Set the energy cost to the default
    		int energyCost = tile.getType().getEnergyCost();

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
	    		}
	    	}

	    	//Check if the tile is water
	    	if ( tile.getType() == Types.WATER ) {
	    		//Check if the player has the boat
	    		if ( hasItem( Items.BOAT ) ) {
	    			energyCost = 1;
	    		}
	    		else{
	    			passable = false;
				}
	    	}

	    	energy -= energyCost;

	    	//Check if the player has any energy left
	    	if ( energy <= 0 ) {
	    		loseFlag = true;
	    	}

    	} else {
    		passable = false;
    	}

    	return passable;
    }

    //Does obstacle checking
    private boolean checkObstacle( Tile tile, Obstacles obstacle ) {

    	boolean passable = true;
    	//Can you pass the obstacle or does the player have the required item?
    	if ( obstacle.getPassable() == true || hasItem( obstacle.requiredItem() ) == true ) {

    		//Does the player have the required item?
    		if ( hasItem( obstacle.requiredItem() ) == true ) {
    			//Just remove it from the map for now
    			tile.setObject( null );
    		}
    		else { //Remove additional energy
    			energy -= obstacle.getEnergyCost();
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
	    			break;

	    		case GOLD:

	    			money += 25;
	    			break;

	    		case POWERBAR:
	    			energy += 3;
	    			break;

	    		case FISH:
	    			energy += 1;
	    			break;

    			default:
    				inventory.add(item);
    		}

    		//Add the item to the inventory
    		System.out.println("Adding: " + item.getName());

    	}
    	else {
    		System.out.println("Error! Null item passed!");
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

    final public int getEnergy() { return energy; }
    final public int getMoney() { return money; }
    final public boolean getWinFlag() { return winFlag; }
    final public boolean getLoseFlag() { return loseFlag; }

    //Setters
    public void setEnergy( int energy ) { this.energy = energy; }
    public void setMoney( int money ) { this.money = money; }
}
