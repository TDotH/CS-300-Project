package com.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.map.Map;
import com.player.Player;
import com.statemachine.*;

public class GameMenu extends JPanel {

	private StateMachine aStateMachine;
	
	public GameMenu ( StateMachine aStateMachine ) {
		this.aStateMachine = aStateMachine;
	}
	
	public void openMenu( JFrame aFrame, Player player, Map map ) {
		
		//TransparentPanel backPanel = new TransparentPanel();
		JPanel backPanel = new JPanel();
		backPanel.setBounds(0, 0, aFrame.getContentPane().getWidth(), aFrame.getContentPane().getHeight());
		backPanel.setBackground( new Color(100, 100, 100, 100) );
		
		aFrame.add(backPanel);
		System.out.println("here");
		
	}
	
	@Override
	protected void paintComponent( Graphics g ) {
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
		
	}
	
}
