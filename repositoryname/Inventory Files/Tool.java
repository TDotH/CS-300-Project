/*
   Author: Mustafa Radheyyan
   Course: CS300
   Date: 1/18/2020
   File: Implementation File
   Proj: This file contains the Tool class.
*/

package com.company;

import java.util.Scanner;

public class Tool extends Item
{
    protected Scanner input=null;
    // Private Fields //
    // The kinds of tools sold at the tools venue
    private String toolsSold;

    // Public Methods
    Tool()
    {
        super();
        toolsSold = null;
    }



    Tool(Tool copy)
    {
        super(copy);
        toolsSold = copy.toolsSold;
    }



    Tool(Item copy)
    {
        super(copy);
        copy.copyItem(this);
    }



    /*
        Uses user input to ask for the type of tools to be sold.
    */
    Tool(String name)
    {
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
        copy.toolsSold = toolsSold;
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
        toolsSold = null;
    }



    /*
        Overloaded/overridden method that just calls the base classes version of the method.
    */
    public int applyItem(Inventory dest)
    {
        return super.applyItem(dest);
    }



    /*
        If the base class's display was false, then don't call this class's
    */
    public boolean display()
    {
        if (super.display())
        {
            if (toolsSold != null)
            {
               // System.out.println("The tools sold at this venue are: " + toolsSold);
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}
