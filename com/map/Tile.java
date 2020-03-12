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
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.inventory.*;
import com.obstacles.*;

//Contains the type in an int and tests with the enums from the tiles.java file
public class Tile {
    private Types type; //Type of tile
    
    private Objects object; //Holds a potential object (like the shopkeeper, an item, or an obstacle); 
    private boolean visited;
    
    //Constructor
    Tile(Types type) {
        this.type = type;
        object = null;
        this.visited = false;
    }

    //Regular draw without images
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
	            default:
	                g2d.setColor(Color.WHITE);
	        }
	        //Fill color
	        g2d.fillRect(posX, posY,line_width,line_width);
	        //Draw rectangle (for the grid)
	        g2d.setColor(Color.BLACK);
	        g2d.drawRect(posX ,posY,line_width,line_width);
	        
	        //Is there an object to draw?
	        if ( object != null ) {
	        	object.draw( g2d, line_width, posX, posY );
	        }
    	//}
    }
    
    /*Draw with imported images
     * Used for the map editor
     */
    public void draw(Graphics2D g2d, int line_width, int posX, int posY, Image itemset, Image obstacleset, Image tileset ) { 
    	
    	int imageSz = 48; //Size of the images
    	
		//Draw the tileset 
    	if ( tileset != null ) {
        	g2d.drawImage(tileset, posX , posY , posX + line_width , posY + line_width,
        			type.imagePosX * imageSz, type.imagePosY * imageSz, imageSz + type.imagePosX * imageSz, imageSz + type.imagePosY * imageSz, null);
    	}
        
        //Is there an object to draw?
        if ( object != null ) {
        	
        	if ( object instanceof Item ) {
        		object.draw( g2d, line_width, posX, posY, itemset );
        	}
        	
        	if ( object instanceof Obstacle ) {
        		object.draw( g2d, line_width, posX, posY, obstacleset );
        	}
        }
    }
    
    /* Used for the gamescreen map
     *  If withinPlayerVision is true, then the tile will draw with 100% opacity
     *  If withinPlayerVision is false, then the tile will draw with 50% opacity
     *  Draw with imported images
     */
    public void draw(Graphics2D g2d, int line_width, int posX, int posY, Image itemset, Image obstacleset, Image tileset, boolean withinPlayerVision ) { 
    	    
    	int imageSz = 48; //Size of the images
    	
        //Can the player see this tile?
        if ( withinPlayerVision == true ) {
        	
    		//Draw the tileset 
        	if ( tileset != null ) {
            	g2d.drawImage(tileset, posX , posY , posX + line_width , posY + line_width,
            			type.imagePosX * imageSz, type.imagePosY * imageSz, imageSz + type.imagePosX * imageSz, imageSz + type.imagePosY * imageSz, null);
        	}
            
        	//Draw anything on the tile
	        //Is there an object to draw?
	        if ( object != null ) {
	        	
	        	if ( object instanceof Item ) {
	        		object.draw( g2d, line_width, posX, posY, itemset );
	        	}
	        	
	        	if ( object instanceof Obstacle ) {
	        		
	        		//Treat the object as an obstacle
	        		Obstacle tempObstacle = (Obstacle)object;
	        		
	        		//Special check for the halfer
	        		if ( tempObstacle.getObstacle() == Obstacles.HALFER ) {
	        			
	        			//If the player has already stepped on the halfer
	        			if ( tempObstacle.getVisible() ) {
	        				object.draw( g2d, line_width, posX, posY, obstacleset );
	        			}
	        			
	        		}
	        		else { //Regular obstacle
	        		
	        			object.draw( g2d, line_width, posX, posY, obstacleset );
	        		}
	        	}
	        }
	        
        } else {
        	float opacity = 0.5f;
        	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        	
    		//Draw the tileset 
        	if ( tileset != null ) {
            	g2d.drawImage(tileset, posX , posY , posX + line_width , posY + line_width,
            			type.imagePosX * imageSz, type.imagePosY * imageSz, imageSz + type.imagePosX * imageSz, imageSz + type.imagePosY * imageSz, null);
        	}
	        
	        //Reset opacity so it doesn't affect anything else
	        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
	        
    }
    
    // Getters
    public Types getType( ) { return this.type; }
    public int getTileID() { return this.type.getTileID(); };
    public Object getObject() { return this.object; }
    public void setVisited() { this.visited = true; }
    public boolean getVisited() { return this.visited; }
    
    //Type setter
    public void setType( Types type ) {
    	this.type = type;
    }
    
    //Object setter;
    public void setObject( Objects object ) { this.object = object; }
    
    //public void setImageID( int imageID ) { this.imageID = imageID; }

}
