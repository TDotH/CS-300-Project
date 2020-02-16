package com.inventory;

/*
   Author: Mustafa Radheyyan
   Course: CS300
   Date: 1/18/2020
   File: Implementation File
   Proj: This file contains the Item class.
*/

import java.util.Scanner;

public abstract class Item
{
    private Items item;
    protected Scanner input = null;
    // Private Fields //
    private int quantity;

    // Public Methods
    Item()
    {
        item = null;
        quantity = 0;
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



    public void copy(Item get)
    {
        item.description = get.item.description;
        quantity = get.quantity;
        item.name = get.item.name;
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
        if (this.getClass() == Food.class)
        {
            return 1;
        }
        else if (this.getClass() == Tool.class)
        {
            return 2;
        }
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
        if (item != null)
        {
            System.out.println("com.company.Item " + item.name + "'s description is " + item.description + ".");
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
        else
        {
            success = false;
        }
        return success;
    }


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
        if (item == null)
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
