/* Author: Tyde Hashimoto
 * Creates the Config.ini file that GameScreen uses to start a game
 */

package com.screens;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.ArrayList;

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
	private static final int    DEFAULT_ENERGY = 20;
	
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
			customButton.addActionListener(this);
			customButton.setActionCommand("create");
			
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
			
			this.setVisible( false );
			
		}
		
		public void openPanel() {
			
			//Turn on all buttons
			quickButton.setEnabled( true );
			customButton.setEnabled( true );
			returnButton.setEnabled( true );
			
			//Move panel to the front
			aLayeredPane.moveToFront( this );
			
			this.setVisible( true );
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
				//Call the customization panel to the front
				customPanel.openPanel();
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
	class CustomPanel extends JPanel {

		final String MAPS_PATH = "src/maps";
		
		//Panel size
		private int width = 200;
		private int height = 300;
		private int offSetY = 50;
		
		private JButton playButton;
		private JButton returnButton;
		JSlider energySlider;
		JComboBox mapList;
		
		String selectedMap;
		int energy;
		final int MAX_ENERGY = 99;
		
		public CustomPanel ( JFrame aFrame ) {
			
			this.setBounds( new Rectangle( (aFrame.getContentPane().getWidth() - width) / 2, ( aFrame.getContentPane().getHeight() - height)/2 + offSetY, width , height ));
			this.setBackground( Color.WHITE );
			
			//Buttons
			playButton = new JButton( "Play Game!" );
			playButton.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					customSetup();
				}
			});
			
			returnButton = new JButton( "Return" );
			returnButton.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					closePanel();
					startPanel.openPanel();
				}
			});
			
			selectedMap = "default_map.map";
			
			//Pull Down Menu to pick map from maps folder
			File file = new File( MAPS_PATH );
			String[] maps = file.list(  new MapFilter() );
			mapList = new JComboBox( maps );
			//mapList.setSelectedIndex( 2 );
			mapList.setSelectedItem( selectedMap );
			mapList.setEditable( false );
			mapList.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					
					JComboBox cb = (JComboBox)e.getSource();
					selectedMap = (String)cb.getSelectedItem();
				}
			});
			//mapList.addActionListener(this);
			
			//Slider creation to set player energy
			JLabel energyLabel = new JLabel( "Player Energy", JLabel.CENTER );
			energyLabel.setAlignmentX( Component.CENTER_ALIGNMENT );
			energySlider = new JSlider( JSlider.HORIZONTAL, 1, MAX_ENERGY, 5);
			energySlider.setMajorTickSpacing(10);
			energySlider.setMinorTickSpacing(1);
			energySlider.setPaintTicks(true);
			energySlider.setPaintLabels(true);
			
			this.add( playButton );
			this.add( mapList );
			this.add( energyLabel );
			this.add( energySlider );
			this.add( Box.createVerticalGlue() );
			this.add( returnButton );
			
			//Disable everything first
			playButton.setEnabled( false );
			returnButton.setEnabled( false );
			mapList.setEnabled( false );
			energySlider.setEnabled( false );
			
			this.setVisible( false );
			
		}
		
		public void openPanel() {
			
			this.setVisible( true );
			
			//Turn on everything
			playButton.setEnabled( true );
			returnButton.setEnabled( true );
			mapList.setEnabled( true );
			energySlider.setEnabled( true );
			
			//Move the panel to the front
			aLayeredPane.moveToFront( this );
			
		}
		
		private void closePanel() {
		
			//Turn off everything
			playButton.setEnabled( false );
			returnButton.setEnabled( false );
			mapList.setEnabled( false );
			energySlider.setEnabled( false );
			
			this.setVisible( false );
		}
		
		//Setup config file according to player inputs
		private void customSetup() {
	    	try {
	    		PrintWriter aWriter = new PrintWriter( CONFIG_FILE ); //Open file
	    		
	    		//Write the wanted map
	    		aWriter.println( MAPS_PATH + "/" + selectedMap );
	    		
	    		//Write how much energy the user wants to start with
	    		aWriter.println( String.valueOf( String.valueOf( energySlider.getValue() ) ));
	    		aWriter.close();
	    		
	    		//Change to the game screen if everything worked out
	    		aStateMachine.change("gamescreen");
	    		
	    	} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		//Create a custom file filter
		public class MapFilter implements FilenameFilter {
			
			/*
			public String getDescription() {
				return "Map Files (*.map)";
			}
			public boolean accept(File file) {
				//If the file extension is .map
				if (file.getName().endsWith(".map")) {
					return true;
				}
				return false;
			}*/
			@Override
			public boolean accept(File file, String name) {
				if (name.endsWith(".map")) {
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
		startPanel = new StartPanel( aFrame );
		customPanel = new CustomPanel( aFrame );
		
		aLayeredPane = new JLayeredPane();
		aLayeredPane.setBounds(0, 0, aFrame.getContentPane().getSize().width , aFrame.getContentPane().getSize().height);
		
		aLayeredPane.add( startPanel, 0 );
		aLayeredPane.add( customPanel, 1 );
		
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
