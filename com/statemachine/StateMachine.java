/*Author: Tyde Hashimoto
 * 
 *  Manages the various states in the game (Main menu, map editor, etc)
 *  Passes stuff to the event log
 */
package com.statemachine;
import java.awt.KeyboardFocusManager;
import java.util.HashMap;

import javax.swing.JFrame;

public class StateMachine {
	
	//Create a map to put in all possible states
	HashMap<String, IState> mStates = new HashMap<String, IState>();
	IState currentState = null;
	
	//Main game frame
	JFrame mainFrame;
	
	public StateMachine ( JFrame mainFrame ) {
		this.mainFrame = mainFrame;
	}
	
	//Run update on current state
	public void update() {
		
		//Check if there is a state
		if ( currentState != null ) {
			currentState.update();
		}
	}
	
	public void render() {
		
		if ( currentState != null ) {
			currentState.render();
		}
	}
	
	public void change( String stateName ) {
		
		//System.out.println(stateName);
		if ( currentState != null ) {
			mainFrame.getContentPane().removeAll(); //Clean the frame
		}
		currentState = mStates.get(stateName);
		currentState.onEnter( mainFrame );
	}
	
	public void add(String name, IState state) {
		mStates.put(name, state);
	}

}
