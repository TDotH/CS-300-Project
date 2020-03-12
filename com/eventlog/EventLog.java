/*
 * Author: Logan Voruz
 *
 * EventLog Class for Frupal Game
 *
 * EventLog is initialized manually and is initially an empty windoow.
 * To display data, it takes in a tile (accessed from the map with Player Coordinates)
 * and displays the tile information to the user.
 */
package com.eventlog;
import java.util.*;
import com.map.*;
import com.player.*;
import com.inventory.*;
import com.screens.*;

public class EventLog {
	private LinkedList<String> log;//current and past dialog

	public EventLog() {
		this.log = new LinkedList<String>();
	}

	private String buildS(Tile tile, int x, int y, Item item, char icode, String event) {
		if (log.size() > 20)
			log.removeFirst();
		StringBuilder s = new StringBuilder("Location: " + x + "," + y
				+ "\nTile: " + tile.getType().name() + "\n" +
				"Cost to move: " + tile.getType().getEnergyCost()
				+ "\n");
		if (icode == 'u') {//item used
			s.append("You used " + item.getName() + "!\n");
		} else {//item acquired
			s.append("You picked up a " + item.getName() +
					"!\n" + item.getDescription());
		}
		s.append(event + "\n");
		return s.toString();
	}

	private String buildS(Tile tile, int x, int y, Item item, char icode) {
		if (log.size() > 20)
			log.removeFirst();
		StringBuilder s = new StringBuilder("Location: " + x + "," + y
				+ "\nTile: " + tile.getType().name() + "\n" +
				"Cost to move: " + tile.getType().getEnergyCost()
				+ "\n");

		if (icode == 'u') {//item used
			s.append("You used " + item.getName() + "!\n");
		} else {//item acquired
			s.append("You picked up a " + item.getName() +
					"!\n" + item.getDescription());
		}
			return s.toString();
	}

	private String buildS(Tile tile, int x, int y, String event){
		if (log.size() > 20)
			log.removeFirst();
		StringBuilder s = new StringBuilder("Location: " + x + "," + y
				+ "\nTile: " + tile.getType().name() + "\n" +
				"Cost to move: " + tile.getType().getEnergyCost()
				+ "\n");
		s.append(event + "\n");
		return s.toString();

	}

	private String buildS(Tile tile, int x, int y){
		if (log.size() > 20)
			log.removeFirst();
		StringBuilder s = new StringBuilder("Location: " + x + "," + y
				+ "\nTile: " + tile.getType().name() + "\n" +
				"Cost to move: " + tile.getType().getEnergyCost()
				+ "\n");
		return s.toString();
	}

	public String display() {
		if(log.size() == 0)
			return "Empty Log";
		Iterator<String> iterator = log.descendingIterator();
		StringBuilder s = new StringBuilder();
		while(iterator.hasNext()){
			s.append(iterator.next());

		}
		return s.toString();
	}


	public void update(Tile tile, Player p, Item item, char icode, String event){
		String s = buildS(tile, p.getPosX(), p.getPosY(), item, icode, event);
		String last = log.peekLast();
		if(last.compareTo(s) == 0)
			return;
		log.addLast(s);
	}

	public void update(Tile tile, Player p) {
		String s = buildS(tile, p.getPosX(), p.getPosY());
		log.addLast(s);
	}

	public void update(Tile tile, Player p, Item item, char icode){
		String s = buildS(tile, p.getPosX(), p.getPosY(), item, icode);
		log.addLast(s);
	}

	public void update(Tile tile, Player p, String event){
		String s = buildS(tile, p.getPosX(), p.getPosY(), event);
		log.addLast(s);
	}
}
