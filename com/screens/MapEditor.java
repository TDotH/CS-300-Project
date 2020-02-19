/*Author: Tyde Hashimoto
 * 
 * A map editor that allows for easier map making
 * TODO: Add items and obstacles
 * 
 */

package com.screens;
import com.inventory.*;
import com.map.*;
import com.player.Player;
import com.statemachine.*;

import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import javax.swing.*;

import java.util.EnumSet;
import static java.util.EnumSet.complementOf;

public class MapEditor extends JFrame implements IState {
	
	//UI constraints
	static final int GUI_POSX = 0;
	static final int GUI_POSY = 0;
	static final int GUI_WIDTH = 200;
	
	//Map editor constraints
	static final int MAP_EDITOR_POSX = 200;
	static final int MAP_EDITOR_POSY = 0;
	
	//Slider constraints
	static final int MAX_MAP_SIZE = 15;
	
	//Tile size constraints
	static final int TILE_SIZE = 48;
	
	private StateMachine aStateMachine;
	private KeyboardFocusManager manager;
	
	static JFrame frame;
	MapPanel mapPanel;
	KeyDispatcher aKeyDispatcher = new KeyDispatcher();
	
	//Current tile type to draw
	Types currType = Types.FOREST;
	Items currItem;
	
	//Used to check if the player has saved recently
	private boolean recentSave;
	
	//Used to figure out what is currently being drawn
	enum Brushes {
		TILES,
		OBJECTS
	}
	
	//Set so Java doesn't scream at you
	Brushes aBrush = Brushes.TILES;
	
	public MapEditor( StateMachine aStateMachine ) {
		this.aStateMachine = aStateMachine;
	}
	
	//Panel that holds all other gui Panels
	class TileGuiPanel extends JPanel  {
		
		public TileGuiPanel() {
			
			this.setBounds( GUI_POSX, GUI_POSY, GUI_WIDTH, frame.getContentPane().getHeight() );
			this.setBackground(Color.lightGray);
			this.setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ));
			this.setPreferredSize( new Dimension( 200, 700 ));
			this.setMinimumSize( new Dimension( 200, 500 ));
			this.setMaximumSize(new Dimension( 200, 500 ));
		}
		
	}
	
	//Panel that deals with map drawing
	class MapPanel extends JPanel implements MouseListener, MouseMotionListener{
		
		Map map;
		Player aPlayer; //Needed for every map
		Jewel aJewel; //Needed for every map
		int mapWidth;
		int mapHeight;
		
		private int mapEditorWidth;
		private int mapEditorHeight; 
		
		private boolean mapGenerated = false;
		private boolean drawing = false;
		
		//Used so that the player can only place on of each
		private boolean playerSpawn = false;
		private boolean jewelSpawn = false;
		
		public MapPanel() {
			
			aPlayer = null;
			aJewel = null;
			mapEditorWidth = frame.getContentPane().getWidth() - GUI_WIDTH;
			mapEditorHeight = frame.getContentPane().getHeight();
			this.setBounds(MAP_EDITOR_POSX, MAP_EDITOR_POSY, mapEditorWidth, mapEditorHeight);
			this.setVisible(true);
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		
		//Creates a map of a given size and height
		public void generateMap( int width, int height ) {
			
			if ( recentSaveChecker() == true ) {
				final Types type = Types.FOREST; //Default tile setting, used to make sure that the player can always move no matter the map generated
				this.mapWidth = width;
				this.mapHeight = height;
				map = new Map( width, height, type );
				
				//Reset jewel and player just in case the player loads a map the generates a new one
				if ( aPlayer != null ) {
					aPlayer = null;
					playerSpawn = false;
				}
				
				if ( aJewel != null ) {
					aJewel = null;
					jewelSpawn = false;
				}
				
				mapGenerated = true;
			}
		}
		
		//Loads a map with the given filename
		public void loadMap( String fileName ) throws Exception {
			
			map = new Map();
			map.loadMap(fileName);
			this.mapWidth = map.getWidth();
			this.mapHeight = map.getHeight();
			
			//Set player position 
			if ( aPlayer == null ) {
				aPlayer = new Player();
				playerSpawn = true;
			}
			aPlayer.setPos( map.getStartX(), map.getStartY());
			
			//Set jewel position
			if ( aJewel == null ) {
				aJewel = new Jewel( map.getJewelX(), map.getJewelY() );
				jewelSpawn = true;
			} else { //In case there is already a jewel
				aJewel.setPosX( map.getJewelX() );
				aJewel.setPosY( map.getJewelY() );
			}
			
			mapGenerated = true;
		}
		
		//Saves the current map with the given filename
		public void saveMap( String fileName ) throws Exception {
			
			map.saveMap( fileName );
		}
		
		@Override
		protected void paintComponent( Graphics g ) {
			
			if ( mapGenerated == true ) {
				
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
								RenderingHints.VALUE_ANTIALIAS_ON);
				map.draw(g2d, (mapEditorWidth - ( mapWidth*TILE_SIZE ))/2, (mapEditorHeight - ( mapHeight*TILE_SIZE ))/2);
				
				//If there is a player spawn
				if ( playerSpawn == true ) {
					aPlayer.draw(g2d, TILE_SIZE, (mapEditorWidth - ( mapWidth*TILE_SIZE ))/2, (mapEditorHeight - ( mapHeight*TILE_SIZE ))/2);
				}
			}
		}
		
		//Getters
		public boolean getMapFlag() { return mapGenerated; }
		public boolean getPlayerSpawnFlag() { return playerSpawn; }
		public boolean getJewelSpawnFlag() { return jewelSpawn; }

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mousePressed(MouseEvent e) {

			//Check if a map is generated
			if ( mapGenerated == true ) {
				
				//Check if clicked within map bounds
				if ( e.getX() > (mapEditorWidth - ( mapWidth*TILE_SIZE ))/2 && e.getX() < (mapEditorWidth + ( mapWidth*TILE_SIZE ))/2)  {
					
					if ( e.getY() > (mapEditorHeight - ( mapHeight*TILE_SIZE ))/2 && e.getY() < (mapEditorHeight + ( mapHeight*TILE_SIZE ))/2) {
						
						//Get Mouse position on the map
						int tempX = ( e.getX() - ( mapEditorWidth - mapWidth*TILE_SIZE )/2 ) / TILE_SIZE;
						int tempY = ( e.getY() - ( mapEditorHeight - mapHeight*TILE_SIZE )/2 ) /TILE_SIZE;
						
						recentSave = false; 
						
						switch( aBrush ) {
						
							case TILES: 
								//Used for drag drawing
								drawing = true;
								map.set_tile_at( currType, tempX, tempY );
				
								break;
							case OBJECTS:
								
								//Check if this object is being place on an impassable tile
								if ( map.get_tile( tempX, tempY ).getType().getPassable() == true ) {
									
									//Brush types
									switch( currItem ) {
									
										case PLAYER:
											
												//Check if there is something on the tile already
												checkTile( map.get_tile( tempX, tempY ), tempX, tempY );
												//Set player spawn flag
												playerSpawn = true;
												
												//Set spawn on map
												map.setStartX( tempX );
												map.setStartY( tempY );
												
												//Create a player and set his coordinates
												if ( aPlayer == null ) {
													aPlayer = new Player();
												}
												aPlayer.setPos( tempX, tempY );
												
											break;
											
										case JEWEL:
											
											//Check if there is something on the tile already
											checkTile( map.get_tile( tempX, tempY ), tempX, tempY );
											
											//Check if there is a jewel placed already
											if ( aJewel != null ) {
												
												map.get_tile( aJewel.getPosX() , aJewel.getPosY() ).setObject( null ); //Set the jewel's current location on the map to null
												//Set the jewel's new position
												aJewel.setPosX( tempX ); 
												aJewel.setPosY( tempY );
												map.get_tile(tempX, tempY).setObject( aJewel ); // Place the jewel's new location on the map
												
											} else { // Nope
												
												jewelSpawn = true; //Set the spawn flag
												aJewel = new Jewel( tempX, tempY ); //Make a new jewel to keep track of coordinates
												map.get_tile(tempX, tempY).setObject( aJewel ); // Place the jewel on the map
												
											}				
											
											break;
											
										default:
									}
								} else { //Throw an error about placing the player here
									JOptionPane.showMessageDialog(frame, "Can't place the " + currItem.getName() + " on an impassable tile!", "Placement Warning", JOptionPane.WARNING_MESSAGE);
								}
								break;
								
							default:
								throw new IllegalStateException("Unexpected value: " + String.valueOf(aBrush));
						}
					}
				}
			}
		}
		
		/* Checks the tile to ensure that objects don't overlap on the same tile
		 * If there is an object already there, then deletes tile
		 * Also resets playerSpawn and JewelSpawn flags if they happen to get deleted
		 */
		private void checkTile( Tile aTile, int tempX, int tempY ) {
		
			if ( aPlayer != null ) {
			//Check if this is the player's position
				if ( ( ( aPlayer.getPosX() == tempX ) && ( aPlayer.getPosY() == tempY ) ) ) {
					
					//Reset the flag
					playerSpawn = false;
					//Delete the player
					aPlayer = null;
				}
			} 
			if ( aTile.getObject() != null ) { //Is there an object on the tile?
				
				//Does this object happen to be the jewel?
				if ( aTile.getObject() instanceof Jewel ) {
					
					jewelSpawn = false; //Reset the jewel flag
					aJewel = null; // Delete the jewel
				}
				
				aTile.setObject( null ); // Delete the object
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			//Reset drawing flag
			drawing = false;
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseDragged( MouseEvent e ) {
			
			if ( drawing == true ) {
				
				//Draw at mouse position
				if ( e.getX() > (mapEditorWidth - ( mapWidth*TILE_SIZE ))/2 && e.getX() < (mapEditorWidth + ( mapWidth*TILE_SIZE ))/2)  {
					
					if ( e.getY() > (mapEditorHeight - ( mapHeight*TILE_SIZE ))/2 && e.getY() < (mapEditorHeight + ( mapHeight*TILE_SIZE ))/2) {
						
						//Mouse position on the map
						int tempX = ( e.getX() - ( mapEditorWidth - mapWidth*TILE_SIZE )/2 ) / TILE_SIZE;
						int tempY = ( e.getY() - ( mapEditorHeight - mapHeight*TILE_SIZE )/2 ) / TILE_SIZE;
						
						map.set_tile_at( currType, tempX, tempY );
					}
				}
				
			}
		}
	}
	
	//Deals with map generation
	class MapGenPanel extends JPanel implements ActionListener {
		
		//Slider used to set map size (from 0x0 to MAP_MAX_SIZExMAP_MAX_SIZE)
		JSlider mapSizeSlider;
		
		public MapGenPanel() {
			
			//Slider creation to set map size
			JLabel mapSizeLabel = new JLabel( "Map Size", JLabel.CENTER );
			mapSizeLabel.setAlignmentX( Component.CENTER_ALIGNMENT );
			mapSizeSlider = new JSlider( JSlider.HORIZONTAL, 1, MAX_MAP_SIZE, 5);
			mapSizeSlider.setMajorTickSpacing(7);
			mapSizeSlider.setMinorTickSpacing(1);
			mapSizeSlider.setPaintTicks(true);
			mapSizeSlider.setPaintLabels(true);
			
			//Button creation
			JButton genMapButton = new JButton("Generate Map");
			genMapButton.setActionCommand("generate");
			genMapButton.addActionListener(this);
			genMapButton.setAlignmentX( Component.CENTER_ALIGNMENT );
			
			//Panel setup
			this.setLayout(new BoxLayout( this, BoxLayout.PAGE_AXIS ) );
			this.add( mapSizeLabel );
			this.add( mapSizeSlider );
			this.add( genMapButton );
			this.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(5, 5, 10, 10 )));
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			//If generate button was pressed tell the map panel to generate a map of a size from the slider
			if ( "generate".equals( e.getActionCommand() ) ) {
				mapPanel.generateMap( mapSizeSlider.getValue(), mapSizeSlider.getValue() );

			}	
		}
	}
	
	//Deals with picking what the player will draw; is a tabbed pane to switch between tiles, items, and obstacles
	class DrawPickerPanel extends JTabbedPane implements ActionListener {
		
		public DrawPickerPanel( DescriptionPanel descriptionPanel ) {
			
			//Generate buttons for each tile type
			JPanel tilesPanel = new JPanel( new GridLayout( 3,3 ));
			for ( Types type : complementOf( EnumSet.of( Types.DEFAULT )) ) { //Don't include the default tile
				
				JButton b = new JButton( String.valueOf(type) );
				b.setToolTipText( type.getName() );
				b.setPreferredSize( new Dimension( 40, 40 ));
				b.addActionListener( new ActionListener() {
					public void actionPerformed( ActionEvent e ) {
						
						//Clear the description box
						descriptionPanel.clearText();
						
						//Name
						String tempStr = String.valueOf( "A " + type.getName() + " tile" );
						descriptionPanel.addText( tempStr );
						
						//Passable?
						tempStr = "Passable: ";
						if ( type.getPassable() == true ) {
							
							tempStr = tempStr.concat( "Yes" );
						} else {
							tempStr = tempStr.concat( "No" );
						}
						descriptionPanel.addText( tempStr );
						
						//If it's passable then tell how much energy will be used
						if ( type.getPassable()  == true ) {
							tempStr = String.valueOf( "Energy Cost: " + String.valueOf( type.getEnergyCost() ) );
							descriptionPanel.addText( tempStr );
						}
						
						currType = type;
						aBrush = Brushes.TILES;
					}
				});
				tilesPanel.add(b);
			}
			
			//Generate a button to place one-off items (just the player for now
			JPanel objectsPanel = new JPanel( new GridLayout( 3, 3 ));
			for ( Items item : complementOf( EnumSet.of( Items.DEFAULT )) ) { //Don't include the default item
				
				JButton b = new JButton( String.valueOf( item ) );
				b.setToolTipText( item.getName() );
				b.setPreferredSize( new Dimension( 40, 40 ));
				b.addActionListener( new ActionListener() {
					public void actionPerformed( ActionEvent e ) {
						
						//Clear the description box
						descriptionPanel.clearText();
						
						//Name of the item
						String tempStr = item.getName();
						descriptionPanel.addText( tempStr );
						
						//Flavor text
						tempStr = item.getDescription();
						descriptionPanel.addText( tempStr );
						
						currItem = item;
						aBrush = Brushes.OBJECTS;
					}
				});
				objectsPanel.add(b);
			}
			
			this.addTab("Tiles", tilesPanel );
			this.addTab("Objects", objectsPanel);
			this.setPreferredSize( new Dimension( 200, 200 ));
			this.setMaximumSize( new Dimension( 200, 200 ));
			this.setMinimumSize(new Dimension( 200, 200 ));
			this.setBorder( BorderFactory.createEmptyBorder( 10, 5, 10, 5 ));
				
			}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	//Deals with the text display
	class DescriptionPanel extends JPanel {
		
		private JTextArea textArea;
		
		public DescriptionPanel() {
			
			this.setPreferredSize( new Dimension( 200, 150 ));
			this.setMaximumSize( new Dimension( 200, 200 ));
			this.setMinimumSize(new Dimension( 200, 200 ));
			this.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(10, 10, 10, 10 )));
			this.setLayout( new BorderLayout() );
			
			textArea = new JTextArea( 5, 5 );
			textArea.setLineWrap( true );
			textArea.setWrapStyleWord( true );
			textArea.setPreferredSize( new Dimension( 100, 300 ) );
			textArea.setMaximumSize( new Dimension( 100, 300 ) );
			textArea.setMinimumSize(new Dimension( 100, 300 )  );
			textArea.setEditable( false );
			textArea.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(5, 5, 10, 10 )));
			
			this.add( textArea, BorderLayout.CENTER );
		}
		
		public void clearText() {
			textArea.setText( null );
		}
		
		public void addText( String text ) {
			
			textArea.append( text + "\n");
		}
	}
	
	//Deals with file saving/load and exiting the map editor
	class FileManagerPanel extends JPanel implements ActionListener {
		
		public FileManagerPanel() {
			
			//Saving and loading buttons
			this.setLayout( ( new FlowLayout( FlowLayout.CENTER, 5, 10 )));
			JButton saveButton = new JButton("Save Map");
			saveButton.setActionCommand("save");
			saveButton.addActionListener(this);
			
			JButton loadButton = new JButton("Load Map");
			loadButton.setActionCommand("load");
			loadButton.addActionListener(this);
			
			JButton exitButton = new JButton("Exit Editor");
			exitButton.setActionCommand("exit");
			exitButton.addActionListener(this);
			
			this.add(saveButton);
			this.add(loadButton);
			this.add(exitButton);
			this.setPreferredSize( new Dimension( 200, 100 ));
			this.setMaximumSize( new Dimension( 200, 100 ));
			this.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(15, 0, 0, 0 )));
		}

		//Used by Generate Map, Save, Load, and Exit buttons
		@Override
		public void actionPerformed(ActionEvent e) {
			switch ( e.getActionCommand() ) {
			
				//Save button was pressed
				case "save":
					//Is there a map to save?
					if ( mapPanel.getMapFlag() == false ) {
						JOptionPane.showMessageDialog(frame, "No current map to be saved!", "Save Warning", JOptionPane.WARNING_MESSAGE);
						
					} else if ( mapPanel.getPlayerSpawnFlag() == false ) { //Is the player spawn set?
						JOptionPane.showMessageDialog(frame, "No point placed for the player to spawn at!", "Save Warning", JOptionPane.WARNING_MESSAGE);
						
					} else if ( mapPanel.getJewelSpawnFlag() == false ) { //Is there a jewel on the map?
						JOptionPane.showMessageDialog(frame, "No jewel placed for the player to pick up!", "Save Warning", JOptionPane.WARNING_MESSAGE);
						
					} else { //Looks like you're good to save!
						//Create a file chooser and set filters
						JFileChooser fc = new JFileChooser("src/maps");
						fc.setDialogTitle("Save map");
						fc.addChoosableFileFilter(  new MapFilter() );
						fc.setSelectedFile( new File("aFile.map"));
						fc.setFileFilter( new MapFilter() );
						int returnVal = fc.showSaveDialog( frame );
						
						//If the player picks a file to save
						if ( returnVal == JFileChooser.APPROVE_OPTION ) {
							
							File file = fc.getSelectedFile();
							try {
								File aFile = new File( file.getName() );
								mapPanel.saveMap( file.getPath() );
								recentSave = true;
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					
					break;
				//Load button was pressed	
				case "load":
					
					if ( recentSaveChecker() == true ) {
						//Create a file chooser and set filters
						JFileChooser fc = new JFileChooser("src/maps");
						fc.setDialogTitle("Save map");
						fc.addChoosableFileFilter(  new MapFilter() );
						fc.setFileFilter( new MapFilter() );
						int returnVal = fc.showOpenDialog( frame );
						
						if ( returnVal == JFileChooser.APPROVE_OPTION ) {
							File file = fc.getSelectedFile();
							try {
								mapPanel.loadMap( file.getPath() );
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					break;
					
				//Exit button was pressed
				case "exit":
					if ( recentSaveChecker() == true ) {
						aStateMachine.change("mainmenu");
					}
					break;
					
				default:
					throw new IllegalStateException("Unexpected value: " + String.valueOf( e.getActionCommand()));
			}
		}
	}
	
	//Checks if the user has saved recently, if not, open a dialog asking if they want to continue
	private boolean recentSaveChecker() {
		
		if ( recentSave == true ) {
			return true;
		} else {
			int choice = JOptionPane.showConfirmDialog(frame, "Unsaved changes will be lost! Are you sure you wish to continue?", "Save warning", JOptionPane.YES_NO_OPTION);
			if ( choice == JOptionPane.YES_OPTION ) {
				return true;
			} else {
				return false;
			} 
		}
	}
	
	//Sets up all ui components
	private void run( JFrame aFrame ) {
		
		//Make sure this states components are painted
		frame = aFrame;
		frame.repaint();
		
		//frame.setName("Frupal Map Editor");		
		
		//Contains the map
		mapPanel = new MapPanel();
		
		//Main gui panel, look above for individual panel purposes
		TileGuiPanel tileGuiPanel = new TileGuiPanel();
		MapGenPanel mapGenPanel = new MapGenPanel();
		DescriptionPanel descriptionPanel = new DescriptionPanel();
		DrawPickerPanel drawPickerPanel = new DrawPickerPanel( descriptionPanel );
		FileManagerPanel fileManagerPanel = new FileManagerPanel();
		
		
		//Add all the panels together
		tileGuiPanel.add( mapGenPanel );
		tileGuiPanel.setBorder( BorderFactory.createEmptyBorder( 10, 0, 10, 0 ));
		tileGuiPanel.add(drawPickerPanel, BorderLayout.CENTER);
		tileGuiPanel.add( descriptionPanel, BorderLayout.CENTER );
		tileGuiPanel.add( Box.createVerticalGlue() ); //Utilizes extra space to put this at the bottom of the ui
		tileGuiPanel.add( fileManagerPanel, BorderLayout.PAGE_END );

		//Add everything to the window
		frame.add( tileGuiPanel );
		frame.add( mapPanel );
		frame.setLayout( null );
		frame.setVisible( true );
		
		//Create the keymanager
		manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher( aKeyDispatcher );
		
		
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

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		frame.repaint();
	}

	@Override
	public void onEnter( JFrame aFrame ) {
		
		recentSave = true; // Player just entered this screen so there isn't anything to save
		this.run( aFrame );
		
	}

	
	@Override
	public void onExit() {
		manager.removeKeyEventDispatcher(aKeyDispatcher);
		frame.getContentPane().removeAll(); //Clear the frame
	}
	
    private class KeyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {

            	
            } 
            else if (e.getID() == KeyEvent.KEY_RELEASED) {
            	if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
            		manager.removeKeyEventDispatcher( aKeyDispatcher );
            		aStateMachine.change("mainmenu");
            	}

            } 
            else if (e.getID() == KeyEvent.KEY_TYPED) {

            }
            return false;
        }
    }
}

