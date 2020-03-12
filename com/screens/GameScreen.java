/* Author: Tyde Hashimoto
 * The main game screen with map, event log, and inventory
 * 
 */

package com.screens;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.swing.*;

import com.inventory.Item;
import com.statemachine.*;
import com.map.Map;
import com.map.Tile;
import com.player.*;
import com.eventlog.*;

public class GameScreen implements IState {

	//Sorry about all the globals, the ui stuff is a pain to work around without them
	private StateMachine aStateMachine;
	private MapScreenPanel aMapPanel;
	private EventLogPanel aEventLogPanel;
	private InventoryPanel aInventoryPanel;
	private KeyboardFocusManager manager;
	private GameMenu gameMenu;
	private JLayeredPane gamePanes;
	private boolean paused;
	private Player player;
	private Map map;
	private EventLog eventLog;
	
	public JFrame aFrame;
	
	private static final int MAP_SCREEN_PANEL_WIDTH = 624;
	
	public GameScreen ( StateMachine aStateMachine ) {
		
		this.aStateMachine = aStateMachine;
		this.eventLog = new EventLog();
	}
	
	//Holds the map and player
	class MapScreenPanel extends JPanel{
		private Camera camera;
		
		//Config file path
		private static final String CONFIG_FILE = "src/maps/config.ini";
		
		KeyDispatcher aKeyDispatcher = new KeyDispatcher();
		
		public MapScreenPanel( JFrame aFrame ) {
			this.setBounds(0, 0, MAP_SCREEN_PANEL_WIDTH, aFrame.getContentPane().getSize().height );
		
			//Load the config file
			this.loadConfig();
			
			this.setBackground( Color.DARK_GRAY );
			manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			manager.addKeyEventDispatcher( aKeyDispatcher );

		}
		
		//Load config file and setup map, player, and camera
		private void loadConfig() {
			
	    	try {
	    		
	    		String tempString;
	    		
				File file = new File( CONFIG_FILE ); //Read in file
			    BufferedReader br = new BufferedReader(new FileReader(file)); //set up buffer reader

			    //First line is map location
			    tempString = br.readLine();
				map = new Map();
				try {
					map.loadMap( tempString );

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Second line is player's starting energy
			    tempString = br.readLine();
				try {
					player = new Player( map.getStartX(), map.getStartY(), map.getWidth(), map.getHeight(), eventLog);
					camera = new Camera( this.getWidth(), this.getHeight(), map.getTileSize(), map.getStartX(), map.getStartY(), map.getWidth(), map.getHeight() );
					player.setEnergy( Integer.parseInt( tempString ));
					eventLog.update(map.get_tile(player.getPosX(), player.getPosY()), player);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	    		br.close(); //Close the stream
	    	} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		private void closeManager() {
			manager.removeKeyEventDispatcher(aKeyDispatcher);
		}
		
		//Update the camera
		public void update() {
			camera.update( player.getPosX(), player.getPosY() );
		}
		
		@Override
		protected void paintComponent( Graphics g ) {
			
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
			map.draw(g2d, player.getPosX(), player.getPosY(), camera);
			player.draw(g2d, map.getTileSize(), camera);
			
		}

		//Used instead of key listeners since it was being a pain
	    private class KeyDispatcher implements KeyEventDispatcher {
	        @Override
	        public boolean dispatchKeyEvent(KeyEvent e) {
	            if (e.getID() == KeyEvent.KEY_PRESSED) {

	            	
	            } 
	            else if (e.getID() == KeyEvent.KEY_RELEASED) {
	            	if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
	            		//manager.removeKeyEventDispatcher(aKeyDispatcher);
	            		if ( paused == false ) {
	            			gameMenu.openMenu(aFrame, player, map);
	            		} 
	            		else if ( paused == true ) {
	            			gameMenu.closeMenu();
	            		}
	            	}
	            	else {
		            	if ( paused == false ) {
		            		player.keyPressed( e, map );
		            		System.out.println("Energy remaining: " + player.getEnergy());
		            	}
	            	}
	            } 
	            else if (e.getID() == KeyEvent.KEY_TYPED) {

	            }
	            return false;
	        }
	    }
		
	}
	
	//Holds the menu screen
	class GameMenu extends JPanel implements ActionListener {
		
		private int width = 200;
		private int height = 300;
		
		private int offSetY = 50;
		
		private int buttonWidth = 125;
		private int buttonHeight = 25; 
		
		private JButton resumeButton;
		private JButton menuButton;
		private JButton quitButton;
		
		public GameMenu() {
			
			paused = false;
	
			this.setBounds(0, 0, aFrame.getContentPane().getWidth(), aFrame.getContentPane().getHeight());
			this.setBackground( new Color(100, 100, 100, 100) );
			
			//Panel to hold all the buttons
			JPanel menuPanel = new JPanel();
			menuPanel.setBounds( this.getWidth()/2 - width/2, aFrame.getContentPane().getHeight()/2 - height/2 + offSetY, width, height);
			menuPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(15, 15, 15, 15 )));
			
			menuPanel.setBackground( Color.WHITE );
			menuPanel.setLayout( new BoxLayout( menuPanel, BoxLayout.PAGE_AXIS ));
			//menuPanel.setLayout( null );
			
			resumeButton = new JButton("Resume");
			resumeButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			resumeButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			resumeButton.addActionListener(this);
			resumeButton.setActionCommand("resume");
			resumeButton.setAlignmentX( Component.CENTER_ALIGNMENT );
			
			menuButton = new JButton( "Main Menu");
			menuButton.addActionListener(this);
			menuButton.setActionCommand("menu");
			menuButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			menuButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			menuButton.setAlignmentX( Component.CENTER_ALIGNMENT );
			
			quitButton = new JButton( "Quit Game");
			quitButton.addActionListener(this);
			quitButton.setActionCommand("quit");
			quitButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			quitButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			quitButton.setAlignmentX( Component.CENTER_ALIGNMENT );
			
			menuPanel.add( resumeButton, BorderLayout.CENTER );
			menuPanel.add( Box.createVerticalGlue() );
			menuPanel.add( menuButton, BorderLayout.CENTER );
			menuPanel.add( Box.createVerticalGlue() );
			menuPanel.add( quitButton, BorderLayout.CENTER );
			
			resumeButton.setEnabled(false);
			menuButton.setEnabled(false);
			quitButton.setEnabled(false);
			
			this.add(menuPanel);
		}
		
		//Will be used for saving and loading potentially later
		public void openMenu( JFrame aFrame, Player player, Map map ) {
			
			gamePanes.moveToFront(this);
			//Enable the buttons
			resumeButton.setEnabled(true);
			menuButton.setEnabled(true);
			quitButton.setEnabled(true);
			paused = true;
			//System.out.println("here");
			
		}
		
		public void closeMenu() {
			
			gamePanes.moveToBack(this);
			//Disable the buttons so they wont react when moused over
			resumeButton.setEnabled(false);
			menuButton.setEnabled(false);
			quitButton.setEnabled(false);
			paused = false;
		}
		
		@Override
		protected void paintComponent( Graphics g ) {
			
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch ( e.getActionCommand() ) {
			case "resume":
				closeMenu();
				break;
			case "menu":
				paused = false; //Make sure paused is always false
				aStateMachine.change("mainmenu");
				break;
			case "quit":
				aFrame.dispatchEvent( new WindowEvent( aFrame, WindowEvent.WINDOW_CLOSING));
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + String.valueOf( e.getActionCommand()));
			}
		}
	}
	
	/*
	//Initial menu the player views
	class LoadPanel extends JPanel implements ActionListener {
		
		public LoadPanel( JFrame frame ) {
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}*/
	
	//Holds Events
	class EventLogPanel extends JPanel {

		public EventLogPanel( JFrame aFrame ) {
			
			this.setBounds(MAP_SCREEN_PANEL_WIDTH, aFrame.getContentPane().getSize().height/2, aFrame.getContentPane().getSize().width - MAP_SCREEN_PANEL_WIDTH, aFrame.getContentPane().getSize().height/2 );
			this.setBackground(Color.yellow);
		}

		private void drawString(Graphics g, String text, int x, int y){
			for(String line: text.split("\n")){
				g.drawString(line, x, y += g.getFontMetrics().getHeight());
			}
		}
		
		@Override
		protected void paintComponent( Graphics g ) {
			
			super.paintComponent(g);
			drawString(g, eventLog.display(), this.getWidth()/15, this.getHeight()/10);

		}
	}
	
	//Holds the inventory
	class InventoryPanel extends JPanel {
		
		public InventoryPanel( JFrame aFrame ) {
			this.setBounds(MAP_SCREEN_PANEL_WIDTH, 0, aFrame.getContentPane().getSize().width - MAP_SCREEN_PANEL_WIDTH, aFrame.getContentPane().getSize().height/2 );
			this.setBackground(Color.cyan);
			//System.out.println("Size:( " + ( aFrame.getContentPane().getSize().width - MAP_SCREEN_PANEL_WIDTH )+ "," + aFrame.getContentPane().getSize().height/2 + " )" );

		}
		
		@Override
		protected void paintComponent( Graphics g ) {
			
			super.paintComponent(g);
			//Graphics2D g2d = (Graphics2D) g;
			g.drawString("Inventory goes here", this.getWidth()/3, this.getHeight()/2 );
			
		}
		
	}
	
	private void run( JFrame aFrame ) {
		
		this.aFrame = aFrame;
		//Make sure that the game is cleared
		aFrame.repaint();
		
		//Initialize on screen panels
		aMapPanel = new MapScreenPanel( aFrame );
		aEventLogPanel = new EventLogPanel( aFrame );
		aInventoryPanel = new InventoryPanel( aFrame );
		gameMenu = new GameMenu();
		
		//Used so we can make panels on top of other panels
		gamePanes = new JLayeredPane();
		gamePanes.setBounds(0, 0, aFrame.getContentPane().getSize().width , aFrame.getContentPane().getSize().height);
		
		//Panel to hold all the panels
		JPanel gamePanelsHolder = new JPanel( null );
		gamePanelsHolder.setBounds(0, 0, aFrame.getContentPane().getSize().width , aFrame.getContentPane().getSize().height);
		gamePanelsHolder.add( aMapPanel );
		gamePanelsHolder.add( aEventLogPanel );
		gamePanelsHolder.add( aInventoryPanel );
		
		gamePanes.add( gamePanelsHolder, 0 );
		gamePanes.add( gameMenu, 1 );
		
		aFrame.add( gamePanes );
		
		//aFrame.setLayout( null );
		aFrame.revalidate();
		aFrame.repaint();
		
	}
	
	
	@Override
	public void update() {

		if ( aMapPanel != null ) {
			aMapPanel.update();
		}
	}

	@Override
	public void render() {
		aFrame.repaint();
		
	}

	@Override
	public void onEnter(JFrame aFrame) {
		//run this state
		run(aFrame);
		
	}

	@Override
	public void onExit() {
		aMapPanel.closeManager();
		aFrame.getContentPane().removeAll();
	}

}
