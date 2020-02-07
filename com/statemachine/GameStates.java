/*Author: Tyde Hashimoto
 * A enum of all possible game states
 */
package com.statemachine;

public enum GameStates {

	MAINMENU("mainmenu"), MAPEDITOR("mapeditor"), GAMESCREEN("gamescreen");
	
	String state;
	
	GameStates( String state ) {
		
		this.state = state;
	}
	
	public String getState() { return state; }
	
}
