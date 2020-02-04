/*
 * Author: Logan Voruz
 *
 * EventLog Class for Frupal Game
 *
 * EventLog is initialized manually and is initially an empty windoow.
 * To display data, it takes in a tile (accessed from the map with Player Coordinates)
 * and displays the tile information to the user.
 */

import java.util.*;
import java.awt.*;

public class EventLog{
	private List<String> log;//current and past dialog

	EventLog(){
		log = new LinkedList<String>();
	}

	//all there really is for now is tile type, 
	private void buildS(Tile tile, int x, int y){
		if(log.size() > 20)
			log.removeLast();
		StringBuilder s0 = new StringBuilder("Location: ");
		s0.append(x);
		s0.append(",");
		s0.append(y);
		s0.append("\n");
		String s = null;
		switch (tile.type){
			case FOREST:
				s = new String("Tile: Forest\n");
				break;
			case SWAMP:
				s = new String("Tile: Swamp\n");
				break;
			case DESERT:
				s = new String("Tile: Desert\n");
				break;
			case WATER:
				s = new String("Tile: Water\n");
				break;
			case MOUNTAINS:
				s = new String("Tile: Mountains\n");
				break;
			case SHOPKEEPER:
				s = new String("Tile: Shopkeeper\n");
				break;
			case CAVERNS:
				s = new String("Tile: Caverns\n");
				break;
			default://illegal
				throw new IllegalStateException("Unexpected value");
		}
		if(s){
			s0.append(s);
			log.add(s0.toString());
		}
		return;
	}

	private void displayCurrent(){//displays last string added to log, the current one
		String s = log.getLast();
		//display it
	}

	public void displayHistory(){//displays log history up to 20 moves ago
		for(int i = 0; i < log.size(); i++){
			//display log item to window
		}
	}
	//call when player moves
	public int updateMovement(Tile tile, Player player){
		return 0;
	}
	//call when player uses an item
	public int updateItem(Item item){
		return 0;
	}
	//custom log update
	public int updateStory(String story){
		return 0;
	}
}
