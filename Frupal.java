/* Author: Tyde Hashimoto
 * Date: 1/25/20
 */

//import fruPack.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;


public class Frupal extends JPanel {

	//Screen size, may change later
	static final int SCREEN_WIDTH = 800;
	static final int SCREEN_HEIGHT = 600;
	
	//Width for tiles
	static final int TILE_SIZE = 50;
		
	private static Player player;
	private static Frupal newGame;
	private static JFrame frame;
	private static Camera camera;
	
	Map map = new Map("src/maps/default_map");
	
	//main game loop
	public static void main( String[] args ) throws Exception  {
		
		newGame = new Frupal();
		
		frame.add(newGame);
		
		boolean gameOn = true;
		//Main game loop
		while( gameOn ) {
			//Do stuff
			
		}
	}
	
	public Frupal() throws Exception {
		
		initGame();
		
	}
	
	//Paint override
	@Override
	public void paint(Graphics g) {		
		//Set up graphics library
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
		map.draw(g2d, TILE_SIZE, player.getPosX(), player.getPosY(), camera);
		player.draw(g2d, TILE_SIZE, camera);
	}
	
	//Updates game upon player input (key) release!
	private static void update()  {
		System.out.println("Player coordinates: (" + player.getPosX() + "," + player.getPosY() + ")");
		System.out.println("Camera coordinates: (" + camera.getCameraPosX() + "," + camera.getCameraPosY()  + ")");
		//Repaint canvas
		camera.update(player.getPosX(), player.getPosY());
		newGame.repaint();
	}
		
	//Game initializers
	private void initGame() {
		
		// x,y origin for player and camera
		int startX = 4;
		int startY = 0;
		
		//Viewport "Center" for the camera to render at
		int cameraPosX = 0;
		int cameraPosY = 0;
		
		//Viewport dimensions for the camera
		int viewPortSzX = 400;
		int viewPortSzY = 600;
		
		frame = new JFrame("Frupal vAlpha");
		player = new Player( startX, startY, map.getWidth(), map.getHeight() );
		camera = new Camera( viewPortSzX, viewPortSzY, TILE_SIZE, cameraPosX, cameraPosY, startX, startY, map.getWidth(), map.getHeight() );
		
		frame.setSize( SCREEN_WIDTH, SCREEN_HEIGHT );
		frame.setVisible( true );
		frame.setResizable( false );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		//Screen dimensions to set game window location
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation( (int)screenSize.getWidth()/3, (int)screenSize.getHeight()/4 ); //Ratio of the game screen is 4:3
		
		keyInput aListener = new keyInput();
        frame.addKeyListener( aListener );
        frame.setFocusable(true);
        
	}
	
	//Used for keyboard input
    private static class keyInput implements KeyListener  {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
            player.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
        	update();
            //System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
        }
    }
		
}
