/* Author: Tyde Hashimoto
 * The Item that lets the player win the game
 */

package com.inventory;

import java.awt.Color;
import java.awt.Graphics2D;

public class Jewel extends Item {

    public Jewel( int posX, int posY ) {
    	
    	super( Items.JEWEL, posX, posY );
    }
	
    public Jewel() {
    	super( Items.JEWEL );
    }
    
	@Override
	void method() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void draw( Graphics2D g, int tile_size, int centerPosX, int centerPosY ) {

    	int itemSz = (tile_size/2);
    	int offSetX = (tile_size/4);
    	int offSetY = (tile_size/4);

    	g.setColor( new Color(127, 0, 255,255));

    	//Set the player's position
    	//int tempPosX = centerPosX + tile_size * ( this.posX ) + offSetX;
    	//int tempPosY = centerPosY + tile_size * ( this.posY ) + offSetY;

    	g.fillRect( centerPosX + offSetX, centerPosY + offSetY, itemSz, itemSz );

    }

}
