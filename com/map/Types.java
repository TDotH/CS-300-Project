/* Author: Forrest Daggett
    Date: 1/25/20
--------------------------------------
    File holding the enum for possible tile values

    IntelliJ wanted me to make the enum in a separate file from Tile.java /shrug

    Notes: Maybe make a tile that tells the game where to start the player?

    Also I'm not committed to this naming convention if someone else has a better idea
 */

package com.map;

public enum Types {
	
	DEFAULT(0, 0, true), FOREST(1, 1, true), SWAMP(2, 2, true), DESERT(3, 1, true), WATER(4, 2, true),
    MOUNTAINS(5, 1, false), SHOPKEEPER(6, 0, true), CAVERNS(7, 0, true);
	
	int tileID;
	int energyCost;
	boolean passable; 
	
	Types(int tileID, int energyCost, boolean passable ) {
		this.tileID = tileID;
		this.energyCost = energyCost;
		this.passable = passable;
	}
	
	public int getTileID() { return tileID; }
	public int getEnergyCost() { return energyCost; }
	public boolean getPassable() { return passable; }
	
}
