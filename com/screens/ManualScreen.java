package com.screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ManualScreen extends JFrame {

	/**
	 * 
	 */
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 430;
	private static final long serialVersionUID = 1L;
	
	public ManualScreen() {
		
		//Window Initializations
		this.setSize( SCREEN_WIDTH, SCREEN_HEIGHT );
		//aFrame.setVisible( true );
		this.setResizable( false );
		this.setFocusable(true);
		this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		//aFrame.setExtendedState( JFrame.MAXIMIZED_BOTH );
		//aFrame.setUndecorated(true);
		this.getContentPane().setPreferredSize(new Dimension(600,430));
		this.setTitle("Frupal Manual");
		this.pack();
		this.setVisible(true);
		
		//Screen dimensions to set game window location
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation( (int)screenSize.getWidth()/4, (int)screenSize.getHeight()/6 );
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.add( new MenuPanel() );
		scrollPane.setViewportView( new MenuPanel() );
		scrollPane.setPreferredSize( new Dimension( this.getContentPane().getWidth(), this.getContentPane().getHeight()) );
		scrollPane.setBounds( 0, 0, this.getContentPane().getWidth(), this.getContentPane().getHeight());
		scrollPane.getHorizontalScrollBar().setUnitIncrement( 12 );
		scrollPane.getVerticalScrollBar().setUnitIncrement( 12 );
		
		this.add(scrollPane);
		this.repaint();
	}

	class MenuPanel extends JPanel {
		
		Image manual = null;
		
		public MenuPanel() {
			this.setPreferredSize( new Dimension( SCREEN_WIDTH , 716));

			
			//this.setBackground( Color.BLACK );
			
		  	//Load the manual
	    	try {
	    		manual = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resources/images/manual.png"));
	    	}
	    	catch (IOException ex) {
	    		System.out.println("Error! manual can't be loaded!");
	    	}
		}
		
		@Override
		protected void paintComponent( Graphics g ) {

			if ( manual != null) {
				
				getParent().revalidate();

				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
								RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.drawImage(manual, 0, 0, null);
				
			}
		
		}
	}
}
