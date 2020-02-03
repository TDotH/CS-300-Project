/*Author: Tyde Hashimoto
 * 
 * A map editor that allows for easier map making
 * TODO: Add items and obstacles
 * 
 */

package fruPack;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import javax.swing.*;

import fruPack.Types;

public class Map_Editor extends JFrame implements  ActionListener {
	
	//Screen constraints
	static final int SCREEN_WIDTH = 1000;
	static final int SCREEN_HEIGHT = 800;
	
	//UI constraints
	static final int GUI_POSX = 0;
	static final int GUI_POSY = 0;
	static final int GUI_WIDTH = 200;
	static final int GUI_HEIGHT = SCREEN_HEIGHT;
	
	//Map editor constraints
	static final int MAP_EDITOR_POSX = 200;
	static final int MAP_EDITOR_POSY = 0;
	static final int MAP_EDITOR_WIDTH = SCREEN_WIDTH - GUI_WIDTH;
	static final int MAP_EDITOR_HEIGHT = SCREEN_HEIGHT;
	
	//Slider constraints
	static final int MAX_MAP_SIZE = 15;
	
	//Tile size constraints
	static final int TILE_SIZE = 50;
	
	static JFrame frame;
	MapPanel mapPanel;
	JSlider mapSizeSlider;
	
	//Current type to draw
	Types currType = Types.DEFAULT;
	
	//Panel that deals with map generation
	class MapPanel extends JPanel implements MouseListener, MouseMotionListener{
		
		Map map;
		int mapWidth;
		int mapHeight;
		
		private boolean mapGenerated = false;
		private boolean drawing = false;
		
		public MapPanel() {
			
			this.setBounds(MAP_EDITOR_POSX, MAP_EDITOR_POSY, MAP_EDITOR_WIDTH, MAP_EDITOR_HEIGHT);
			this.setVisible(true);
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		
		//Creates a map of a given size and height
		public void generateMap( int width, int height ) {
			
			this.mapWidth = width;
			this.mapHeight = height;
			map = new Map( width, height );
			mapGenerated = true;
		}
		
		//Loads a map with the given filename
		public void loadMap( String fileName ) throws Exception {
			
			map = new Map();
			map.loadMap(fileName);
			this.mapWidth = map.getWidth();
			this.mapHeight = map.getHeight();
			mapGenerated = true;
			frame.repaint();
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
				map.draw(g2d, TILE_SIZE, (MAP_EDITOR_WIDTH - ( mapWidth*TILE_SIZE ))/2, (MAP_EDITOR_HEIGHT - ( mapHeight*TILE_SIZE))/2);
			}
		}
		
		//Getter
		public boolean getMapFlag() { return mapGenerated; }

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}


		
		@Override
		public void mousePressed(MouseEvent e) {

			//Check if a map is generated
			if ( mapGenerated == true ) {
				
				//Used for drag drawing
				drawing = true;
				//Check if clicked within map bounds
				if ( e.getX() > (MAP_EDITOR_WIDTH - ( mapWidth*TILE_SIZE ))/2 && e.getX() < (MAP_EDITOR_WIDTH + ( mapWidth*TILE_SIZE ))/2)  {
					
					if ( e.getY() > (MAP_EDITOR_HEIGHT - ( mapHeight*TILE_SIZE ))/2 && e.getY() < (MAP_EDITOR_HEIGHT + ( mapHeight*TILE_SIZE ))/2) {
						
						//Mouse position on the map
						int tempX = ( e.getX() - ( MAP_EDITOR_WIDTH - mapWidth*TILE_SIZE )/2 ) / 50;
						int tempY = ( e.getY() - ( MAP_EDITOR_HEIGHT - mapHeight*TILE_SIZE )/2 ) / 50;
						//System.out.println("Mouse Coordinates: (" + tempX + ", " + tempY + ")" );
						
						map.set_tile_at( currType, tempX, tempY);
						frame.repaint();
					}
				}
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
				if ( e.getX() > (MAP_EDITOR_WIDTH - ( mapWidth*TILE_SIZE ))/2 && e.getX() < (MAP_EDITOR_WIDTH + ( mapWidth*TILE_SIZE ))/2)  {
					
					if ( e.getY() > (MAP_EDITOR_HEIGHT - ( mapHeight*TILE_SIZE ))/2 && e.getY() < (MAP_EDITOR_HEIGHT + ( mapHeight*TILE_SIZE ))/2) {
						
						//Mouse position on the map
						int tempX = ( e.getX() - ( MAP_EDITOR_WIDTH - mapWidth*TILE_SIZE )/2 ) / 50;
						int tempY = ( e.getY() - ( MAP_EDITOR_HEIGHT - mapHeight*TILE_SIZE )/2 ) / 50;
						
						map.set_tile_at( currType, tempX, tempY);
						frame.repaint();
					}
				}
				
			}
		}
	}
	
	class TileGuiPanel extends JPanel  {
		
		public TileGuiPanel() {
			
			this.setBounds( GUI_POSX, GUI_POSY, GUI_WIDTH, GUI_HEIGHT );
			this.setBackground(Color.lightGray);
		}
		
	}
	
	//Sets up all ui components
	public Map_Editor() {
		
		//Frame initialization
		frame = new JFrame("Frupal Map Editor");
		frame.setSize( SCREEN_WIDTH, SCREEN_HEIGHT );
		frame.setResizable( false );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation( (int)screenSize.getWidth()/4, (int)screenSize.getHeight()/5 );
		
		//Contains the map
		mapPanel = new MapPanel();
		
		//Main gui panel 
		TileGuiPanel tileGuiPanel = new TileGuiPanel();
		tileGuiPanel.setLayout( new BoxLayout( tileGuiPanel, BoxLayout.PAGE_AXIS ));
		tileGuiPanel.setPreferredSize( new Dimension( 200, 800 ));
		tileGuiPanel.setMinimumSize( new Dimension( 200, 800 ));
		
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
		
		//Throw the map generation into a panel
		JPanel mapGenPanel = new JPanel();
		mapGenPanel.setLayout(new BoxLayout( mapGenPanel, BoxLayout.PAGE_AXIS ) );
		mapGenPanel.add( mapSizeLabel );
		mapGenPanel.add( mapSizeSlider );
		mapGenPanel.add( genMapButton );
		mapGenPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(5, 5, 10, 10 )));

		//Generate buttons for each tile type
		JPanel tilesPanel = new JPanel( new FlowLayout( FlowLayout.CENTER, 5, 5 ));
		for ( Types type : Types.values() ) {
			
			JButton b = new JButton( String.valueOf(type) );
			b.setPreferredSize( new Dimension( 50, 40 ));
			b.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					
					currType = type;
				}
			});
			tilesPanel.add(b);
			
		}
		
		//A tabbed pane to switch between tiles, items, and obstacles
		JTabbedPane drawPane = new JTabbedPane();
		drawPane.addTab("Tiles", tilesPanel );
		drawPane.setPreferredSize( new Dimension( 200, 200 ));
		drawPane.setMaximumSize( new Dimension( 200, 200 ));
		drawPane.setMinimumSize(new Dimension( 200, 200 ));
		
		//Saving and loading buttons
		JPanel fileManagerPanel = new JPanel( new FlowLayout( FlowLayout.CENTER, 5, 0) );
		JButton saveButton = new JButton("Save Map");
		saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
		
		JButton loadButton = new JButton("Load Map");
		loadButton.setActionCommand("load");
		loadButton.addActionListener(this);
		
		fileManagerPanel.add(saveButton);
		fileManagerPanel.add(loadButton);
		fileManagerPanel.setAlignmentY( Component.BOTTOM_ALIGNMENT );
		fileManagerPanel.setPreferredSize( new Dimension( 200, 60 ));
		fileManagerPanel.setMaximumSize( new Dimension( 200, 60 ));
		fileManagerPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(10, 0, 10, 0 )));
		
		//Add all the panels together
		tileGuiPanel.add( mapGenPanel );
		tileGuiPanel.setBorder( BorderFactory.createEmptyBorder( 10, 0, 10, 0 ));
		drawPane.setBorder( BorderFactory.createEmptyBorder( 10, 5, 10, 5 ));

		tileGuiPanel.add(drawPane, BorderLayout.CENTER);
		tileGuiPanel.add( Box.createVerticalGlue() ); //Utilizes extra space to put this at the bottom of the ui
		tileGuiPanel.add( fileManagerPanel, BorderLayout.SOUTH );

		//Add everything to the window
		frame.add( tileGuiPanel );
		frame.add( mapPanel );
		frame.setLayout( null );

		frame.setVisible( true );
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
	public void actionPerformed(ActionEvent e) {
		
		//Generate map was pressed
		if ("generate".equals(e.getActionCommand())) {
			//System.out.println("Button!");
			mapPanel.generateMap( mapSizeSlider.getValue(), mapSizeSlider.getValue() );
			frame.repaint();
			frame.revalidate();
		}
		
		//Save was pressed
		if ( "save".equals(e.getActionCommand()) ) {
			
			//Is there a map to save?
			if ( mapPanel.getMapFlag() == true ) {
				//Create a file chooser and set filters
				JFileChooser fc = new JFileChooser("src/maps");
				fc.setDialogTitle("Save map");
				fc.addChoosableFileFilter(  new MapFilter() );
				fc.setSelectedFile( new File("aFile.map"));
				fc.setFileFilter( new MapFilter() );
				int returnVal = fc.showSaveDialog( this );
				
				//If the player picks a file to save
				if ( returnVal == JFileChooser.APPROVE_OPTION ) {
					
					File file = fc.getSelectedFile();
					try {
						File aFile = new File( file.getName() );
						mapPanel.saveMap( file.getPath() );
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			} else { //Show warning if no map is generated
				
				JOptionPane.showMessageDialog(frame, "No current map to be saved!", "Save Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if ( "load".equals(e.getActionCommand())) {
			//Create a file chooser and set filters
			JFileChooser fc = new JFileChooser("src/maps");
			fc.setDialogTitle("Save map");
			fc.addChoosableFileFilter(  new MapFilter() );
			fc.setFileFilter( new MapFilter() );
			int returnVal = fc.showOpenDialog( this );
			
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
	}
}

