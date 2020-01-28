/*
   Author: Mustafa Radheyyan
   Course: CS300
   Date: 1/18/2020
   File: Implementation File
   Proj: This file contains the Item class.
*/

package com.company;

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

    // Public Methods
    Item()
    {
        name = null;
        description = null;
        quantity = 0;
    }



    Item(String name)
    {
        input = new Scanner(System.in);
        System.out.println("What do you want to name the " + name + " item?");

        this.name = input.nextLine();

        System.out.println("What do you want to name the description to be?");
        this.description = input.nextLine();

        quantity = 0;
    }



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
    public Item setItem()
    {
        String temp;
        Item itemT = null;
        boolean success = false, quit = false;
        input = new Scanner(System.in);

        do {
            System.out.println("What kind of item do you want to create (Food or Tool?)");//, or Entertainment? (Case sensitive)");
            temp = input.nextLine();
            if (temp.equalsIgnoreCase("Food"))
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
    public int applyItem(Inventory dest)
    {
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
                System.out.println("Item " + name + "'s description is " + description + ".");
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
