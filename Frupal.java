/* Author: Tyde Hashimoto
 * Date: 1/25/20
 */

import com.screens.*;
import com.statemachine.*;

import java.awt.*;

import javax.swing.*;

public class Frupal extends JPanel {

	//Screen size
	static final int SCREEN_WIDTH = 960;
	static final int SCREEN_HEIGHT = 720;
	
	//Current version 
	static final String version = "1.0"; 
	
	public static void main( String[] args ) throws Exception  {
		
		new Frupal().run();
	}
	
	//Main game runs here
	public void run() throws Exception {
		
		//Main window frame used in the game
		JFrame mainFrame = new JFrame("Frupal v" + version);
		
		//Keeps track of current game states
		StateMachine gGameMode = new StateMachine( mainFrame );
		
		this.initGame( mainFrame, gGameMode );
		
        //Set the starting state
        gGameMode.change("mainmenu");
		
		//Game flag
		boolean gameOn = true;
		
		//Used so for later animations
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
		
		//Main game loop
		while ( gameOn ) {

			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double)OPTIMAL_TIME);
			
			gGameMode.update();
			gGameMode.render();
			Thread.sleep( (lastLoopTime - System.nanoTime() + OPTIMAL_TIME)/1000000 );
		}
	}
		
	//Takes in a frame and initializes game
	private void initGame( JFrame aFrame, StateMachine gGameMode ) {
		
		//Window Initializations
		aFrame.setSize( SCREEN_WIDTH, SCREEN_HEIGHT );
		//aFrame.setVisible( true );
		aFrame.setResizable( false );
        aFrame.setFocusable(true);
		aFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		//aFrame.setExtendedState( JFrame.MAXIMIZED_BOTH );
		//aFrame.setUndecorated(true);
		aFrame.getContentPane().setPreferredSize(new Dimension(960,624));
		aFrame.pack();
		aFrame.setVisible(true);
		
		//Screen dimensions to set game window location
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		aFrame.setLocation( (int)screenSize.getWidth()/4, (int)screenSize.getHeight()/6 );
        
		/*
        for( GameStates states : GameStates.values() ) {
        	gGameMode.add( states.getStateName(), states.getGameState());
        }*/
		
		gGameMode.add("mainmenu", new MainMenu( gGameMode ));
		gGameMode.add("mapeditor", new MapEditor( gGameMode ));
        gGameMode.add("gamescreen", new GameScreen( gGameMode ));
        gGameMode.add("initscreen", new InitScreen( gGameMode ));

	}	
}
