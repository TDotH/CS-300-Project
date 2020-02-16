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
import java.awt.Toolkit;
import java.io.*;

import javax.imageio.ImageIO;

import com.player.*;

public class Map {

    private Tile[][] map; //Container for the map/tiles
    private int width, height; //Width and height of the map
    private int startX, startY; //Start coordinates for the player
    
    //Controls size of tiles
    private static int LINE_WIDTH = 48;
    
    //The tileset to be used
    private Image tileset = null;
    
    //Blank Constructor
    public Map() {
    	//Ignore for now
    	//tileset = Toolkit.getDefaultToolkit().getImage(("src/images/tileset.png"));
    	/*try {
    		tileset = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/images/tileset.png"));
    	}
    	catch (IOException ex) {
    		System.out.println("Error! Tileset can't be loaded!");
    	}*/
    	
    }
    
    //Construct a blank map with the given width and height
    public Map( int width, int height ) {
    	
    	this.width = width;
    	this.height = height;
    	
    	map = new Tile[width][height]; //Initialize map
    			
    	// Initialize tiles
    	for ( int x = 0; x < width; x++ ) { 
    		for ( int y = 0; y < height; y++) {
    			map[x][y] = new Tile( Types.DEFAULT );
    		}
    	} 	
    }

    //Loads a map with the given filename of format (src/maps/(filename).map)
    public void loadMap(String filename) throws Exception {
        File file = new File(filename); //Read in file
        BufferedReader br = new BufferedReader(new FileReader(file)); //set up buffer reader
       // tileset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("src/images/tileset.png"));
        
        String str; //Holds the data from file
        if ((str = br.readLine()) != null) { //Check for first line
            String[] size_of_map = str.split(","); //Split string

            //Get map size
            width = Integer.parseInt(size_of_map[0]);
            height = Integer.parseInt(size_of_map[1]);

            map = new Tile[width][height]; //Initialize map
        } else { br.close(); }
        
        if ((str = br.readLine()) != null) { //Check for the second line
            String[] size_of_map = str.split(","); //Split string

            //Get player spawn coordinates
            startX = Integer.parseInt(size_of_map[0]);
            startY = Integer.parseInt(size_of_map[1]);

        } else { br.close(); }
        
        //Iterate through file (The for loops may cause errors if the file doesn't match the given size)
        for (int y = 0; y < height; ++y) {
            str = br.readLine(); //Read in line
            for (int x = 0; x < width; ++x) {
                Types type; // For initializing individual tiles
                int tempInt = -1;
                
                switch ( Integer.parseInt( String.valueOf(str.charAt(x)))) { //assign from file index
	                case 0:
	                	type = Types.DEFAULT;
	                	break;
	                case 1:
	                    type = Types.FOREST;
	                    tempInt = 0;
	                    break;
	                case 2:
	                    type = Types.SWAMP;
	                    break;
	                case 3:
	                    type = Types.DESERT;
	                    break;
	                case 4:
	                    type = Types.WATER;
	                    tempInt = 12;
	                    break;
	                case 5:
	                    type = Types.MOUNTAINS;
	                    break;
	                case 6:
	                    type = Types.SHOPKEEPER;
	                    break;
	                case 7:
	                    type = Types.CAVERNS;
	                    break;
                    default: //okay intelliJ lol
                        throw new IllegalStateException("Unexpected value: " + str.charAt(x));
                }

                //Initialize tile
                map[x][y] = new Tile(type);
                //map[x][y].setImageID(tempInt);
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
    			}
    			
    			aWriter.println(tempString);
    		}
    		
    		aWriter.close();
    	} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    }
    
    //Draws from a center point (Ritvik will implement the conditional shader here)
    public void draw(Graphics2D g, int centerPosX, int centerPosY ) {
    	
    	for ( int x = 0; x < width; x++ ) {
    		for ( int y = 0; y < height; y++) {
    			map[x][y].draw(g, LINE_WIDTH, centerPosX + LINE_WIDTH * x, centerPosY + LINE_WIDTH * y ); // tileset );
    		}
    	}    	
    }
    
    /* Takes in the player's coordinates and a given camera size and draws relative to the camera's position on the map
     * - Deals with what tiles to render and gives the position of rendering to each individual tile.draw()
     */
    public void draw(Graphics2D g, int playerPosX, int playerPosY, Camera camera ) {
    	/* Check if the player is near the bounds of the map
    	 * If so just render the same part of the map
    	 */
    	
    	//Used to render render coordinates relative to the camera
    	int tempPosX, tempPosY;
    	
    	int i = 0;
    	int j = 0;
    	
        for (int x = camera.getCameraPosX() - camera.gettileMaxWidth()/2; x <= camera.getCameraPosX() + camera.gettileMaxWidth()/2; ++x) {
        	if ( x >= 0 && x < width ) {
	            for (int y = camera.getCameraPosY() - camera.gettileMaxHeight()/2; y <= camera.getCameraPosY() + camera.gettileMaxHeight()/2; ++y) {
	            	if ( y >= 0 && y < height ) {
	            		tempPosX = LINE_WIDTH * (i);
	            		tempPosY = LINE_WIDTH * (j);
	            		if((x == playerPosX + 1 || x == playerPosX - 1) && (y == playerPosY + 1 || y == playerPosY - 1) || map[x][y].getVisited()) {
							map[x][y].draw(g, LINE_WIDTH, tempPosX, tempPosY); //, tileset );
							j++;
						}
	            	}
	            }
	        	j = 0;
	        	i++;
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
    
    //Setters
    public void setStartX( int startX ) { this.startX = startX; }
    public void setStartY( int startY ) { this.startY = startY; }

}
