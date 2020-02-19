/* Author: Tyde Hashimoto
 * Doesn't really do anything but abstracts some stuff to make it easier to place in the map editor
 * 
 */

package com.inventory;

import java.awt.Graphics2D;

public interface Objects {

	public void draw( Graphics2D g, int tile_size, int centerPosX, int centerPosY );
	
}
