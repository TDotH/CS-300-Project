/* Author: Tyde Hashimototem
 * The main game screen with map, event log, and inventory
 *
 */

package com.screens;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.statemachine.*;
import com.actors.ShopKeeper;
import com.eventlog.EventLog;
import com.inventory.Item;
import com.map.Map;
import com.player.*;
import com.screens.GameScreen.ShopKeepPanel;

public class GameScreen implements IState {

	//Sorry about all the globals, the ui stuff is a pain to work around without them
	private StateMachine aStateMachine;
	private MapScreenPanel aMapScreenPanel;
	private GuiPanel aGuiPanel;
	private EventLogPanel aEventLogPanel;
	private InventoryPanel aInventoryPanel;
	private WinPanel aWinPanel;
	private LosePanel aLosePanel;
	private ShopKeepPanel aShopKeepPanel;
	private KeyboardFocusManager manager;
	private GameMenu gameMenu;
	private JLayeredPane gamePanes;

	private boolean paused; //Used to prevent player from moving while the game is paused
	private boolean finished; //Used so that when the player wants to explore after they win, the player is unaffected by stuff

	public JFrame aFrame;
	private EventLog eventLog;
	

	private static final int MAP_SCREEN_PANEL_WIDTH = 624;

	public GameScreen ( StateMachine aStateMachine ) {

		this.aStateMachine = aStateMachine;
		this.eventLog = new EventLog();
	}

	//Holds the map and player
	class MapScreenPanel extends JPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Map map;
		private Player player;
		private Camera camera;

		//Config file path
		private static final String CONFIG_FILE = "src/maps/config.ini";

		KeyDispatcher aKeyDispatcher = new KeyDispatcher();
		
		private Border raisedBorder = BorderFactory.createBevelBorder( BevelBorder.RAISED, new Color(56,112,255) ,new Color(0, 51, 179));
		private Border loweredBorder= BorderFactory.createBevelBorder( BevelBorder.LOWERED,  new Color(56,112,255), new Color(0, 51, 179));
		private Border panelBorder = BorderFactory.createCompoundBorder( loweredBorder, 
				BorderFactory.createCompoundBorder( raisedBorder, loweredBorder));

		public MapScreenPanel( JFrame aFrame ) {
			this.setBounds(0, 0, MAP_SCREEN_PANEL_WIDTH, aFrame.getContentPane().getSize().height );
			this.setBorder(panelBorder);
			
			//Load the config file
			this.loadConfig();

			this.setBackground( Color.BLACK );
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
					player = new Player( map.getStartX(), map.getStartY(), map.getWidth(), map.getHeight(), eventLog );
					camera = new Camera( this.getWidth(), this.getHeight(), map.getTileSize(), map.getStartX(), map.getStartY(), map.getWidth(), map.getHeight() );
					player.setEnergy( Integer.parseInt( tempString ));

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Third line is player's starting money
			    tempString = br.readLine();
				try {
					player.setMoney( Integer.parseInt( tempString ));

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

		//Restarts map and player
		protected void restartMap() {
			loadConfig();
			aInventoryPanel.setInventory();
		}

		//Stops the user from doing key inputs
		protected void stopManager() {
			manager.removeKeyEventDispatcher(aKeyDispatcher);
		}

		//Allows the user to do key inputs
		protected void startManager() {
			manager.addKeyEventDispatcher( aKeyDispatcher );
		}

		//Update the camera
		public void update() {
			camera.update( player.getPosX(), player.getPosY() );
			
			//Update the inventory panel if need be
			if ( aInventoryPanel != null ) {
				if ( player.getInventoryFlag() == true );
				aInventoryPanel.update();
			}
		}

		@Override
		protected void paintComponent( Graphics g ) {

			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
			map.draw(g2d, player.getPosX(), player.getPosY(), player.getVisiontRadius(),  camera, finished, this.getInsets().top);
			player.draw(g2d, map.getTileSize(), camera, this.getInsets().top);

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
		            		//System.out.println("Energy remaining: " + player.getEnergy());
		            		winFlagCheck();

		            	}
	            	}
	            }
	            else if (e.getID() == KeyEvent.KEY_TYPED) {

	            }
	            return false;
	        }
	    }

	    //The cheat button
	    protected void winButtonPressed() {

	    	//Give the jewel to the player
	    	player.addItem( map.getJewel() );
	    	//Set the jewel's tile on the map to null
	    	map.get_tile( map.getJewelX(), map.getJewelY() ).setObject( null );
	    	winFlagCheck();
	    }

	    //Check for win flags
	    protected void winFlagCheck() {
    		// Check for flags
    		if ( finished == false ) {
    			if ( player.getWinFlag() == true ) {
    				aWinPanel.openMenu(aFrame);
    			} else if ( player.getLoseFlag() == true ) {
    				aLosePanel.openMenu(aFrame);
    			} else if ( player.inDialogue() ){
    				
    				if ( aShopKeepPanel != null ) {
    	   				aShopKeepPanel.openMenu(aFrame, player);
        				aShopKeepPanel.updateMenu();
    				}
				}
    		}
	    }

	    protected void setPlayerInventoryFlag( boolean flag ) { player.setInventoryFlag( flag ); } 
	    protected void togglePlayerDialog() { player.toggleDialogue(); }
	    
	    //Getters for the gui
	    protected int getPlayerEnergy() { return player.getEnergy(); }
	    protected int getPlayerMoney() { return player.getMoney(); }
	    protected ArrayList<Item> getPlayerInventory() { return player.getInventory(); }
	    protected boolean getPlayerInventoryFlag() { return player.getInventoryFlag(); }
	    public ShopKeeper getShopKeeper() { return map.getShopkeep(); }   
	}

	//Holds the gui
	class GuiPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Image guiset;
		private int imageSize = 32;

		private JLabel energyLabel;
		private JLabel moneyLabel;
		private int currentEnergy = 0;
		private int currentMoney = 0;

		private int enMoGuiWidth = 96;
		private int enMoGuiHeight = 64;
		private int guiOffset = 25;

		private int sidesOffset = 6;

		public float guiTransparency = .75f;

		private int guiSize = 48;
		
		private Border raisedBorder = BorderFactory.createBevelBorder( BevelBorder.RAISED, new Color(56,112,255) ,new Color(0, 51, 179));
		private Border loweredBorder= BorderFactory.createBevelBorder( BevelBorder.LOWERED,  new Color(56,112,255), new Color(0, 51, 179));
		private Border panelBorder = BorderFactory.createCompoundBorder( raisedBorder, loweredBorder );

		public GuiPanel() {

		  	try {
	    		guiset = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/images/guiset.png"));
	    	}
	    	catch (IOException ex) {
	    		System.out.println("Error! guiset can't be loaded!");
	    	}

			this.setBounds(0, 0, MAP_SCREEN_PANEL_WIDTH, aFrame.getContentPane().getSize().height );
			this.setLayout( null );
			this.setBackground( new Color(0, 0, 0, 0));

			JPanel energyPanel = new JPanel() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2d = (Graphics2D)g;

					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, guiTransparency));
					g2d.drawImage(guiset, 0, 0 + sidesOffset, guiSize, guiSize + sidesOffset,
							0, 0, imageSize, imageSize, null);
				}

			};
			energyPanel.setBounds( this.getWidth() - enMoGuiWidth*2 - guiOffset*2,  guiOffset, enMoGuiWidth, enMoGuiHeight);
			energyPanel.setBorder( panelBorder );

			energyLabel = new JLabel(String.valueOf(currentEnergy), SwingConstants.RIGHT);
			energyLabel.setFont( new Font( energyLabel.getName(), Font.PLAIN, 25));
			energyLabel.setBounds(38,8, 50, 50);
			energyPanel.setLayout( null );
			energyPanel.add( energyLabel );

			JPanel moneyPanel = new JPanel() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2d = (Graphics2D)g;

					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, guiTransparency));
					g2d.drawImage(guiset, 0, 0 + sidesOffset, guiSize, guiSize + sidesOffset,
							imageSize, 0, imageSize*2, imageSize, null);
				}

			};
			moneyPanel.setBounds( this.getWidth() - enMoGuiWidth - guiOffset,  guiOffset, enMoGuiWidth, enMoGuiHeight);
			moneyPanel.setBorder( panelBorder );

			moneyLabel = new JLabel(String.valueOf(currentEnergy), SwingConstants.RIGHT);
			moneyLabel.setFont( new Font( moneyLabel.getName(), Font.PLAIN, 25));
			moneyLabel.setBounds(38,8, 50, 50);
			moneyPanel.setLayout( null );
			moneyPanel.add( moneyLabel );

			this.add( energyPanel );
			this.add( moneyPanel );
		}

		public void updateGui() {

			if ( finished == false ) {
				//Check if the panel needs to update
				if ( aMapScreenPanel.getPlayerEnergy() != currentEnergy ) {
					currentEnergy = aMapScreenPanel.getPlayerEnergy();
					energyLabel.setText( String.valueOf( Math.max(0, currentEnergy) ) );
					
				}
				if ( aMapScreenPanel.getPlayerMoney() != currentMoney ) {
					currentMoney = aMapScreenPanel.getPlayerMoney();
					moneyLabel.setText( String.valueOf( Math.max(0, currentMoney) ) );
				}
			}
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, guiTransparency));
		}
	}

	//Holds the win panel
	class WinPanel extends JPanel implements ActionListener{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int width = 300;
		private int height = 100;

		private int offSetY = 0;

		private int buttonWidth = 100;
		private int buttonHeight = 50;

		JButton resumeButton;
		JButton menuButton;

		public WinPanel() {

			this.setBounds(0, 0, aFrame.getContentPane().getWidth(), aFrame.getContentPane().getHeight());
			this.setBackground( new Color( 100, 100, 100, 100 ) );

			//Panel to hold all the stuff
			JPanel menuPanel = new JPanel();
			menuPanel.setBounds( aFrame.getContentPane().getWidth()/2 - width/2, aFrame.getContentPane().getHeight()/2 - height/2 + offSetY, width, height);
			menuPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder( 15, 15, 15, 15 )));

			menuPanel.setBackground( Color.WHITE );
			menuPanel.setLayout( new BoxLayout( menuPanel, BoxLayout.PAGE_AXIS ));
			//menuPanel.setLayout( new BorderLayout() );;
			this.setLayout( null );

			JLabel label = new JLabel("Great job, you got the Jewel! You Win!");
			label.setAlignmentX( Component.CENTER_ALIGNMENT );

			resumeButton = new JButton("Resume");
			resumeButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			resumeButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			resumeButton.setPreferredSize(new Dimension( buttonWidth, buttonHeight ));
			resumeButton.addActionListener(this);
			resumeButton.setActionCommand("resume");
			resumeButton.setAlignmentX( Component.CENTER_ALIGNMENT );

			menuButton = new JButton( "Main Menu");
			menuButton.addActionListener(this);
			menuButton.setActionCommand("menu");
			menuButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			menuButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			menuButton.setPreferredSize( new Dimension( buttonWidth, buttonHeight ));
			menuButton.setAlignmentX( Component.CENTER_ALIGNMENT );

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout( new FlowLayout() );
			buttonPanel.setBackground( Color.WHITE );
			buttonPanel.add( resumeButton );
			buttonPanel.add( menuButton );

			menuPanel.add(label, BorderLayout.PAGE_START);
			menuPanel.add( Box.createVerticalGlue() );
			menuPanel.add( buttonPanel , BorderLayout.CENTER );

			resumeButton.setEnabled(false);
			menuButton.setEnabled(false);

			this.add(menuPanel);
		}

		//Open the panel
		public void openMenu( JFrame aFrame ) {

			aMapScreenPanel.stopManager();
			paused = true;
			gamePanes.moveToFront(this);
			//Enable the buttons
			resumeButton.setEnabled(true);
			menuButton.setEnabled(true);
			//System.out.println("here");

		}

		//Closes the menu
		public void closeMenu() {

			paused = false;
			gamePanes.moveToBack(this);
			//Disable the buttons so they wont react when moused over
			resumeButton.setEnabled(false);
			menuButton.setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch ( e.getActionCommand() ) {
			case "resume":
				finished = true;
				aMapScreenPanel.startManager();
				closeMenu();
				break;
			case "menu":
				paused = false; //Make sure paused is always false
				aStateMachine.change("mainmenu");
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + String.valueOf( e.getActionCommand()));
			}
		}
	}

	//Holds the lose panel
	class LosePanel extends JPanel implements ActionListener{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int width = 300;
		private int height = 100;

		private int offSetY = 0;

		private int buttonWidth = 100;
		private int buttonHeight = 50;

		JButton restartButton;
		JButton menuButton;

		public LosePanel() {

			this.setBounds(0, 0, aFrame.getContentPane().getWidth(), aFrame.getContentPane().getHeight());
			this.setBackground( new Color(100, 100, 100, 100) );

			//Panel to hold all the stuff
			JPanel menuPanel = new JPanel();
			menuPanel.setBounds( aFrame.getContentPane().getWidth()/2 - width/2, aFrame.getContentPane().getHeight()/2 - height/2 + offSetY, width, height);
			menuPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder( 15, 15, 15, 15 )));

			menuPanel.setBackground( Color.WHITE );
			menuPanel.setLayout( new BoxLayout( menuPanel, BoxLayout.PAGE_AXIS ));
			this.setLayout( null );

			JLabel label = new JLabel("Oh no, you ran out of energy!");
			label.setAlignmentX( Component.CENTER_ALIGNMENT );

			restartButton = new JButton("Restart");
			restartButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			restartButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			restartButton.setPreferredSize( new Dimension( buttonWidth, buttonHeight ));
			restartButton.addActionListener(this);
			restartButton.setActionCommand("restart");
			restartButton.setAlignmentX( Component.CENTER_ALIGNMENT );

			menuButton = new JButton( "Main Menu");
			menuButton.addActionListener(this);
			menuButton.setActionCommand("menu");
			menuButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			menuButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			menuButton.setPreferredSize( new Dimension( buttonWidth, buttonHeight ));
			menuButton.setAlignmentX( Component.CENTER_ALIGNMENT );

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout( new FlowLayout() );
			buttonPanel.setBackground( Color.WHITE );
			buttonPanel.add( restartButton );
			buttonPanel.add( menuButton );

			menuPanel.add(label, BorderLayout.PAGE_START );
			menuPanel.add( Box.createVerticalGlue() );
			menuPanel.add( buttonPanel, BorderLayout.CENTER );

			restartButton.setEnabled(false);
			menuButton.setEnabled(false);

			this.add(menuPanel);
		}

		//Open the panel
		public void openMenu( JFrame aFrame ) {

			aMapScreenPanel.stopManager();
			paused = true;
			gamePanes.moveToFront(this);
			//Enable the buttons
			restartButton.setEnabled(true);
			menuButton.setEnabled(true);
			//System.out.println("here");

		}

		//Closes the menu
		public void closeMenu() {

			paused = false;
			gamePanes.moveToBack(this);
			//Disable the buttons so they wont react when moused over
			restartButton.setEnabled(false);
			menuButton.setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch ( e.getActionCommand() ) {
			case "restart":
				aMapScreenPanel.restartMap();
				aMapScreenPanel.startManager();
				closeMenu();
				break;
			case "menu":
				paused = false; //Make sure paused is always false
				aStateMachine.change("mainmenu");
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + String.valueOf( e.getActionCommand()));
			}
		}

	}

	//Holds the ShopKeeps panel
	class ShopKeepPanel extends JPanel implements ActionListener{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int width = 500;
		private int height = 300;

		private int offSetY = 0;

		private int buttonWidth = 200;
		private int buttonHeight = 25;

		private Player player;
		private ShopKeeper shopKeep;

		HashMap<JButton, Item> itemsDict = new HashMap<JButton, Item>();

		JPanel menuPanel;
		JButton exitButton;
		ArrayList<JButton> buttons  = new ArrayList<JButton>();
		JLabel label;

		public ShopKeepPanel( ShopKeeper shopkeep) {

			this.setBounds(0, 0, aFrame.getContentPane().getWidth(), aFrame.getContentPane().getHeight());
			this.setBackground( new Color( 100, 100, 100, 100 ) );
			this.setLayout(null);
			
			this.shopKeep = shopkeep;
			
			generateMenu();

		}

		public void generateMenu(){
			menuPanel = new JPanel();
			menuPanel.setBounds( aFrame.getContentPane().getWidth()/2 - width/2, aFrame.getContentPane().getHeight()/2 - height/2 + offSetY, width, height);
			menuPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder( 15, 15, 15, 15 )));

			menuPanel.setBackground( Color.WHITE );
			menuPanel.setLayout( new BoxLayout( menuPanel, BoxLayout.PAGE_AXIS ));
			//menuPanel.setLayout( new BorderLayout() );;
			this.setLayout( null );

			String msg = "TEST";//shopKeep.getHello();

			//ArrayList<Item> inv = shopKeep.getInventory();

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout( new FlowLayout() );
			buttonPanel.setBackground( Color.WHITE );

			for(int x = 0; x < shopKeep.getInventory().size(); x++ ){
				JButton thisJButton = new JButton("TEST");//item.getName() + " - " + item.getValue() + "G");
				thisJButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
				thisJButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
				thisJButton.setPreferredSize(new Dimension( buttonWidth, buttonHeight ));
				thisJButton.addActionListener(this);
				thisJButton.setActionCommand("sell");
				thisJButton.setAlignmentX( Component.CENTER_ALIGNMENT );

				thisJButton.setEnabled(false);

				buttons.add(thisJButton);
				buttonPanel.add(thisJButton);
			}

			exitButton = new JButton("Goodbye (Exit)");
			exitButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			exitButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			exitButton.setPreferredSize(new Dimension( buttonWidth, buttonHeight ));
			exitButton.addActionListener(this);
			exitButton.setActionCommand("resume");
			exitButton.setAlignmentX( Component.CENTER_ALIGNMENT );
			exitButton.setEnabled(false);
			buttonPanel.add(exitButton);

			label = new JLabel(msg);
			label.setAlignmentX( this.CENTER_ALIGNMENT );

			menuPanel.add(label, BorderLayout.PAGE_START);
			menuPanel.add( Box.createVerticalGlue() );
			menuPanel.add( buttonPanel , BorderLayout.CENTER );

			this.add(menuPanel);
		}

		//Open the panel
		public void openMenu( JFrame aFrame, Player aPlayer ) {
			player = aPlayer;
			//shopKeep = player.speakingWith();
			aMapScreenPanel.stopManager();
			paused = true;
			gamePanes.moveToFront(this);
			//Enable the buttons
			for (JButton button : buttons) {
				button.setEnabled(true);
			}
			exitButton.setEnabled(true);
			
			if ( shopKeep != null ) {
				label.setText( shopKeep.getHello() );
			}
		}

		public void updateMenu(){

			ArrayList<Item> inv = shopKeep.getInventory();
			label.setText( shopKeep.getHello() );

			for(int x = 0; x < inv.size(); x++){
				buttons.get(x).setText(inv.get(x).getName() + " - " + inv.get(x).getValue() + "G");
				itemsDict.put(buttons.get(x), inv.get(x));

			}
		}
		
		public void setShopkeeper( ShopKeeper aShopKeep ) { this.shopKeep = aShopKeep; }

		//Closes the menu
		public void closeMenu() {
			
			aMapScreenPanel.togglePlayerDialog();
			paused = false;
			gamePanes.moveToBack(this);
			//Disable the buttons so they wont react when moused over
			for (JButton button : buttons){
				button.setEnabled(false);
			}
			exitButton.setEnabled(false);
		}



		public void sell(JButton button){

			Item itemOfInterest = itemsDict.get(button);
			
			//Check if the player can buy the item first
			if ( player.getMoney() > itemOfInterest.getValue() ) {
				
				shopKeep.sellToPlayer(itemOfInterest, itemOfInterest.getValue());
				player.buyItem(itemOfInterest);
				button.setText("SOLD!");
				button.setActionCommand("sold");
			} else {
				label.setText( shopKeep.notEnoughMoney() );
			}

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch ( e.getActionCommand() ) {

				case "resume":
					closeMenu();
					aMapScreenPanel.startManager();
					break;
				case "menu":
					paused = false; //Make sure paused is always false
					aStateMachine.change("mainmenu");
					break;
				case "sell":
					//hold on
					shopKeep = player.speakingWith();
					sell((JButton)e.getSource());
					break;
				case "sold":
					label.setText( shopKeep.soldOut() );
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + String.valueOf( e.getActionCommand()));
			}
		}
	}
	
	//Holds the menu screen
	class GameMenu extends JPanel implements ActionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int width = 200;
		private int height = 275;

		private int offSetY = 0;

		private int buttonWidth = 125;
		private int buttonHeight = 50000;

		private JButton resumeButton;
		private JButton menuButton;
		private JButton quitButton;
		private JButton cheatButton;

		public GameMenu() {

			paused = false;

			this.setBounds(0, 0, aFrame.getContentPane().getWidth(), aFrame.getContentPane().getHeight());
			this.setBackground( new Color(100, 100, 100, 100) );

			//Panel to hold all the buttons
			JPanel menuPanel = new JPanel();
			menuPanel.setBounds( aFrame.getContentPane().getWidth()/2 - width/2, aFrame.getContentPane().getHeight()/2 - height/2 + offSetY, width, height);
			menuPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder( 15, 15, 15, 15 )));

			menuPanel.setBackground( Color.WHITE );
			menuPanel.setLayout( new BoxLayout( menuPanel, BoxLayout.PAGE_AXIS ));
			this.setLayout( null );

			resumeButton = new JButton("Resume");
			resumeButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			resumeButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			resumeButton.addActionListener(this);
			resumeButton.setActionCommand("resume");
			resumeButton.setAlignmentX( Component.CENTER_ALIGNMENT );

			cheatButton = new JButton( "Cheat!");
			cheatButton.addActionListener(this);
			cheatButton.setActionCommand("cheat");
			cheatButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ));
			cheatButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ));
			cheatButton.setAlignmentX( Component.CENTER_ALIGNMENT );

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
			menuPanel.add( cheatButton, BorderLayout.CENTER );
			menuPanel.add( Box.createVerticalGlue() );
			menuPanel.add( menuButton, BorderLayout.CENTER );
			menuPanel.add( Box.createVerticalGlue() );
			menuPanel.add( quitButton, BorderLayout.CENTER );

			resumeButton.setEnabled(false);
			cheatButton.setEnabled(false);
			menuButton.setEnabled(false);
			quitButton.setEnabled(false);

			this.add(menuPanel);
		}

		//Will be used for saving and loading potentially later
		public void openMenu( JFrame aFrame, Player player, Map map ) {

			gamePanes.moveToFront(this);
			//Enable the buttons
			resumeButton.setEnabled(true);
			if ( finished == false ) {
				cheatButton.setEnabled( true );
			}
			menuButton.setEnabled(true);
			quitButton.setEnabled(true);
			paused = true;
			//System.out.println("here");

		}

		//Closes the menu
		public void closeMenu() {

			gamePanes.moveToBack(this);
			//Disable the buttons so they wont react when moused over
			resumeButton.setEnabled(false);
			cheatButton.setEnabled(false);
			menuButton.setEnabled(false);
			quitButton.setEnabled(false);
			paused = false;
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
			case "cheat":
				closeMenu();
				aMapScreenPanel.winButtonPressed();
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + String.valueOf( e.getActionCommand()));
			}
		}
	}

	//Holds Events
	class EventLogPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Border raisedBorder = BorderFactory.createBevelBorder( BevelBorder.RAISED, new Color(56,112,255) ,new Color(0, 51, 179));
		private Border loweredBorder= BorderFactory.createBevelBorder( BevelBorder.LOWERED,  new Color(56,112,255), new Color(0, 51, 179));
		private Border panelBorder = BorderFactory.createCompoundBorder( loweredBorder, 
				BorderFactory.createCompoundBorder( raisedBorder, loweredBorder));
		
		public EventLogPanel( JFrame aFrame ) {

			this.setBounds(MAP_SCREEN_PANEL_WIDTH, aFrame.getContentPane().getSize().height/2, 
					aFrame.getContentPane().getSize().width - MAP_SCREEN_PANEL_WIDTH, aFrame.getContentPane().getSize().height/2 );
			this.setBackground(Color.BLACK);
			this.setBorder( BorderFactory.createTitledBorder ( panelBorder, "Event Log", TitledBorder.DEFAULT_JUSTIFICATION, 
					TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 14), Color.WHITE ));
			
			JPanel aLogPanel = new JPanel() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

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
			};
			
			aLogPanel.setBackground( Color.WHITE );
			aLogPanel.setPreferredSize( new Dimension ( this.getWidth() - this.getInsets().bottom * 2, this.getHeight() - this.getInsets().top*2 ));
			this.add(aLogPanel);
		}
		
		@Override
		protected void paintComponent( Graphics g ) {
			
			super.paintComponent(g);
		}
	}

	//Holds the inventory
	class InventoryPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Border raisedBorder = BorderFactory.createBevelBorder( BevelBorder.RAISED, new Color(56,112,255) ,new Color(0, 51, 179));
		private Border loweredBorder= BorderFactory.createBevelBorder( BevelBorder.LOWERED,  new Color(56,112,255), new Color(0, 51, 179));
		private Border panelBorder = BorderFactory.createCompoundBorder( loweredBorder, 
				BorderFactory.createCompoundBorder( raisedBorder, loweredBorder));
		
		private ArrayList<Item> playerInventory; //A copy of the player inventory, it's bad practice but whatevs
		
		private InventoryGrid aInventoryGrid;
		
		public InventoryPanel( JFrame aFrame ) {
			this.setBounds(MAP_SCREEN_PANEL_WIDTH, 0, aFrame.getContentPane().getSize().width - MAP_SCREEN_PANEL_WIDTH, aFrame.getContentPane().getSize().height/2 );
			this.setBackground(Color.BLACK);
			this.setBorder( BorderFactory.createTitledBorder ( panelBorder, "Inventory", TitledBorder.DEFAULT_JUSTIFICATION, 
					TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 14), Color.WHITE ));
			
			playerInventory = aMapScreenPanel.getPlayerInventory();
			//System.out.println("Size:( " + ( aFrame.getContentPane().getSize().width - MAP_SCREEN_PANEL_WIDTH )+ "," + aFrame.getContentPane().getSize().height/2 + " )" );

			aInventoryGrid = new InventoryGrid( this.getWidth() - this.getInsets().bottom * 2, this.getHeight() - this.getInsets().top*2 ); 
			this.add( aInventoryGrid );
		}
		
		//Holds multiple itemPanels in a grid
		private class InventoryGrid extends JPanel {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private ArrayList<aItemPanel> itemPanels;
			
			public InventoryGrid( int parentWidth, int parentHeight ) {
				
				//this.setBounds( 0, 0, parentWidth, parentHeight );
				this.setBackground(Color.darkGray);
				this.setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ));
				this.setPreferredSize( new Dimension(parentWidth, parentHeight) );
			}
			
			public void update( ArrayList<Item> inventory ) {
				
				this.removeAll();
				for ( Item item: inventory ) {
					
					
					this.add( new aItemPanel( this.getWidth(), 50, item.getName() ));
				}
			}
			
			public void reset() {
				this.removeAll();
			}
			
		};
		
		//Panel to display individual items
		private class aItemPanel extends JPanel {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			//Constructor a panel of a certian width, height, and item
			public aItemPanel( int width, int height, String itemName ) {
				
				this.setBackground( Color.white );
				this.setLayout( new FlowLayout() );
				this.setPreferredSize( new Dimension( width + 10, height ));
				this.setMaximumSize( new Dimension( width + 10, height ));
				
				JLabel itemLabel = new JLabel( itemName, JLabel.CENTER );
				this.add( itemLabel );
			}
		}
		
		public void update() {
			
			//Check if the panel exists
			if ( aMapScreenPanel != null ) {
				
				//Did the player's inventory change?
				if ( aMapScreenPanel.getPlayerInventoryFlag() == true ) { //If so update the panel
					
					aInventoryGrid.update( playerInventory );
					/*
					String temp = "";
					
					for( Item item : playerInventory ) {
						
						temp = temp + item.getName() + " ";
					}
					System.out.println( temp );*/
					
					//Reset the player's flag
					aMapScreenPanel.setPlayerInventoryFlag( false );
				}
			}
			
		
		}
		
		//Used if the player has to restart the map
		public void setInventory() {
			if ( playerInventory != null ) {
				playerInventory = null;
				playerInventory = aMapScreenPanel.getPlayerInventory();
				aInventoryGrid.reset();
			}
		}

		@Override
		protected void paintComponent( Graphics g ) {

			super.paintComponent(g);
			//Graphics2D g2d = (Graphics2D) g;
			g.drawString("Inventory goes here", this.getWidth()/3, this.getHeight()/2 );
		}
		
		public void setInventory( ArrayList<Item> playerInventory ) { this.playerInventory = playerInventory; }

	}

	private void run( JFrame aFrame ) {

		this.aFrame = aFrame;
		//Make sure that the game is cleared
		aFrame.repaint();
		finished = false;

		JLayeredPane mapScreenPanes = new JLayeredPane();
		mapScreenPanes.setBounds(0, 0, aFrame.getContentPane().getSize().width , aFrame.getContentPane().getSize().height);

		//Initialize on screen panels
		aMapScreenPanel = new MapScreenPanel( aFrame );
		aGuiPanel = new GuiPanel();
		mapScreenPanes.add( aMapScreenPanel, 1 );
		mapScreenPanes.add( aGuiPanel, 0 );

		aEventLogPanel = new EventLogPanel( aFrame );
		aInventoryPanel = new InventoryPanel( aFrame );
		aWinPanel = new WinPanel();
		
		aLosePanel = new LosePanel();
		gameMenu = new GameMenu();

		//Used so we can make panels on top of other panels
		gamePanes = new JLayeredPane();
		gamePanes.setBounds(0, 0, aFrame.getContentPane().getSize().width , aFrame.getContentPane().getSize().height);

		//Panel to hold all the panels
		JPanel gamePanelsHolder = new JPanel( null );
		gamePanelsHolder.setBounds(0, 0, aFrame.getContentPane().getSize().width , aFrame.getContentPane().getSize().height);
		gamePanelsHolder.add( mapScreenPanes );
		gamePanelsHolder.add( aEventLogPanel );
		gamePanelsHolder.add( aInventoryPanel );

		gamePanes.add( gamePanelsHolder, 0 );
		gamePanes.add( gameMenu, 1 );
		gamePanes.add( aWinPanel, 2 );
		gamePanes.add( aLosePanel, 3);
		
		if ( aMapScreenPanel.getShopKeeper() != null ) {
			aShopKeepPanel = new ShopKeepPanel( aMapScreenPanel.getShopKeeper()) ;
			gamePanes.add(aShopKeepPanel, 4);
		}
		aFrame.add( gamePanes );

		//aFrame.setLayout( null );
		aFrame.revalidate();
		aFrame.repaint();

	}

	@Override
	public void update() {

		if ( aMapScreenPanel != null ) {
			aMapScreenPanel.update();

		}
		if( aGuiPanel != null ) {
			aGuiPanel.updateGui();
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
		aMapScreenPanel.stopManager();
		aFrame.getContentPane().removeAll();
	}
}
