package com.actors;

import javax.swing.*;
import com.inventory.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ShopKeeper implements Objects {

    //ShopKeep Inventory
    private ArrayList<Item> inventory = new ArrayList<Item>();

    //Max Inventory size
    //Didn't wanna do this but I'm sick of trying think of a better way. I've already invested a good five minutes.
    int FINAL_INVENTORY_SIZE = 6;

    String hello   = "Trinkets and goods for those who can afford them...";
    String poor    = "Come back when you have more cash";  
    String soldOut = "I'm all out of that..";

    //Starting money 100. Can change to whatever.
    private int money = 100;

    private int posX, posY;

    Items item = Items.SHOPKEEP;

    public ShopKeeper(int posX, int posY){
        this.posX = posX;
        this.posY = posY;

        //setRandomInv(FINAL_INVENTORY_SIZE);
    }

    /*
    public ShopKeeper(int posX, int posY, int invSize){
        this.posX = posX;
        this.posY = posY;

        setRandomInv(invSize);
    }*/

    public String getHello(){
        return hello;
    }
    
    public String notEnoughMoney() { return poor; }
    public String soldOut() { return soldOut; }


    public int getMoney(){
        return money;
    }

    public void sellToPlayer(Item goods, int income){
        inventory.remove(goods);
        money += income;
    }

    //public void buyFromPlayer()

    public ArrayList<Item> getInventory(){
        return this.inventory;
    }
    
    public void setPos( int x, int y ) {
    	posX = x;
    	posY = y;
    }
    
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    
    public void clearInventory() {
    	
    	inventory.clear();
    }

    private void setRandomInv(int inventorySize){
        int MAX_SIZE = inventorySize;
        int possibleItems = (Items.values()).length-2;
        int maxValue = 100;
        Random random = new Random();

        ArrayList<Item> tempInventory = new ArrayList<Item>();

        for (int x = 0; x < MAX_SIZE; x++){
            Item thisItem = new Item(Items.itemByID(random.nextInt(possibleItems)));
            int maxCount = thisItem.getInventoryLimit();
            int curCount = 0;

            for (int y = 0; y < tempInventory.size(); y++){
                if (tempInventory.get(y).getName() == thisItem.getName()){
                    curCount++;
                }
            }

            if (curCount < maxCount && thisItem.getValue() < maxValue){
                tempInventory.add(thisItem);
            } else {
                x--;
            }
        }

        this.inventory = tempInventory;
    }
    
    public void addItem( Item aItem ) {
    	inventory.add( aItem );
    }
    
    //Regular draw without images
    public void draw( Graphics2D g2d, int tile_size, int centerPosX, int centerPosY ) {

    	int itemSz = (tile_size/2);
    	int offSetX = (tile_size/4);
    	int offSetY = (tile_size/4);

    	g2d.setColor(new Color(127, 0, 255,255));

    	g2d.fillRect( centerPosX + offSetX, centerPosY + offSetY, itemSz, itemSz );

    }

    //Draw with images
    public void draw( Graphics2D g2d, int tile_size, int centerPosX, int centerPosY, Image itemset ) {

    	int itemSz = (tile_size/2);
    	int offSet = (tile_size/16);

    	int imageSz = 16;

    	if ( itemset != null ) {
        	g2d.drawImage(itemset, centerPosX + offSet , centerPosY + offSet , centerPosX + tile_size - offSet , centerPosY + tile_size - offSet,
        			item.getImagePosX() * imageSz, item.getImagePosY() * imageSz, imageSz + item.getImagePosX() * imageSz, imageSz + item.getImagePosY() * imageSz, null);
    	} else {
    		g2d.setColor(new Color(127, 0, 255,255));
    		g2d.fillRect( centerPosX + offSet, centerPosY + offSet, itemSz, itemSz );
    	}
    }
}

