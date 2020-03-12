package com.inventory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

/*
   Author: Mustafa Radheyyan
   Course: CS300
   Date: 1/18/2020
   File: Implementation File
   Proj: This file contains the Item class.
*/

import java.util.Scanner;

public class Item implements Objects
{
    private Items item;
    private int posX, posY; // Item position
    protected Scanner input = null;
    // Private Fields //
    private int quantity;

    // Public Methods
    Item()
    {
        item = null;
        quantity = 0;
    }

    public void setPosX(int posX)
    {
        this.posX = posX;
    }


    public void setPosY(int posY)
    {
        this.posY = posY;
    }


    public int getPosX()
    {
        return posX;
    }


    public int getPosY()
    {
        return posY;
    }
    public Item(String type)
    {
        item = null;
        quantity = 0;
    }

    public Item(Items type)
    {
        item = type;
    }

    //Constructor that takes the item type and the item position (on the map)
    public Item( Items type, int posX, int posY ) {

    	item = type;
    	this.posX = posX;
    	this.posY = posY;
    }

  
    /*
        This method checks the type of the current object, and returns a value associated with each type.
    */
    public int checkType()
    {
        return item.itemID;
    }

    public Items getItem() { return item; }

    public void copyPos(Item item)
    {
        posX = item.posX;
        posY = item.posY;
    }


    public String getDescription() { return item.description; }
    public String getName()
    {
        return item.name;
    }

    // Protected Methods
    protected void incrementQuantity()
    {
        ++quantity;
    }

    public int getValue() { return item.value; }
    public int getInventoryLimit() { return item.inventoryLimit; }
    
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
        			item.imagePosX * imageSz, item.imagePosY * imageSz, imageSz + item.imagePosX * imageSz, imageSz + item.imagePosY * imageSz, null);
    	} else {
    		g2d.setColor(new Color(127, 0, 255,255));
    		g2d.fillRect( centerPosX + offSet, centerPosY + offSet, itemSz, itemSz );
    	}
    }
}
