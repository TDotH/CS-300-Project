/* Author: Forrest Daggett
    Date: 1/25/20
--------------------------------------
    File holding the enum for possible tile values

    IntelliJ wanted me to make the enum in a separate file from Tile.java /shrug

    Notes: Maybe make a tile that tells the game where to start the player?

    Also I'm not committed to this naming convention if someone else has a better idea
 */

package fruPack;

public enum Types {
	
	DEFAULT(0), FOREST(1), SWAMP(2), DESERT(3), WATER(4),
    MOUNTAINS(5), SHOPKEEPER(6), CAVERNS(7);
	
	int tileID;
	
	Types(int tileID) {
		this.tileID = tileID;
	}
	
	int getTileID() { return tileID; }
}
