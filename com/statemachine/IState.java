//Author: Tyde Hashimoto
package com.statemachine;

import javax.swing.JFrame;

//Used help manage states
public interface IState {
	
	public void update();
	public void render();
	public void onEnter( JFrame aFrame );
	public void onExit();
	
}
