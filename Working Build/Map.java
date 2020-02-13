package com.company;
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

//package fruPack;

import java.awt.Graphics2D;
import java.io.*;

public class Map {

    private Tile[][] map; //Container for the map/tiles
    private int width, height; //Width and height of the map

    //Constructor through a file
    public Map(String filename) throws Exception {
        File file = new File(filename); //Read in file
        BufferedReader br = new BufferedReader(new FileReader("src/com/company/default_map.txt")); //set up buffer reader

        String str; //Holds the data from file
        if ((str = br.readLine()) != null) { //Check for first line
            String[] size_of_map = str.split(","); //Split string

            //Get map size
            width = Integer.parseInt(size_of_map[0]);
            height = Integer.parseInt(size_of_map[1]);

            map = new Tile[width][height]; //Initialize map
        } else { br.close(); }

        int xc;
        //Iterate through file (The for loops may cause errors if the file doesn't match the given size)
        for (int y = 0; y < height; ++y) {
            str = br.readLine(); //Read in line
            for (int x = 0; x < width * 2; ++x) {
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
                Item item;

                ++x;
                switch (str.charAt(x)) { //assign from file index
                    case 'B':
                        item = new Tool(Items.BOAT);
                        break;
                    case 'C':
                        item = new Tool(Items.CHAINSAW);
                        break;
                    case 'E':
                        item = new Food(Items.POWERBAR);
                        break;
                    case 'F':
                        item = new Food(Items.FISH);
                        break;
                    case 'G':
                        item = new Food(Items.GOLD);
                        break;
                    case 'L':
                        item = new Tool(Items.BINOCULARS);
                        break;
                    case 'P':
                        item = new Tool(Items.JACKHAMMER);
                        break;
                    case 'R':
                        item = new Tool(Items.ROPE);
                        break;
                    case 'S':
                        item = new Tool(Items.WEEDWHACKER);
                        break;
                    case 'X':
                        item = new Tool(Items.DEFAULT);
                        break;

                    default: //okay intelliJ lol
                        throw new IllegalStateException("Unexpected value: " + str.charAt(x));
                }

                //Initialize tile
                xc = (x % 2 == 0) ? x/2 + 1 : x/2;
                map[xc][y] = new Tile(type, item);
            }
        }
    }

    //TODO: constructor for a user defined map (blank constructor)

    /* Takes in the player's coordinates and a given camera size and draws relative to the camera's position on the map
     * - Deals with what tiles to render and gives the position of rendering to each individual tile.draw()
     */
    public void draw(Graphics2D g, int line_width, int playerPosX, int playerPosY, Camera camera ) {
        /* Check if the player is near the bounds of the map
         * If so just render the same part of the map
         */

        //Used to render render coordinates relative to the camera
        int tempPosX, tempPosY;

        int i = 0;
        int j = 0;

        for (int x = camera.getCameraPosX() - camera.gettileMaxWidth()/2; x <= camera.getCameraPosX() + camera.gettileMaxWidth()/2; ++x) {
            if ( x >= 0 && x < width ) {
                for (int y = camera.getCameraPosY() - camera.gettileMaxHeight()/2; y <= camera.getCameraPosY() + camera.gettileMaxHeight()/2; ++y) {
                    if ( y >= 0 && y < height ) {
                        tempPosX = camera.getWindowPosX() + line_width * ( i );
                        tempPosY = camera.getWindowPosY() + line_width * ( j );
                        map[x][y].draw(g, line_width, tempPosX, tempPosY);
                        j++;
                    }
                }
                j = 0;
                i++;
            }
        }
    }

    //com.company.Tile getter
    public Tile get_tile(int x, int y) {
        //Check bounds
        if (x < 0 || x >= width
                || y < 0 || y >= height)
            return null;
        return map[x][y];
    }

    //com.company.Tile setter
    public void set_tile(Tile tile, int x, int y) {
        //check bounds
        if (x < 0 || x >= width
                || y < 0 || y >= height)
            return;
        map[x][y] = tile;
    }

    // Height and Width getters
    public int getHeight() { return height; }
    public int getWidth() { return width; }

}