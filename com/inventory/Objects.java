/* Author: Tyde Hashimoto
 * Doesn't really do anything but abstracts some stuff to make it easier to place in the map editor
 * 
 */

package com.inventory;

import java.awt.Graphics2D;
import java.awt.Image;

public interface Objects {

	public void draw( Graphics2D g, int tile_size, int centerPosX, int centerPosY ); //Regular ol draw
	public void draw( Graphics2D g, int tile_size, int centerPosX, int centerPosY, Image anImage ); //With images
	
}
