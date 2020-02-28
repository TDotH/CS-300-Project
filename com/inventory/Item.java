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
        Copies the item that is passed in as an argument.

        It uses RTTI in the form of getClass() to determine
        what the values of the next reference and each of the objects in
        the array will be.
    */
    Item(Item copy)
    {
        item = copy.item;
        quantity = copy.quantity;
    }


    /*
       This method is abstract, and is implemented by the derived classes.
    */
    //abstract void method();



    /*
        These methods are empty, but are declared to be overriden by the derived classes.
    */
    public void copyItem(Food copy)
    {
    }



    public void copyItem(Tool copy)
    {
    }


    public void setItem(Items item)
    {
        this.item = item;
    }


    public void copy(Item get)
    {
        item.description = get.item.description;
        quantity = get.quantity;
        item.name = get.item.name;
    }



    public boolean compareID(int check)
    {
        return item.itemID == check;
    }



    public boolean compareID(Item check)
    {
        return item.itemID == check.item.itemID;
    }



    public int compareEquality(String check)
    {
        if (item.name != null)
        {
            return item.name.compareToIgnoreCase(check);
        }
        else
        {
            return -1;
        }
    }

    public int compareEquality(Item check)
    {
        if (item.name != null)
        {
            return item.name.compareToIgnoreCase(check.item.name);
        }
        else
        {
            return -1;
        }
    }


    /*
        Compares object's name variable to passed in string,
        ignoring the upper and lower case differences.
    */
    public boolean compareName(String check)
    {
        if (item.name != null)
        {
            if (item.name.compareToIgnoreCase(check) >= 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean compareName(Item check)
    {
        if (item.name != null)
        {
            if (item.name.compareToIgnoreCase(check.item.name) >= 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public void displayName()
    {
        System.out.print(item.name);
    }

    public void displayDescription()
    {
        System.out.print(item.description);
    }

    /*
        Sets the data members to null.
    */
    public void nullify()
    {
        item.name = null;
        item.description = null;
        quantity = 0;
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

    /*
        Asks the user for information regarding what kind of venue they want to create.
        Checks the string they typed and matches it with a venue type. Then it creates
        a new venue of that type.
    */
    /*
    public Item setItem() throws Exception {
        int temp = 0;
        String temp2;
        Item itemT = null;
        boolean success, quit = false;
        input = new Scanner(System.in);

        do {
            success = true;
            System.out.println("What kind of item do you want to add? (type number)");
            for (Items dir : Items.values()) {
                System.out.println(dir.itemID + " " + dir.name);
            }
            if (input.hasNextInt())
            {
                temp = input.nextInt();
            }
            else
            {
               input.next();
            }
            switch(temp)
            {
                case 1:
                    itemT = new Tool(Items.BINOCULARS);
                    break;
                case 2:
                    itemT = new Food(Items.POWERBAR);
                    break;
                case 3:
                    itemT = new Tool(Items.WEEDWHACKER);
                    break;
                case 4:
                    itemT = new Tool(Items.JACKHAMMER);
                    break;
                case 5:
                    itemT = new Tool(Items.CHAINSAW);
                    break;
                case 6:
                    itemT = new Tool(Items.BOAT);
                    break;
                case 7:
                    itemT = new Food(Items.JEWEL);
                    break;
                case 8:
                    itemT = new Food(Items.FISH);
                    break;
                case 9:
                    itemT = new Food(Items.GOLD);
                    break;
                case 10:
                    itemT = new Tool(Items.ROPE);
                    break;
                default:
                    success = false;
            }
            if (!success)
            {
                System.out.println("That was not a valid choice. Please try again.");
            }
        } while (!success);
        return itemT;
    }*/



    /*
        Function to apply a venue object to the
        festival object argument.

        It uses user input to set the venue, and then
        applies the venue to the festival.
    */
    /*
    public int applyItem(Inventory dest) throws Exception {
        Item temp;
        int success;

        if (!dest.checkStatus())
        {
            success = -1;
        }
        else
        {
            temp = setItem();
            if (temp != null)
            {
                success = dest.addItem(temp);
            }
            else
            {
                success = 0;
            }
        }
        return success;
    }*/

/*

    public boolean display()
    {
        boolean success = true;

        if (item != null)
        {
            System.out.println("Item " + item.name + "'s description is " + item.description + ".");
            System.out.print("There ");
            if (quantity == 1)
                System.out.print("is ");
            else
            {
                System.out.print("are ");
            }
            System.out.println(quantity + " of them.");
        }
        else
        {
            success = false;
        }
        return success;
    }*/




    public String getName()
    {
        return item.name;
    }


    public String getNameInit()
    {
        if (item != null)
            return Character.toString(item.name.charAt(0));
        else
        {
            return "";
        }
    }


    public boolean isEmpty()
    {
        return item == null;
    }


    // Protected Methods
    protected void incrementQuantity()
    {
        ++quantity;
    }

    //Regular draw without images
    public void draw( Graphics2D g2d, int tile_size, int centerPosX, int centerPosY ) {

    	int itemSz = (tile_size/2);
    	int offSetX = (tile_size/4);
    	int offSetY = (tile_size/4);

    	g2d.setColor(new Color(127, 0, 255,255));

    	//Set the player's position
    	//int tempPosX = centerPosX + tile_size * ( this.posX ) + offSetX;
    	//int tempPosY = centerPosY + tile_size * ( this.posY ) + offSetY;

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


    	//Set the item's
    	//int tempPosX = centerPosX + tile_size * ( this.posX ) + offSetX;
    	//int tempPosY = centerPosY + tile_size * ( this.posY ) + offSetY;

    	//g.fillRect( centerPosX + offSetX, centerPosY + offSetY, itemSz, itemSz );

    }
}
