//Author: Tyde Hashimoto

package com.screens;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.*;

import com.statemachine.*;

public class MainMenu extends JFrame implements IState, ActionListener {

	private StateMachine aStateMachine;
	private JFrame mainFrame;
	
	public MainMenu ( StateMachine aStateMachine ) {
		this.aStateMachine = aStateMachine;
	}
	
	private void run( JFrame aFrame ) {
		
		//Clear the frame
		mainFrame = aFrame;
		
		aFrame.repaint();
		
		JPanel menuPanel = new JPanel( new FlowLayout( FlowLayout.CENTER, 5, 10) );
		JButton playButton = new JButton("Play Game");
		playButton.addActionListener(this);
		playButton.setActionCommand("gamescreen");
		menuPanel.add(playButton);
		
		JButton mapEditorButton = new JButton("Map Editor");
		mapEditorButton.addActionListener(this);
		mapEditorButton.setActionCommand("mapeditor");
		menuPanel.add(mapEditorButton);
		
		JButton quitButton = new JButton( "Quit Game ");
		quitButton.addActionListener(this);
		quitButton.setActionCommand("quit");
		menuPanel.add( quitButton );

		aFrame.setLayout( new FlowLayout( FlowLayout.CENTER, 5, 0));
		aFrame.add( menuPanel );
		
		aFrame.revalidate();
		aFrame.setVisible(true);
		
	}
	
	@Override
	public void paint( Graphics g ) {
		
		super.paint(g);
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
        
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
	
		
	}

	@Override
	public void onEnter( JFrame aFrame ) {

		//Run the menu
		this.run(aFrame);
	}

	@Override
	public void onExit() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch ( e.getActionCommand() ) {
			case "mapeditor":
				aStateMachine.change("mapeditor");
				break;
			case "gamescreen":
				aStateMachine.change("gamescreen");
				break;
			case "quit":
				mainFrame.dispatchEvent( new WindowEvent( mainFrame, WindowEvent.WINDOW_CLOSING));
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + String.valueOf( e.getActionCommand()));
		}
	}

}
