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
	
	DEFAULT(0, 0, true, "Default"),
	FOREST(1, 1, true, "Forest"),
	SWAMP(2, 2, true, "Swamp"),
	DESERT(3, 1, true, "Desert"),
	WATER(4, 3, true, "Water"),
    MOUNTAINS(5, -1, false, "Mountain"),
	SHOPKEEPER(6, 0, false, "Shopkeeper"),
	CAVERNS(7, 0, true, "Cavern");
	
	int tileID;
	int energyCost;
	boolean passable; 
	String name;
	
	Types(int tileID, int energyCost, boolean passable, String name ) {
		this.tileID = tileID;
		this.energyCost = energyCost;
		this.passable = passable;
		this.name = name;
	}
	
	public int getTileID() { return tileID; }
	public int getEnergyCost() { return energyCost; }
	public boolean getPassable() { return passable; }
	public String getName() { return name; }
	
}
