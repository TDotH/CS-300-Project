import java.awt.*;

import javax.swing.*;
//import javax.swing.JPanel;

//TODO: Create initialization method

public class Frupal extends JPanel {

	
	static final int SCREEN_WIDTH = 800;
	static final int SCREEN_HEIGHT = 600;

	//Map
	Map map = new Map("default_map");;

	public Frupal() throws Exception {
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
	}

	//main game loop
	public static void main( String[] args ) throws Exception {
		
		JFrame frame = new JFrame("Frupal vAlpha");
		Frupal frupal = new Frupal();
		frame.add(frupal);
		frame.setSize( SCREEN_WIDTH, SCREEN_HEIGHT );
		frame.setVisible( true );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		boolean gameOn = true;
		
		//Main game loop
		while( gameOn ) {
			//Do stuff
			frupal.repaint(); //Repaint canvas
			Thread.sleep(10); //Wait 10 ms
			
		}
	}
}
