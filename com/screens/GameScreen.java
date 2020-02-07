/* Author: Tyde Hashimoto
 * The main game screen with map, event log, and inventory
 * 
 */

package com.screens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

import com.statemachine.*;
import com.map.Map;
import com.player.*;

public class GameScreen implements IState {

	private StateMachine aStateMachine;
	private MapScreenPanel aMapPanel;
	private EventLogPanel aEventLogPanel;
	private InventoryPanel aInventoryPanel;
	private KeyboardFocusManager manager;
	
	public JFrame aFrame;
	
	private boolean redraw = false;
	
	private static final int MAP_SCREEN_PANEL_WIDTH = 624;
	private static final int MAP_SCREEN_PANEL_HEIGHT = 624;
	
	public GameScreen ( StateMachine aStateMachine ) {
		
		this.aStateMachine = aStateMachine;
	}
	
	//Holds the map and player
	class MapScreenPanel extends JPanel{
		
		private Map map;
		private Player player;
		private Camera camera;
		
		KeyDispatcher aKeyDispatcher = new KeyDispatcher();
		
		public MapScreenPanel( JFrame aFrame ) {
			this.setBounds(0, 0, MAP_SCREEN_PANEL_WIDTH, aFrame.getContentPane().getSize().height );
		
			map = new Map();
			try {
				map.loadMap("src/maps/aFile.map");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			player = new Player( map.getStartX(), map.getStartY(), map.getWidth(), map.getHeight() );
			camera = new Camera( this.getWidth(), this.getHeight(), map.getTileSize(), map.getStartX(), map.getStartY(), map.getWidth(), map.getHeight() );
			
			this.setBackground( Color.DARK_GRAY );
			manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			manager.addKeyEventDispatcher( aKeyDispatcher );

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
	            		manager.removeKeyEventDispatcher(aKeyDispatcher);
	            		aStateMachine.change("mainmenu");
	            	}
	    			player.keyPressed(e);
	    			redraw = true;

	            } 
	            else if (e.getID() == KeyEvent.KEY_TYPED) {

	            }
	            return false;
	        }
	    }
		
	}
	
	class EventLogPanel extends JPanel {
		
		public EventLogPanel( JFrame aFrame ) {
			
			this.setBounds(MAP_SCREEN_PANEL_WIDTH, aFrame.getContentPane().getSize().height/2, aFrame.getContentPane().getSize().width - MAP_SCREEN_PANEL_WIDTH, aFrame.getContentPane().getSize().height/2 );
			this.setBackground(Color.yellow);
		}
		
		@Override
		protected void paintComponent( Graphics g ) {
			
			super.paintComponent(g);
			//Graphics2D g2d = (Graphics2D) g;
			g.drawString("Event log goes here", this.getWidth()/3, this.getHeight()/2 );
			
		}
		
	}
	
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
		
		aFrame.add( aMapPanel );
		aFrame.add( aEventLogPanel );
		aFrame.add( aInventoryPanel );
		
		aFrame.setLayout( null );
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
		//Only redraw if we need to
		if ( redraw == true ) {
			aFrame.repaint();
			redraw = false;
		}
		

	}

	@Override
	public void onEnter(JFrame aFrame) {
		//run this state
		run(aFrame);
		
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
	}

}
