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
	private JLayeredPane aLayeredPane;
	private StartPanel startPanel;
	private CustomPanel customPanel;
	
	//File that the config will be saved to
	private static final String CONFIG_FILE = "src/maps/config.ini";
	
	//Used for default settings
	private static final String DEFAULT_MAP = "src/maps/default_map.map";
	private static final int    DEFAULT_ENERGY = 50;
	
	public InitScreen ( StateMachine aStateMachine ) {
		this.aStateMachine = aStateMachine;
	}
	
	//Lets the player choose if they want to just play using a default file setup or a custom game
	class StartPanel extends JPanel implements ActionListener {
		
		//Panel size
		private int width = 200;
		private int height = 300;
		
		private int offSetY = 50;
		
		//Buttons
		private JButton quickButton;
		private JButton customButton;
		private JButton returnButton;
		
		public StartPanel ( JFrame aFrame ) {
			
			this.setBounds( new Rectangle( (aFrame.getContentPane().getWidth() - width) / 2, ( aFrame.getContentPane().getHeight() - height)/2 + offSetY, width , height ));
			this.setBackground( Color.WHITE );
			
			quickButton = new JButton( "Quick Play" );
			quickButton.addActionListener(this);
			quickButton.setActionCommand("quick");
			
			customButton = new JButton( "Custom Game" );
			
			returnButton = new JButton( "Main Menu" );
			returnButton.addActionListener(this);
			returnButton.setActionCommand("menu");
			
			this.add( quickButton );
			this.add( customButton );
			this.add( returnButton );
			
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
		private void closePanel() {
			
			//Turn off all the buttons 
			quickButton.setEnabled( false );
			customButton.setEnabled( false );
			returnButton.setEnabled( false );
			
			//Call the customization panel to the front
			customPanel.openPanel();
		}
		
		public void openPanel() {
			
			//Turn on all buttons
			quickButton.setEnabled( true );
			customButton.setEnabled( true );
			returnButton.setEnabled( true );
			
			//Move panel to the front
			aLayeredPane.moveToFront( this );
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch ( e.getActionCommand() ) {
			case "quick":
				this.defaultSetup();
				break;
			case "create":
				this.closePanel();
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
	class CustomPanel extends JPanel implements ActionListener {

		//Panel size
		private int width = 200;
		private int height = 300;
		private int offSetY = 50;
		
		private JButton playButton;
		private JButton returnButton;
		
		public CustomPanel ( JFrame aFrame ) {
			this.setBounds( new Rectangle( (aFrame.getContentPane().getWidth() - width) / 2, ( aFrame.getContentPane().getHeight() - height)/2 + offSetY, width , height ));
			this.setBackground( Color.WHITE );
			
			//Buttons
			playButton = new JButton( "Play Game!" );
			playButton.addActionListener(this);
			playButton.setActionCommand("quick");
			
			returnButton = new JButton( "Return" );
			returnButton.addActionListener(this);
			returnButton.setActionCommand("return");
			
			this.add( playButton );
			this.add( returnButton );
			
		}
		
		public void openPanel() {
			
			//Turn on the buttons
		}
		
		private void closePanel() {
		
			//Turn off everything
			
		}
		
		//Setup config file according to player inputs
		private void customSetup() {
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
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch ( e.getActionCommand() ) {
			case "play":
				this.customSetup();
				break;
			case "return":
				this.closePanel();
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + String.valueOf( e.getActionCommand()));
			}
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
	}
	
	public void run( JFrame aFrame ) {
		
		this.aFrame = aFrame;
		//Make sure that the game is cleared
		aFrame.repaint();
		StartPanel startPanel = new StartPanel( aFrame );
		
		aLayeredPane = new JLayeredPane();
		aLayeredPane.setBounds(0, 0, aFrame.getContentPane().getSize().width , aFrame.getContentPane().getSize().height);
		
		aLayeredPane.add( startPanel, 0 );
		
		
		aFrame.add( aLayeredPane );
		
		
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
