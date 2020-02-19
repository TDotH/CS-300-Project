package com.map;

/* Author: Forrest Daggett
    Date: 1/25/20
--------------------------------------
    Tile class for the game. Feel free to change this as much as we need

    TODO:
     -Add visit flag
     -Add movment speed requirements
     -Maybe make this an abstract base class
*/

import java.awt.*;

//Contains the type in an int and tests with the enums from the tiles.java file
public class Tile {
    private Types type; //Type of tile
   // private int imageID = -1 ; //Which tile from the tile set to use
    

    //Constructor
    Tile(Types type) {
        this.type = type;
    }

    public void draw(Graphics2D g2d, int line_width, int posX, int posY ) { //Image tileSet ) { <-- ignore for now
    	
    	//There is a corresponding image for this tile!
    	//if ( imageID != -1 && tileSet != null ) {
    	//	g2d.drawImage(tileSet, posX, posY, posX + line_width, posY + line_width, imageID/12 * 16, imageID/4 * 16, 16 + imageID/4 * 16, 16 + imageID/4 * 16, null);
    	//}
    	//else { //Just draw a square
	        //Fill in squares by color
	        //TODO: Draw graphics instead of flat colors (maybe lol)
	        switch (type) {
	            case FOREST:
	                g2d.setColor(Color.GREEN);
	                break;
	            case SWAMP:
	                g2d.setColor(Color.DARK_GRAY);
	                break;
	            case DESERT:
	                g2d.setColor(Color.YELLOW);
	                break;
	            case WATER:
	                g2d.setColor(Color.BLUE);
	                break;
	            case MOUNTAINS:
	                g2d.setColor(Color.GRAY);
	                break;
	            case SHOPKEEPER:
	                g2d.setColor(Color.magenta);
	                break;
	            case CAVERNS:
	                g2d.setColor(Color.BLACK);
	                break;
	            default:
	                g2d.setColor(Color.WHITE);
	        }
	        //Fill color
	        g2d.fillRect(posX, posY,line_width,line_width);
	        //Draw rectangle (for the grid)
	        g2d.setColor(Color.BLACK);
	        g2d.drawRect(posX ,posY,line_width,line_width);
    	//}
    }
    
    //Type getter
    public Types getType( ) { return this.type; }
    public int getTileID() { return this.type.getTileID(); };
    
    //Type setter
    public void setType( Types type ) {
    	this.type = type;
    }
    
    //public void setImageID( int imageID ) { this.imageID = imageID; }

}
