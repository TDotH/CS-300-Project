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
	private LinkedList<String> log;//current and past dialogA
	private boolean pickedUp;
	private Item itemptr;
	private char icodeptr;

	public EventLog() {
		this.log = new LinkedList<String>();
		this.pickedUp = false;
	}

	private String buildS(Tile tile, int x, int y, Item item, char icode, String event) {
		if (log.size() > 20)
			log.removeFirst();
		y = 12 - y;
		StringBuilder s = new StringBuilder("Location: " + x + "," + y
				+ "\nTile: " + tile.getType().name() + "\n" +
				"Cost to move: " + tile.getType().getEnergyCost()
				+ "\n");
		if (icode == 'u') {//item used
			s.append("You used " + item.getName() + "!\n");
		} else {//item acquired
			s.append("You picked up a " + item.getName() +
					"!\n" + item.getDescription() + '\n');
		}
		s.append(event + "\n");
		return s.toString();
	}

	private String buildS(Tile tile, int x, int y, Item item, char icode) {
		if (log.size() > 20)
			log.removeFirst();
		y = 12 - y;
		StringBuilder s = new StringBuilder("Location: " + x + "," + y
				+ "\nTile: " + tile.getType().name() + "\n" +
				"Cost to move: " + tile.getType().getEnergyCost()
				+ "\n");

		if (icode == 'u') {//item used
			s.append("You used " + item.getName() + "!\n");
		} else if (icode == 'a'){//item acquired
			s.append("You picked up a " + item.getName() +
					"!\n" + item.getDescription() + '\n');
		}
		else{//item bought, but shouldn't be called here
			s.append("You bought a " + item.getName() + "!\n" + item.getDescription() + '\n');
		}
		return s.toString();
	}

	private String buildS(Tile tile, int x, int y, String event){
		if (log.size() > 20)
			log.removeFirst();
		y = 12 - y;
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
		y = 12 - y;
		StringBuilder s = new StringBuilder("Location: " + x + "," + y
				+ "\nTile: " + tile.getType().name() + "\n" +
				"Cost to move: " + tile.getType().getEnergyCost()
				+ "\n");
		return s.toString();
	}

	private String buildS(Item item, char icode){
		if (log.size() > 20)
			log.removeFirst();
		StringBuilder s = new StringBuilder();
		if (icode == 'u') {//item used
			s.append("You used " + item.getName() + "!\n");
		} else if (icode == 'a'){//item acquired
			s.append("You picked up a " + item.getName() +
					"!\n" + item.getDescription() + '\n');
		}
		else{
			s.append("You bought a " + item.getName() + "!\n" + item.getDescription() + '\n');
		}
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
		this.pickedUp = true;
		this.itemptr = item;
		this.icodeptr = icode;
		//String s = buildS(tile, p.getPosX(), p.getPosY(), item, icode, event);
		//log.addLast(s);
	}

	public void update(Tile tile, Player p) {
		String s;
		if(this.pickedUp == true){
			s = buildS(tile, p.getPosX(), p.getPosY(), this.itemptr, this.icodeptr);
			this.pickedUp = false;
		}
		else
			s = buildS(tile, p.getPosX(), p.getPosY());
		log.addLast(s);
	}

	public void update(Tile tile, Player p, Item item, char icode){
		this.pickedUp = true;
		this.itemptr = item;
		this.icodeptr = icode;
		//String s = buildS(tile, p.getPosX(), p.getPosY(), item, icode);
		//log.addLast(s);
	}

	public void update(Tile tile, Player p, String event){
		String s;
		if(this.pickedUp == true){
			//log.removeLast();
			s = buildS(tile, p.getPosX(), p.getPosY(), this.itemptr, this.icodeptr, event);
			this.pickedUp = false;
		}
		else
			s = buildS(tile, p.getPosX(), p.getPosY(), event);
		log.addLast(s);
	}

	public void update(Item item, char icode){
		String s = buildS(item, icode);
		log.addLast(s);

	}

	public void clear(){
		log.clear();
	}
}
