package com.obstacles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import com.inventory.Objects;

/* Author: Tyde Hashimoto
 * Date: 2/24/2020
 * The obstacle file
 * 
 */

public class Obstacle implements Objects {
	
    private Obstacles obstacle;
    private int posX, posY; // Item position
    private boolean visible = true; //Used for the halfer

    // Public Methods
    public Obstacle() {
    	obstacle = null;
    }
    public Obstacle( Obstacles obstacle, int posX, int posY ) {
    	this.obstacle = obstacle;
    	this.posX = posX;
    	this.posY = posY;
    	
    	if ( obstacle == Obstacles.HALFER ) {
    		this.visible = false;
    	}
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }


    public void setPosY(int posY)
    {
        this.posY = posY;
    }


    public int getPosX()
    {
        return posX;
    }


    public int getPosY()
    {
        return posY;
    }

    public void setObstacle(Obstacles obstacle)
    {
        this.obstacle = obstacle;
    }
    
    public Obstacles getObstacle() { return obstacle; }

    /*
        This method checks the type of the current object, and returns a value associated with each type.
    */
    public int checkType()
    {
        return obstacle.obstacleID;
    }


    public void copyPos(Obstacle obstacle)
    {
        posX = obstacle.getPosX();
        posY = obstacle.getPosY();
    }

    public String getName()
    {
        return obstacle.name;
    }

	public boolean getVisible() { return visible; };
	public void setVisible( boolean visible ) { this.visible = visible; }
    
    //Draws at the given location from the center position
    public void draw( Graphics2D g, int tile_size, int centerPosX, int centerPosY ) {

    	int itemSz = (tile_size/2);
    	int offSetX = (tile_size/4);
    	int offSetY = (tile_size/4);

    	g.setColor(Color.ORANGE);

    	//Set the obstacles's position
    	int tempPosX = centerPosX + tile_size * ( this.posX ) + offSetX;
    	int tempPosY = centerPosY + tile_size * ( this.posY ) + offSetY;

    	g.fillRect( centerPosX + offSetX, centerPosY + offSetY, itemSz, itemSz );

    }
    //Draw with images
    public void draw( Graphics2D g2d, int tile_size, int centerPosX, int centerPosY, Image obstacleset ) {

    	int itemSz = (tile_size/2);
    	int offSet = (tile_size/16);
    	
    	int imageSz = 16;

    	if ( obstacleset != null ) {
    		g2d.drawImage(obstacleset, centerPosX + offSet , centerPosY + offSet , centerPosX + tile_size - offSet , centerPosY + tile_size - offSet, 
    				obstacle.imagePosX * imageSz, obstacle.imagePosY * imageSz, imageSz + obstacle.imagePosX * imageSz, imageSz + obstacle.imagePosY * imageSz, null);
    	} else {
    		g2d.setColor(new Color(127, 0, 255,255));
    		g2d.fillRect( centerPosX + offSet, centerPosY + offSet, itemSz, itemSz );
    	}

    	
    	//Set the item's
    	//int tempPosX = centerPosX + tile_size * ( this.posX ) + offSetX;
    	//int tempPosY = centerPosY + tile_size * ( this.posY ) + offSetY;

    	//g.fillRect( centerPosX + offSetX, centerPosY + offSetY, itemSz, itemSz );

    }
}
