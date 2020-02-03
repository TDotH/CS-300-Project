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

package fruPack;

import java.awt.Graphics2D;
import java.io.*;

public class Map {

    private Tile[][] map; //Container for the map/tiles
    private int width, height; //Width and height of the map
    
    //Blank Constructor
    public Map() {}
    
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
                
                switch ( Integer.parseInt( String.valueOf(str.charAt(x)))) { //assign from file index
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
    
    public void draw(Graphics2D g, int line_width, int centerPosX, int centerPosY ) {
    	
    	for ( int x = 0; x < width; x++ ) { 
    		for ( int y = 0; y < height; y++) {
    			map[x][y].draw(g, 50, centerPosX + line_width * x, centerPosY + line_width * y);
    		}
    	}    	
    }
    /* Takes in the player's coordinates and a given camera size and draws relative to the camera's position on the map
     * - Deals with what tiles to render and gives the position of rendering to each individual tile.draw()
     */
    public void draw(Graphics2D g, int line_width, int playerPosX, int playerPosY, Camera camera ) {
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
	            		tempPosX = camera.getWindowPosX() + line_width * ( i );
	            		tempPosY = camera.getWindowPosY() + line_width * ( j );
		                map[x][y].draw(g, line_width, tempPosX, tempPosY);
		                j++;
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
    
    // Height and Width getters
    public int getHeight() { return height; }
    public int getWidth() { return width; }

}
