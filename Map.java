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

    TODO: Allow user to change the coordinates of where this will be drawn on the canvas
 */

package fruPack;

import java.awt.Graphics2D;
import java.io.*;

public class Map {

    private Tile[][] map; //Container for the map/tiles
    private int width, height; //Width and height of the map

    //Constructor through a file
    public Map(String filename) throws Exception {
        File file = new File(filename); //Read in file
        BufferedReader br = new BufferedReader(new FileReader(file)); //set up buffer reader

        String str; //Holds the data from file
        if ((str = br.readLine()) != null) { //Check for first line
            String[] size_of_map = str.split(","); //Split string

            //Get map size
            width = Integer.parseInt(size_of_map[0]);
            height = Integer.parseInt(size_of_map[1]);

            map = new Tile[width][height]; //Initialize map
        } else { br.close(); }
        
        //Iterate through file (The for loops may cause errors if the file doesn't match the given size)
        for (int y = 0; y < height; ++y) {
            str = br.readLine(); //Read in line
            for (int x = 0; x < width; ++x) {
                Types type; // For initializing individual tiles

                switch (str.charAt(x)) { //assign from file index
                    case 'F':
                        type = Types.FOREST;
                        break;
                    case 'S':
                        type = Types.SWAMP;
                        break;
                    case 'D':
                        type = Types.DESERT;
                        break;
                    case 'W':
                        type = Types.WATER;
                        break;
                    case 'M':
                        type = Types.MOUNTAINS;
                        break;
                    case 'K':
                        type = Types.SHOPKEEPER;
                        break;
                    case 'C':
                        type = Types.CAVERNS;
                        break;

                    default: //okay intelliJ lol
                        throw new IllegalStateException("Unexpected value: " + str.charAt(x));
                }

                //Initialize tile
                map[x][y] = new Tile(type);
            }
        }
    }

    //TODO: constructor for a user defined map (blank constructor)

    /* Takes in the player's coordinates and a given camera size and 
     * draws relative to the player position in a cameraRadiusxcameraRadius circle around the player
     * - Deals with what tiles to render and gives the position of rendering to each individual tile.draw()
     */
    public void draw(Graphics2D g, int line_width, int playerPosX, int playerPosY, Camera camera ) {
    	/* Check if the player is near the bounds of the map
    	 * If so just render the same part of the map
    	 */
    	
    	/*
    	if ( playerPosX - cameraRadius < 0 ) {
    		playerPosX += Math.abs( cameraRadius - playerPosX );
    	} 
    	else if ( playerPosX + cameraRadius > width - 1  ) {
    		playerPosX -= Math.abs( cameraRadius - ( width - 1 - playerPosX ) );
    	} 
    	
    	if ( playerPosY - cameraRadius < 0 ) {
    		playerPosY += Math.abs( cameraRadius - playerPosY );
    	} 
    	else if ( playerPosY + cameraRadius > height - 1 ) {
    		playerPosY -= Math.abs( cameraRadius - ( height - 1 - playerPosY ) );
    	} 
		*/
    	
    	//Used to render render coordinates relative to the player
    	int tempPosX, tempPosY;
    	
        for (int x = camera.getCameraPosX() - camera.getRadius(); x <= camera.getCameraPosX() + camera.getRadius(); ++x) {
        	if ( x >= 0 && x < width ) {
	            for (int y = camera.getCameraPosY() - camera.getRadius(); y <= camera.getCameraPosY() + camera.getRadius(); ++y) {
	            	if ( y >= 0 && y < height ) {
	            		tempPosX = camera.getwindowPosX() + line_width * ( x - playerPosX );
	            		tempPosY = camera.getwindowPosY() + line_width * ( y - playerPosY );
		                map[x][y].draw(g, line_width, tempPosX, tempPosY);
	            	}
	            }
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
    
    // Height and Width getters
    public int getHeight() { return height; }
    public int getWidth() { return width; }

}
