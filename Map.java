/* Author: Forrest Daggett

    Date: 1/25/20
--------------------------------------
    Map Class for the frupal game

    Map is initialized either through a file, or manually as a 2-dimensional array of Tile objects.

    When reading through the map file, let's use this legend:
    First line is [width],[height]
    F = FOREST
    S = SWAMP
    D = DESERT
    W = WATER
    M = MOUNTAINS
    K = SHOPKEEPER
    C = CAVERNS - Where the jewels are held (?)

    Map is constructed so that the 0,0 coordinates are in the top left of the grid
    and increase as you go right/down

    TODO: Allow user to change the coordinates of where this will be drawn on the canvas
 */

package fruPack;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.*;

public class Map {

    private Tile[][] map; //Container for the map/tiles
    private int width, height; //Width and height of the map
    private int line_width;


    //Constructor through a file
    public Map(String filename, int line_width) throws Exception {
        File file = new File(filename); //Read in file
        this.line_width = line_width; //Set tile width
        BufferedReader br = new BufferedReader(new FileReader(file)); //set up buffer reader

        String str; //Holds the data from file
        if ((str = br.readLine()) != null) { //Check for first line
            String[] size_of_map = str.split(","); //Split string

            //Get map size
            width = Integer.parseInt(size_of_map[0]);
            height = Integer.parseInt(size_of_map[1]);

            map = new Tile[width][height]; //Initialize map
        } else return;

        //Iterate through file (The for loops may cause errors if the file doesn't match the given size)
        for (int y = 0; y < height; ++y) {
            str = br.readLine(); //Read in line
            for (int x = 0; x < width; ++x) {
                Types type; // For initializing individual tiles

                switch (str.charAt(x)) { //assign from file index
                    case 'F':
                        type = Types.FOREST;
                        break;
                    case 'S':
                        type = Types.SWAMP;
                        break;
                    case 'D':
                        type = Types.DESERT;
                        break;
                    case 'W':
                        type = Types.WATER;
                        break;
                    case 'M':
                        type = Types.MOUNTAINS;
                        break;
                    case 'K':
                        type = Types.SHOPKEEPER;
                        break;
                    case 'C':
                        type = Types.CAVERNS;
                        break;

                    default: //okay intelliJ lol
                        throw new IllegalStateException("Unexpected value: " + str.charAt(x));
                }

                //Initialize tile
                map[x][y] = new Tile(type,x,y, line_width);
            }
        }
    }

    //TODO: constructor for a user defined map (blank constructor)

    //Draws the map as it is
    //TODO: give coordinates to draw so we can see the map from the players perspective
    public void draw(Graphics2D g) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                map[x][y].draw(g);
            }
        }
    }

    //Tile getter
    public Tile get_tile(int x, int y) {
        //Check bounds
        if (x < 0 || x >= width
                || y < 0 || y >= height)
            return null;
        return map[x][y];
    }

    //Tile setter
    public void set_tile(Tile tile, int x, int y) {
        //check bounds
        if (x < 0 || x >= width
                || y < 0 || y >= height)
            return;
        map[x][y] = tile;
    }


}
