/* Author: Tyde Hashimoto
 * Date: 1/25/20
 */

//import fruPack.Player;
//import fruPack.Map;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Frupal extends JPanel {

	//Screen size, may change later
	static final int SCREEN_WIDTH = 800;
	static final int SCREEN_HEIGHT = 600;
	
	static final int TILE_SIZE = 100;
	
	//Max size for a (MAXSIZE, MAXSIZE) map
	static final int MAXSIZE = 5;
	
	private static Player player;
	private static Frupal newGame;
	
	private static JFrame frame;
	
	Map map = new Map("src/maps/default_map", TILE_SIZE);
	
	//main game loop
	public static void main( String[] args ) throws Exception  {
		
		newGame = new Frupal();
		
		frame.add(newGame);
		
		boolean gameOn = true;
		//Main game loop
		while( gameOn ) {
			//Do stuff
			frupal.repaint(); //Repaint canvas
			Thread.sleep(10); //Wait 10 ms
			
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
		map.draw(g2d);
		player.draw(g2d, TILE_SIZE);
	}
	
	//Updates game upon player input (key) release!
	private static void update() {
		//System.out.println("Player coordinates: (" + player.getPosX() + "," + player.getPosY() + ")");
		 //Repaint canvas
		newGame.repaint();
	}
		
	//Game initializers
	private void initGame() {
		
		frame = new JFrame("Frupal vAlpha");
		player = new Player( 1, 1, MAXSIZE, MAXSIZE);
		
		frame.setSize( SCREEN_WIDTH, SCREEN_HEIGHT );
		frame.setVisible( true );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		keyInput aListener = new keyInput();
        frame.addKeyListener( aListener );
        frame.setFocusable(true);
        
	}
	
	//Used for keyboard input
    private static class keyInput implements KeyListener {
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
