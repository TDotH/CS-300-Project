package com.company;

/*
   Author: Mustafa Radheyyan
   Course: CS300
   Date: 1/18/2020
   File: Implementation File
   Proj: This file contains the com.company.Item class.
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public abstract class Item
{
    protected Scanner input = null;
    // Private Fields //
    // The name of the venue.
    private String name;
    // The name of the venue.
    private String description;
    private int quantity;
  //  private Tile[][] item; //Container for the map/tiles
    private int width, height; //Width and height of the map

    /*
    public Item(String filename) throws Exception {
        File file = new File(filename); //Read in file
        BufferedReader br = new BufferedReader(new FileReader("src/default_map.txt")); //set up buffer reader

        String str; //Holds the data from file
        if ((str = br.readLine()) != null) { //Check for first line
            String[] size_of_map = str.split(","); //Split string

            //Get map size
            width = Integer.parseInt(size_of_map[0]);
            height = Integer.parseInt(size_of_map[1]);

            item = new Tile[width][height]; //Initialize map
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
                item[x][y] = new Tile(type);
            }
        }
    }


     */
    // Public Methods
    Item()
    {
        name = null;
        description = null;
        quantity = 0;
    }


    public Item(String type)
    {
        name = type;
        description = null;
        quantity = 0;
    }
/*
    com.company.Item(String name)
    {
        input = new Scanner(System.in);
        System.out.println("What do you want to name the " + name + " item?");

        this.name = input.nextLine();

        System.out.println("What do you want to name the description to be?");
        this.description = input.nextLine();

        quantity = 0;
    }
*/


    /*
        Copies the venue that is passed in as an argument.

        It uses RTTI in the form of getClass() to determine
        what the values of the next reference and each of the objects in
        the array will be.
    */
    Item(Item copy)
    {
        name = copy.name;
        description = copy.description;
        quantity = copy.quantity;
    }


    /*
       This method is abstract, and is implemented by the derived classes.
    */
    abstract void method();



    /*
        These methods are empty, but are declared to be overriden by the derived classes.
    */
    public void copyItem(Food copy)
    {
    }



    public void copyItem(Tool copy)
    {
    }



    /*
    public void copyItem(Entertainment copy)
    {
    }
     */



    public void copy(Item get)
    {
        description = get.description;
        quantity = get.quantity;
        name = get.name;
    }



    public int compareEquality(String check)
    {
        if (name != null)
        {
            return name.compareToIgnoreCase(check);
        }
        else
        {
            return -1;
        }
    }


    public int compareEquality(Item check)
    {
        if (name != null)
        {
            return name.compareToIgnoreCase(check.name);
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
        if (name != null)
        {
            if (name.compareToIgnoreCase(check) >= 0)
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
        if (name != null)
        {
            if (name.compareToIgnoreCase(check.name) >= 0)
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
        System.out.print(name);
    }



    public void displayDescription()
    {
        System.out.print(description);
    }



    /*
        Sets the data members to null.
    */
    public void nullify()
    {
        name = null;
        description = null;
        quantity = 0;
    }


    /*
        This method checks the type of the current object, and returns a value associated with each type.
    */
    public int checkType()
    {
        if (this.getClass() == Food.class)
        {
            return 1;
        }
        else if (this.getClass() == Tool.class)
        {
            return 2;
        }
        /*
        else if (this.getClass() == Entertainment.class)
        {
            return 3;
        }
         */
        else
        {
            return 0;
        }
    }



    /*
        Asks the user for information regarding what kind of venue they want to create.

        Checks the string they typed and matches it with a venue type. Then it creates
        a new venue of that type.
    */
    public Item setItem() throws Exception {
        String temp;
        Item itemT = null;
        boolean success = false, quit = false;
        input = new Scanner(System.in);

        do {
            System.out.println("What kind of item do you want to create (com.company.Food or Tool?)");//, or Entertainment? (Case sensitive)");
            temp = input.nextLine();
            if (temp.equalsIgnoreCase("com.company.Food"))
            {
                itemT = new Food(temp);
                success = true;
            }
            else if (temp.equalsIgnoreCase("Tool"))

            {
                itemT = new Tool(temp);
                success = true;
            }
            /*
            else if (temp.equalsIgnoreCase("Entertainment"))
            {
                itemT = new Entertainment(temp);
                success = true;
            }
             */
            else
            {
                System.out.println("That was not a valid choice. Please press \"t\" to try again," +
                        "or if you would like to quit then type \"q\"");
                temp = input.nextLine();
                if (temp.equalsIgnoreCase("q"))
                {
                    quit = true;
                }
            }
        } while (!success && !quit);

        return itemT;
    }



    /*
        Function to apply a venue object to the
        festival object argument.

        It uses user input to set the venue, and then
        applies the venue to the festival.
    */
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
                success = dest.setItem(temp);
            }
            else
            {
                success = 0;
            }
        }
        return success;
    }



    public boolean display()
    {
        boolean success = true;
        if (name != null)
        {
            if (description != null)
            {
                System.out.println("com.company.Item " + name + "'s description is " + description + ".");
                System.out.print("There ");
                if (quantity == 1)
                    System.out.print("is ");
                else
                {
                    System.out.print("are ");
                }
                System.out.println(quantity + " of them.");
                success = true;
            }
        }
        else
        {
            success = false;
        }
        return success;
    }


    public String getName()
    {
        return name;
    }


    public String getNameInit()
    {
        if (name != null)
       return Character.toString(name.charAt(0));
        else
        {
            return "";
        }
    }


    public boolean isEmpty()
    {
        if (name == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    // Protected Methods
    protected void incrementQuantity()
    {
        ++quantity;
    }


}
