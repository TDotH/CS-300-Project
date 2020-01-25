import fruPack.Movement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frupal extends JPanel {

	static final int SCREEN_WIDTH = 800;
	static final int SCREEN_HEIGHT = 600;
	private static Movement player;
	
	//main game loop
	public static void main( String[] args ) throws InterruptedException  {
		
		Frupal newGame = new Frupal();
		
		boolean gameOn = true;
		//Main game loop
		while( gameOn ) {
			//Do stuff
			
		}
	}
	
	public Frupal() {
		
		initGame();
		
	}
	
	//Updates game upon player input (key) release!
	private static void update() {
		System.out.println("Player coordinates: (" + player.getPosX() + "," + player.getPosY() + ")");
		
	}
		
	//Game initializers
	private void initGame() {
		
		JFrame frame = new JFrame("Frupal vAlpha");
		
		player = new Movement( 1, 1, SCREEN_WIDTH, SCREEN_HEIGHT, 0, 0);
		
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
