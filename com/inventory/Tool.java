/*
   Author: Mustafa Radheyyan
   Course: CS300
   Date: 1/18/2020
   File: Implementation File
   Proj: This file contains the Tool class.
*/

package com.inventory;

import java.util.Scanner;

public class Tool extends Item
{
    protected Scanner input=null;
    // Private Fields //
    // The kinds of tools sold at the tools venue
    private String strength;

    // Public Methods
    Tool()
    {
        super();
        strength = null;
    }


    public Tool(Items type)
    {
        super(type);
    }


    Tool(Tool copy)
    {
        super(copy);
        strength = copy.strength;
    }



    Tool(Item copy)
    {
        super(copy);
        copy.copyItem(this);
    }



    /*
        Uses user input to ask for the type of tools to be sold.
    */
    Tool(String name) throws Exception {
        super(name);
        Scanner input = new Scanner(System.in);

     /* System.out.print("What tools will you sell at ");
        displayName();
        System.out.println("? (Use a comma separated list of all tools)");
        toolsSold = input.nextLine();
      */
    }

    public void copyItem(Tool copy)
    {
        copy.strength = strength;
    }



    /*
        This is an implementation of the incomplete abstract class method,
        although it was created solely to make the base class abstract.
    */
    void method()
    {

    }



    /*
        Sets the data members to null.
    */
    public void nullify()
    {
        super.nullify();
        strength = null;
    }



    /*
        Overloaded/overridden method that just calls the base classes version of the method.
    */
    public int applyItem(Inventory dest) throws Exception {
        return super.applyItem(dest);
    }



    /*
        If the base class's display was false, then don't call this class's
    */
    public boolean display()
    {
        if (super.display())
        {
            System.out.println("The strength of this tool is: " + strength);
            return true;
        }
        else
        {
            return false;
        }
    }
}
