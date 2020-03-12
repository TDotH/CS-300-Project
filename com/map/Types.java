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
	
	DEFAULT		(0, 0, true, "Default", 0 , 0), 
	FOREST		(1, 1, true, "Forest", 0, 0), 
	SWAMP		(2, 2, true, "Swamp", 3, 0), 
	DESERT		(3, 1, true, "Desert", 1, 0), 
	WATER		(4, 3, true, "Water", 0, 1),
    MOUNTAINS	(5, -1, false, "Mountain", 2, 0); 
	//, SHOPKEEPER(6, 0, true, "Shopkeeper"), CAVERNS(7, 0, true, "Cavern");
	
	int tileID;
	int energyCost;
	boolean passable; 
	String name;
    int imagePosX;
    int imagePosY;
	
	Types(int tileID, int energyCost, boolean passable, String name, int imagePosX, int imagePosY ) {
		this.tileID = tileID;
		this.energyCost = energyCost;
		this.passable = passable;
		this.name = name;
		this.imagePosX = imagePosX;
		this.imagePosY = imagePosY;
	}
	
	public int getTileID() { return tileID; }
	public int getEnergyCost() { return energyCost; }
	public boolean getPassable() { return passable; }
	public String getName() { return name; }
	
}
