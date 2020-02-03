//package fruPack;

/*TODO
 * -Add energy and money
 * -Add inventory
 * -Add a way for the player to know what tile they've stepped on?
 */

import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Graphics2D;
import Tile.java;
import Types.java;

public class Player {

    //integers that hold the current position coordinates, as well as the map boundaries
    private int posX, posY, upBoundX, upBoundY;

    //Energy and money; unused until items are ready
    //private int energy, money;

    //Constructor: startX, startY, maxX, maxY, minX, minY
    public Player( int posX, int posY, int maxX, int maxY ) {

        setPos( posX, posY );
        setBounds( maxX - 1, maxY - 1);

    }

    //sets position coordinates to specified arguments
    public void setPos(int startX, int startY) {
        posX = startX;
        posY = startY;
    }

    //set map boundary coordinates
    public void setBounds(int maxX, int maxY) {
        upBoundX = maxX;
        upBoundY = maxY;
    }

    //Keyboard events from Frupal.java
    //returns an int greater than 0 (energy spent) if movement possible, 0 if obstacle is in the way,
    //-1 if shopkeeper tile is encountered, -2 if caverns (jewels, gameOver) are encountered
    //returns -3 if player tries to move out of bounds (no movement occurs)
    public int keyPressed(KeyEvent e) {
        Tile current_tile;
        int energyUsed;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if ((posX - 1) >= 0) {
                current_tile = get_tile(posX - 1, posY);
                energyUsed = determineEnergy(current_tile);
                if(energyUsed > 0)
                    --posX;
                else
                    return energyUsed;
            }
            else
                return -3;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if((posX + 1) <= upBoundX) {
                current_tile = get_tile(posX + 1, posY);
                energyUsed = determineEnergy(current_tile);
                if(energyused > 0)
                    ++posX;
                else
                    return energyUsed;
            }
            else
                return -3;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            if((posY + 1) <= upBoundY) {
                current_tile = get_tile(posX, posyY + 1);
                energyUsed = determineEnergy(current_tile);
                if(energyUsed > 0)
                    ++posY;
                else
                    return energyUsed;
            else
                return -3;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            if ((posY - 1 >= 0)) {
                current_tile = get_tile(posX, posY - 1);
                energyUsed = determineEnergy(current_tile);
                if(energyUsed > 0)
                    --posY;
                else
                    return energyUsed;
            else
                return -3;
        }
    }

    //Returns the amount of energy used to move
    public int determineEnergy(Tile current_tile) {
        switch (current_tile) {
            case 1:
                current_tile instanceof Types.FOREST;
                return 1;
            case 2:
                current_tile instanceof Types.SWAMP;
                return 2;
            case 3:
                current_tile instanceof Types.DESERT;
                return 2;
            case 4:
                current_tile instanceof Types.WATER;
                return 0;
            case 5:
                current_tile instanceof Types.MOUNTAINS;
                return 0;
            case 6:
                current_tile instanceof Types.SHOPKEEPER;
                return -1;
            case 7:
                current_tile instanceof Types.CAVERNS;
                return -2;
        }
    }


    //Draws the player according to the given tile size and relative to the camera; offsets movement by tile size
    public void draw( Graphics2D g, int tile_size, Camera camera ) {

        int playerSz = (tile_size/2);
        int offSetX = (tile_size/4);
        int offSetY = (tile_size/4);

        //Default render positions (at camera origin)
        int tempPosX = camera.getWindowPosX() + offSetX;
        int tempPosY = camera.getWindowPosY() + offSetY;

        g.setColor(Color.RED);

        //Set the player's position relative to the camera
        tempPosX = camera.getWindowPosX() + tile_size * ( posX - camera.getCameraPosX() + camera.gettileMaxWidth()/2 ) + offSetX;
        tempPosY = camera.getWindowPosY() + tile_size * ( posY - camera.getCameraPosY() + camera.gettileMaxHeight()/2 ) + offSetY;

        g.fillRect( tempPosX, tempPosY, playerSz, playerSz );

    }

    public int getPosX() {

        return posX;
    }

    public int getPosY() {

        return posY;
    }
}
