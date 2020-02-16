//Author: Tyde Hashimoto

package com.screens;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.*;

import com.statemachine.*;

public class MainMenu extends JFrame implements IState{

	private StateMachine aStateMachine = null;
	private JFrame mainFrame;
	
	public MainMenu ( StateMachine aStateMachine ) {
		this.aStateMachine = aStateMachine;
	}
	
	/*
	//Empty constructor
	public MainMenu() {}
	
	public void setStateMachine ( StateMachine aStateMachine ) {
		this.aStateMachine = aStateMachine;
	}*/
	
	
	//Holds all the ui components
	class MenuPanel extends JPanel implements ActionListener {
		
		private int width = 200;
		private int height = 300;
		
		private int offSetY = 50;
		
		private int buttonWidth = 125;
		private int buttonHeight = 100000; //Don't ask why but this works
		
		public MenuPanel( JFrame aFrame ) {
			
			this.setBounds( aFrame.getContentPane().getWidth()/2 - width/2, aFrame.getContentPane().getHeight()/2 - height/2 + offSetY, width, height);
			this.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(15, 15, 15, 15 )));
			
			//this.setOpaque(true);
			this.setBackground(Color.WHITE );
			this.setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ));
			JButton playButton = new JButton("Start Game");
			playButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			playButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			playButton.addActionListener(this);
			playButton.setActionCommand("gamescreen");
			playButton.setAlignmentX( Component.CENTER_ALIGNMENT );
			
			JButton mapEditorButton = new JButton("Map Editor");
			mapEditorButton.addActionListener(this);
			mapEditorButton.setActionCommand("mapeditor");
			mapEditorButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			mapEditorButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			mapEditorButton.setAlignmentX( Component.CENTER_ALIGNMENT );
			
			JButton optionsButton = new JButton( "Options ");
			optionsButton.addActionListener(this);
			optionsButton.setActionCommand("options");
			optionsButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			optionsButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			optionsButton.setAlignmentX( Component.CENTER_ALIGNMENT );
			
			JButton quitButton = new JButton( "Quit Game ");
			quitButton.addActionListener(this);
			quitButton.setActionCommand("quit");
			quitButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			quitButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			quitButton.setAlignmentX( Component.CENTER_ALIGNMENT );
			
			this.add( playButton, BorderLayout.CENTER );
			this.add( Box.createVerticalGlue() );
			this.add( mapEditorButton, BorderLayout.CENTER );
			this.add( Box.createVerticalGlue() );
			this.add( optionsButton, BorderLayout.CENTER );
			this.add( Box.createVerticalGlue() );
			this.add( quitButton, BorderLayout.CENTER );
			//this.add( Box.createVerticalGlue() );
			//this.setVisible(true);
		}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch ( e.getActionCommand() ) {
					case "mapeditor":
						aStateMachine.change("mapeditor");
						break;
					case "gamescreen":
						aStateMachine.change("initscreen");
						break;
					case "quit":
						mainFrame.dispatchEvent( new WindowEvent( mainFrame, WindowEvent.WINDOW_CLOSING));
						break;
					case "options":
						System.out.println("I do nothing!");
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + String.valueOf( e.getActionCommand()));
				}
			}
	}
	private void run( JFrame aFrame ) {
		
		//Clear the frame
		mainFrame = aFrame;
		aFrame.repaint();
		
		MenuPanel menuPanel = new MenuPanel( aFrame );

		aFrame.setLayout( null );
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
		mainFrame.getContentPane().removeAll(); //Clear the frame
	}



}
