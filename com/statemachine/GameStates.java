/*Author: Tyde Hashimoto
 * A enum of all possible game states
 */
package com.statemachine;

public enum GameStates {

	MAINMENU("mainmenu"), MAPEDITOR("mapeditor"), GAMESCREEN("gamescreen");
	
	String stateName;
	String className;
	
	GameStates( String stateName ) {
		
		this.stateName = stateName;
	}
	
	public String getStateName() { return stateName; }
	//public IState getGameState() { return gameState; }
	
}