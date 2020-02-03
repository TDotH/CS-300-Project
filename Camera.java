package fruPack;

/* Author: Tyde Hashimoto
 * Date: 1/28/20
 * Camera automatically centers itself around the player unless it would show the map out of bounds, then it doesn't move
 * Takes in viewport dimensions and will calculated how many tiles x and y it can show
 */

public class Camera {
	
	//Size of the viewport in the window
	private int viewPortSzX, viewPortSzY;
	
	//Position of the origin of the viewport in the window
	private int windowPosX, windowPosY;
	
	//How many tiles can be rendered with the given viewport and tile sizes
	private int tileMaxWidth, tileMaxHeight;
	
	//The position of camera on the map
	private int cameraPosX, cameraPosY;
	
	//The outer bounds of the map
	private int mapMaxX, mapMaxY;
	
	public Camera( int viewPortSzX, int viewPortSzY, int tileWidth, int windowPosX, int windowPosY, int cameraPosX, int cameraPosY, int mapMaxX, int mapMaxY ) {		
		

		//Set the viewport size in the window
		this.viewPortSzX = viewPortSzX;
		this.viewPortSzY = viewPortSzY;
		
		// Calculate max tile size that can be drawn
		tileMaxWidth = viewPortSzX / tileWidth;
		tileMaxHeight = viewPortSzY / tileWidth;

		
		//If the max is greater than the size of the map, change if to the max of the map
		if( tileMaxWidth > mapMaxX ) {
			tileMaxWidth = mapMaxX - 1;
		}
		
		if ( tileMaxHeight > mapMaxY ) {
			tileMaxHeight = mapMaxY - 1;
		}
		
		//System.out.println("tileMaxWidth: " + tileMaxWidth);
		
		// Origin of the camera in window
		this.windowPosX = windowPosX;
		this.windowPosY = windowPosY;
		
		// Current position of the camera on the map
		this.cameraPosX = cameraPosX;
		this.cameraPosY = cameraPosY;
		
		// Max bounds of the map
		this.mapMaxX = mapMaxX - 1;
		this.mapMaxY = mapMaxY - 1;	
		
		//Make sure the camera is in the correct position no matter what origin is given
		update( cameraPosX, cameraPosY );
	}
	
	//Moves with the player unless the camera would hit the bounds of the map [0, maxX]x[0, maxY]
	public void update( int playerPosX, int playerPosY ) {
		
		//Center the camera on the player
		cameraPosX = playerPosX;
		cameraPosY = playerPosY;
		
		if( cameraPosX - tileMaxWidth/2 < 0 ) {
			
			cameraPosX = tileMaxWidth/2;
	
		}
		
		if( cameraPosY - tileMaxHeight/2 < 0 ) {
			
			cameraPosY = tileMaxHeight/2;
	
		}
		
		if( cameraPosX + tileMaxWidth/2 > mapMaxX ) {
			
			cameraPosX = mapMaxX - tileMaxWidth/2;
	
		}
		
		if( cameraPosY + tileMaxHeight/2 > mapMaxY ) {
			
			cameraPosY = mapMaxY - tileMaxHeight/2;
	
		}
		
	}
	
	// Getters
	public int gettileMaxWidth() { return tileMaxWidth; }
	public int gettileMaxHeight() { return tileMaxHeight; }
	public int getWindowPosX() { return windowPosX; }
	public int getWindowPosY() { return windowPosY; }
	public int getCameraPosX() { return cameraPosX; }
	public int getCameraPosY() { return cameraPosY; }
	
	//Setters
	public void setCameraPosX( int cameraPosX ) { this.cameraPosX = cameraPosX; }
	public void setCameraPosY ( int cameraPosY ) { this.cameraPosY = cameraPosY; }
	
}
