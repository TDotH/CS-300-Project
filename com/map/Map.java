/* Author: Forrest Daggett

    Date: 1/25/20
--------------------------------------
    Map Class for the frupal game

    Map is initialized either through a file, or manually as a 2-dimensional array of Tile objects.

    When reading through the map file, let's use this legend:
    First line is [width],[height]
    F = FOREST
    S = SWAMP
    D = DESERT
    W = WATER
    M = MOUNTAINS
    K = SHOPKEEPER
    C = CAVERNS - Where the jewels are held (?)

    Map is constructed so that the 0,0 coordinates are in the top left of the grid
    and increase as you go right/down
 */

package com.map;


import java.awt.Graphics2D;
import java.awt.Image;
import java.io.*;
import java.util.ArrayList;

import com.actors.ShopKeeper;
import com.inventory.*;
import com.obstacles.Obstacle;
import com.obstacles.Obstacles;

import javax.imageio.ImageIO;

import com.player.*;

public class Map {

	private ArrayList<Item> mapItems = new ArrayList<Item>(); //Used to keep track of item locations (since items hold their own coordinates)
	private ArrayList<Obstacle> mapObstacles = new ArrayList<Obstacle>(); //Container for the obstacles
    private Tile[][] map; //Container for the map/tiles
    private int width, height; //Width and height of the map
    private int startX, startY; //Start coordinates for the player
    private Item jewel; //Makes getting the jewel for the cheat button much easier
    private ShopKeeper shopkeeper; //Makes getting the shopkeep easier

    //Controls size of tiles
    private static int LINE_WIDTH = 32;

    //The tilesets to be used
    private Image tileset = null;
    private Image itemset = null;
    private Image obstacleset = null;

    //Blank Constructor
    public Map() {

    	loadTilesets();
    }

    //Construct a blank map with the given width and height, and type
    public Map( int width, int height, Types type ) {

    	this.width = width;
    	this.height = height;

    	map = new Tile[width][height]; //Initialize map

    	loadTilesets();

    	// Initialize tiles
    	for ( int x = 0; x < width; x++ ) {
    		for ( int y = 0; y < height; y++) {
    			map[x][y] = new Tile( type );
    		}
    	}
    }

    private void loadTilesets() {

    	//Load the item tileset
    	try {
    		itemset = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/images/itemset.png"));
    	}
    	catch (IOException ex) {
    		System.out.println("Error! Itemset can't be loaded!");
    	}

    	//Load the obstacle tileset
    	try {
    		obstacleset = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/images/obstacleset.png"));
    	}
    	catch (IOException ex) {
    		System.out.println("Error! Itemset can't be loaded!");
    	}

    	//Load the tileset
    	try {
    		tileset = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/images/tileset.png"));
    	}
    	catch (IOException ex) {
    		System.out.println("Error! Tileset can't be loaded!");
    	}
    }

    //Loads a map with the given filename of format (src/maps/(filename).map)
    public void loadMap(String filename) throws Exception {
        File file = new File(filename); //Read in file
        BufferedReader br = new BufferedReader(new FileReader(file)); //set up buffer reader

        String str; //Holds the data from file

        //Get map size on the first line
        if ((str = br.readLine()) != null) { //Check for first line
            String[] size_of_map = str.split(","); //Split string

            width = Integer.parseInt(size_of_map[0]);
            height = Integer.parseInt(size_of_map[1]);

            map = new Tile[width][height]; //Initialize map
        } else { br.close(); }

        //Get player spawn coordinates on the second line
        if ((str = br.readLine()) != null) { //Check for the second line
            String[] playerSpawn = str.split(","); //Split string

            startX = Integer.parseInt(playerSpawn[0]);
            startY = Integer.parseInt(playerSpawn[1]);

        } else { br.close(); }

        //Iterate through file (The for loops may cause errors if the file doesn't match the given size)
        for (int y = 0; y < height; ++y) {
            str = br.readLine(); //Read in line
            for (int x = 0; x < width; ++x) {
                Types type; // For initializing individual tiles
                switch ( Integer.parseInt( String.valueOf(str.charAt(x))) ) { //assign from file index
	                case 0:
	                	type = Types.DEFAULT;
	                	break;
	                case 1:
	                    type = Types.FOREST;
	                    break;
	                case 2:
	                    type = Types.SWAMP;
	                    break;
	                case 3:
	                    type = Types.DESERT;
	                    break;
	                case 4:
	                    type = Types.WATER;
	                    break;
	                case 5:
	                    type = Types.MOUNTAINS;
	                    break;
                    default: //okay intelliJ lol
                        throw new IllegalStateException("Unexpected value: " + str.charAt(x));
                }

                //Initialize tile
                map[x][y] = new Tile(type);
            }
        }
        //Load items on next line
        if ((str = br.readLine()) != null) { //Check for first line

            String[] itemLocations = str.split(";"); //First split individual items

            //Iterate through array to create each item
            for ( int i = 0; i < itemLocations.length; i++ ) {
            	String[] item = itemLocations[i].split(","); //Second split to get item type and location
            		//Get the items position on the map
            		int tempPosX = Integer.parseInt( item[1] );
            		int tempPosY = Integer.parseInt( item[2] );

            		int tempID = Integer.parseInt( item[0] );
            		Item tempItem = null;
            		//Iterate through items to find the item with the specific id
            		for( Items aItem : Items.values() ) {

            			if( aItem.getItemID() == tempID ) {
            				tempItem = new Item( aItem, tempPosX, tempPosY );
            			}
            		}

            		if ( tempItem != null ) {

            			if ( tempItem.getItem() == Items.JEWEL ) {
            				jewel = tempItem;
            			}

        				//mapItems.add( tempItem );
        				map[tempPosX][tempPosY].setObject(tempItem);
            		}
            }

        } else { br.close(); }

        //Load obstacles on next next line
        if ((str = br.readLine()) != null) { //Check for first line

            String[] obstacleLocations = str.split(";"); //First split individual obstacles

            //Iterate through array to create each item
            for ( int i = 0; i < obstacleLocations.length; i++ ) {
            	String[] obstacle = obstacleLocations[i].split(","); //Second split to get obstacle type and location
            		//Get the items position on the map
            		int tempPosX = Integer.parseInt( obstacle[1] );
            		int tempPosY = Integer.parseInt( obstacle[2] );

            		int tempID = Integer.parseInt( obstacle[0] );
            		Obstacle tempObstacle = null;
            		//Iterate through items to find the item with the specific id
            		for( Obstacles aObstacle : Obstacles.values() ) {

            			if( aObstacle.getObstacleID() == tempID ) {
            				tempObstacle = new Obstacle( aObstacle, tempPosX, tempPosY );
            			}
            		}

            		if ( tempObstacle!= null ) {

        				//mapObstacles.add( tempObstacle );
        				map[tempPosX][tempPosY].setObject(tempObstacle);
            		}
            }
        } else { br.close(); }
        
        //Load shopkeep on last line
        shopkeeper = null;
        
        if ((str = br.readLine()) != null) { //Check for first line

        	//First, check if there is a shopkeep on this map
        	if ( Integer.parseInt(str) == 1 ) {
        		
        		//Check the next line for shopkeeper location
                if ((str = br.readLine()) != null) { 
                    String[] shopLoc = str.split(","); 

                    int shopX = Integer.parseInt(shopLoc[0]);
                    int shopY = Integer.parseInt(shopLoc[1]);
                    
                    shopkeeper = new ShopKeeper( shopX, shopY );
                    map[shopX][shopY].setObject(shopkeeper);

                } else { br.close(); }
        		
        		//Check the next line for shopkeeper inventory
                if ( shopkeeper != null ) {
                    if ((str = br.readLine()) != null) { 
                    	
                    	String[] shopkeepInventory = str.split(";"); //First split individual items
                    	
                    	for ( int i = 0; i < shopkeepInventory.length; i++ ) {
                    		
                    		String[] item = shopkeepInventory[i].split(","); //Second split to get item type and location
                    		//Get the items position on the map
                    		int tempItemId = Integer.parseInt( item[0] );
                    		int tempPrice = Integer.parseInt( item[1] );

                    		Item tempItem = null;
                    		//Iterate through items to find the item with the specific id
                    		for( Items aItem : Items.values() ) {

                    			if( aItem.getItemID() == tempItemId ) {
                    				tempItem = new Item( aItem );
                    				shopkeeper.addItem( tempItem );
                    			}
                    		}

                    	}
                    	
                    } else { br.close(); }
                }
        	}
        }
    }

    //Saves the current map with format (src/maps/(filename).map)
    public void saveMap ( String fileName ) throws Exception {

    	try {
    		PrintWriter aWriter = new PrintWriter( fileName ); //Open file

    		//Temp string to hold chars
    		String tempString;

    		//Write the width and height
    		tempString = String.valueOf(width) + "," + String.valueOf(height);
    		aWriter.println( tempString );

    		//Write the player's spawn coordinates
    		tempString = String.valueOf(startX) + "," + String.valueOf(startY);
    		aWriter.println( tempString );

    		//Write each tile
    		for ( int y = 0; y < height; y++ ) {

    			//Clear string
    			tempString = "";
    			for ( int x = 0; x < width; x++ ) {

    				tempString = tempString.concat( ( String.valueOf( String.valueOf(map[x][y].getTileID() ))));

    				//If there is an item on this tile, push to the map array
    				if ( map[x][y].getObject() != null ) {
    					//Is the object an item?
    					if ( map[x][y].getObject() instanceof Item ) {

    						mapItems.add( (Item) map[x][y].getObject() );
    					}
    					//Is the object an obstacle?
    					if ( map[x][y].getObject() instanceof Obstacle ) {

    						mapObstacles.add( (Obstacle) map[x][y].getObject() );
    					}
    				}
    			}

        		if ( tempString.length() != 0 ) {
        			aWriter.println( tempString );
        		}
    		}

    		//Clear the string
    		tempString = "";
    		//Add any potential items
    		for ( Item items : mapItems ) {
    			tempString = tempString.concat( String.valueOf( items.checkType() ) + "," + String.valueOf( items.getPosX() ) + "," + String.valueOf( items.getPosY() ) +";");
    		}
    		if ( tempString.length() != 0 ) {
    			aWriter.println( tempString );
    		}

    		//Clear the string
    		tempString = "";
    		//Add any potential obstacles
    		for ( Obstacle obstacles : mapObstacles ) {
    			tempString = tempString.concat( String.valueOf( obstacles.checkType() ) + "," + String.valueOf( obstacles.getPosX() ) + "," + String.valueOf( obstacles.getPosY() ) +";");
    		}

    		if ( tempString.length() != 0 ) {
    			aWriter.println( tempString );
    		}
    		
    		//Clear the string
    		tempString = "";
    		//Add the shopkeeper and the inventory
    		if ( shopkeeper != null ) {
    			aWriter.println("1");
    			
    			aWriter.println( String.valueOf( shopkeeper.getPosX() ) + ',' + String.valueOf( shopkeeper.getPosY() ));
    			
    			for ( Item items : shopkeeper.getInventory() ) {
    				tempString = tempString.concat( String.valueOf( items.getItem().getItemID() ) + "," + String.valueOf(items.getItem().getValue() ) + ";");
    			}
    			
        		if ( tempString.length() != 0 ) {
        			aWriter.println( tempString );
        		}
    			
    		} else {
    			aWriter.println("0");
    		}

    		aWriter.close();
    	} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    }

    //Draws from a center point
    public void draw(Graphics2D g, int centerPosX, int centerPosY ) {

    	for ( int x = 0; x < width; x++ ) {
    		for ( int y = 0; y < height; y++) {

        		if ( ( itemset != null ) && ( obstacleset != null ) && ( tileset != null ) ) { //Draw with images
        			map[x][y].draw(g, LINE_WIDTH, centerPosX + LINE_WIDTH * x, centerPosY + LINE_WIDTH * y, itemset, obstacleset, tileset );
        		}
        		else { //Draw with colored blocks
        			map[x][y].draw(g, LINE_WIDTH, centerPosX + LINE_WIDTH * x, centerPosY + LINE_WIDTH * y );
        		}
    		}
    	}
    }

    /* Takes in the player's coordinates and a given camera size and draws relative to the camera's position on the map
     * - Deals with what tiles to render and gives the position of rendering to each individual tile.draw()
     */
    public void draw(Graphics2D g, int playerPosX, int playerPosY, int playerVisionRadius, Camera camera, boolean finished, int inset ) {
    	/* Check if the player is near the bounds of the map
    	 * If so just render the same part of the map
    	 */

    	//Camera bounds [Min, Max]
    	int[] cameraBoundX = {camera.getCameraPosX() - camera.gettileMaxWidth()/2, camera.getCameraPosX() + camera.gettileMaxWidth()/2};
    	int[] cameraBoundY = {camera.getCameraPosY() - camera.gettileMaxHeight()/2, camera.getCameraPosY() + camera.gettileMaxHeight()/2};

    	final int MIN = 0;
    	final int MAX = 1;

    	//Used to render render coordinates relative to the camera
    	int tempPosX, tempPosY;

    	//Used to make the map draw from (0,0) out
    	int xDraw = 0;
    	int yDraw = 0;

    	//Draw tiles first
        for (int x = cameraBoundX[MIN]; x <= cameraBoundX[MAX]; ++x) {
        	if ( x >= 0 && x < width ) {
	            for (int y = cameraBoundY[MIN]; y <= cameraBoundY[MAX]; ++y) {
	            	if ( y >= 0 && y < height ) {
	            		tempPosX =  LINE_WIDTH * ( xDraw ) + inset;
	            		tempPosY = LINE_WIDTH * ( yDraw ) + inset;
	            		
	            		//Check to see if the game "finished"
	            		if ( finished == true ) {
	            			
	                		if ( ( itemset != null ) && ( obstacleset != null ) && ( tileset != null ) ) { //Draw with images
	                			map[x][y].draw(g, LINE_WIDTH, tempPosX, tempPosY, itemset, obstacleset, tileset );
	                		}
	                		else { //Draw with colored blocks
	                			map[x][y].draw(g, LINE_WIDTH, tempPosX, tempPosY + LINE_WIDTH * y );
	                		}
	            		}
	            		
	            		else {
		            		//Draw if within player "vision"
							if( ( ( playerPosX + playerVisionRadius >= x ) && ( x >= playerPosX - playerVisionRadius ) ) && 
									( ( playerPosY + playerVisionRadius >= y ) && ( y >= playerPosY - playerVisionRadius ) ) ) {
									
								//Set the tiles the player can see to visited if not already
								if ( map[x][y].getVisited() == false ) {
									map[x][y].setVisited();
								}
								
			            		if ( ( itemset != null ) && ( obstacleset != null ) && ( tileset != null ) ) { //Draw with images
			            			map[x][y].draw(g, LINE_WIDTH, tempPosX, tempPosY, itemset, obstacleset, tileset, true ); 
			            		}
			            		else { //Draw with colored blocks
			            			//map[x][y].draw(g, LINE_WIDTH, tempPosX, tempPosY, true );
			            		}
								
							//If not then check if the player has seen it already	
							} else if ( map[x][y].getVisited() ) {
								
			            		if ( ( itemset != null ) && ( obstacleset != null )  && ( tileset != null )  ) { //Draw with images
			            			map[x][y].draw(g, LINE_WIDTH, tempPosX, tempPosY, itemset, obstacleset, tileset, false ); 
			            		}
			            		else { //Draw with colored blocks
			            			//map[x][y].draw(g, LINE_WIDTH, tempPosX, tempPosY, false );
			            		}
							}
		            	}
	            		yDraw++;
	            	}
	            }
	            yDraw = 0;
	        	xDraw++;
        	}
        }
    }

    //Tile getter
    public Tile get_tile(int x, int y) {
        //Check bounds
        if (x < 0 || x >= width
                || y < 0 || y >= height)
            return null;
        return map[x][y];
    }

    //Tile setter
    public void set_tile(Tile tile, int x, int y) {
        //check bounds
        if (x < 0 || x >= width
                || y < 0 || y >= height)
            return;
        map[x][y] = tile;
    }

    //Searches for a specific tile that posX, posY would be in on the map given a centerPosX and centerPosY
    public void set_tile_at( Types type, int posX, int posY ) {

    	//Check if within bounds
    	if ( posX >= 0 && posX < width ) {

    		if ( posY >= 0 && posY < height ) {
    	    	map[posX][posY].setType(type);
    		}
    	}
    }

    //Getters
    public int getHeight() { return height; }
    public int getWidth() { return width; }
    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getTileSize() { return LINE_WIDTH; }
    public int getJewelX() { return jewel.getPosX(); }
    public int getJewelY() { return jewel.getPosY(); }
    public Item getJewel() { return jewel; };
    public ShopKeeper getShopkeep() { return shopkeeper; }

    //Setters
    public void setStartX( int startX ) { this.startX = startX; }
    public void setStartY( int startY ) { this.startY = startY; }
    public void setShopkeep( ShopKeeper shopkeeper ) { this.shopkeeper = shopkeeper; }

}
