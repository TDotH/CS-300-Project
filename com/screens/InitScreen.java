/* Author: Tyde Hashimoto
 * Creates the Config.ini file that GameScreen uses to start a game
 */

package com.screens;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import com.statemachine.*;

public class InitScreen implements IState {

	private StateMachine aStateMachine;
	private JFrame aFrame;
	private JLayeredPane gamePanes;
	
	private static final String CONFIG_FILE = "src/maps/config.ini";
	
	//Used for default settings
	private static final String DEFAULT_MAP = "src/maps/default_map.map";
	private static final int    DEFAULT_ENERGY = 999;
	
	public InitScreen ( StateMachine aStateMachine ) {
		this.aStateMachine = aStateMachine;
	}
	
	//Lets the player choose if they want to just play using a default file setup or a custom game
	class StartPanel extends JPanel implements ActionListener {
		
		//Panel size
		private int width = 200;
		private int height = 300;
		
		private int offSetY = 50;
		
		public StartPanel ( JFrame aFrame ) {
			
			this.setBounds( new Rectangle( (aFrame.getContentPane().getWidth() - width) / 2, ( aFrame.getContentPane().getHeight() - height)/2 + offSetY, width , height ));
			this.setBackground( Color.WHITE );
			
			JButton quickButton = new JButton( "Quick Play" );
			quickButton.addActionListener(this);
			quickButton.setActionCommand("quick");
			
			JButton customButton = new JButton( "Custom Game" );
			
			JButton returnButton = new JButton( "Main Menu" );
			returnButton.addActionListener(this);
			returnButton.setActionCommand("menu");
			
			this.add( quickButton );
			this.add( customButton );
			this.add( returnButton );
			
		}
		
		//Create a custom file filter
		public class MapFilter extends FileFilter {
			
			public String getDescription() {
				return "Map Files (*.map)";
			}

			public boolean accept(File file) {
				//If the file extension is .map
				if (file.getName().endsWith(".map")) {
					return true;
				}
				return false;
			}
		}
		
		//Creates a default file for the game to load
		private void defaultSetup() {
			
	    	try {
	    		PrintWriter aWriter = new PrintWriter( CONFIG_FILE ); //Open file
	    		
	    		//Write the default map
	    		aWriter.println( DEFAULT_MAP );
	    		
	    		//Write how much energy the player should start with
	    		aWriter.println( String.valueOf( DEFAULT_ENERGY ));
	    		aWriter.close();
	    		
	    		//Change to the game screen if everything worked out
	    		aStateMachine.change("gamescreen");
	    		
	    	} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		//Allow the player to make a custom game
		private void openCustomPanel() {
			
			//Turn off all the buttons 
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch ( e.getActionCommand() ) {
			case "quick":
				defaultSetup();
				break;
			case "create":
				openCustomPanel();
				break;
			case "menu":
				aStateMachine.change("mainmenu");
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + String.valueOf( e.getActionCommand()));
			}
		}
	}
	
	//Used to create custom games
	class customPanel extends JPanel implements ActionListener {

		//Panel size
		private int width = 200;
		private int height = 300;
		
		private int offSetY = 50;
		
		public customPanel ( JFrame aFrame ) {
			this.setBounds( new Rectangle( (aFrame.getContentPane().getWidth() - width) / 2, ( aFrame.getContentPane().getHeight() - height)/2 + offSetY, width , height ));
			
			
		}
		
		public void openPanel() {
			
		}
		
		public void closePanel() {
			
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		
		
		
	}
	
	public void run( JFrame aFrame ) {
		
		this.aFrame = aFrame;
		//Make sure that the game is cleared
		aFrame.repaint();
		StartPanel startPanel = new StartPanel( aFrame );
		
		gamePanes = new JLayeredPane();
		gamePanes.setBounds(0, 0, aFrame.getContentPane().getSize().width , aFrame.getContentPane().getSize().height);
		aFrame.add( startPanel );
		
		
		aFrame.revalidate();
		aFrame.repaint();
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
	public void onEnter( JFrame aFrame) {

		run( aFrame );
	}

	@Override
	public void onExit() {
	
		aFrame.getContentPane().removeAll();
	}
	
}
