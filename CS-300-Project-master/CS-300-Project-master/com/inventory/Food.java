package com.inventory;/*
   Author: Mustafa Radheyyan
   Course: CS300
   Date: 1/18/2020
   File: Implementation File
   Proj: This file contains the com.company.Food class.
*/

import java.util.Scanner;

public class Food extends Item
{
    protected Scanner input=null;
    // Private Fields //
    // The kinds of food sold at the food venue
    private int energyContents;

    // Public Methods
    Food()
    {
        super();
        energyContents = 0;
    }



    public Food(Items type)
    {
        super(type);
    }


    Food(Food copy)
    {
        super(copy);
        energyContents = copy.energyContents;
    }



    Food(Item copy)
    {
        super(copy);
        copy.copyItem(this);
    }



    /*
        Uses user input to ask for the type of food to be served.
    */
    Food(String name) throws Exception {
        super(name);
/*        Scanner input = new Scanner(System.in);

        do
        {
            System.out.println("How much energy will this food give?");
            if (input.hasNextInt())
            {
                energyContents = input.nextInt();
            }
            else
            {
                System.out.println("Please type an integer value.");
                input.next();
            }
        } while (energyContents == 0);

 */
    }



    public void copyItem(Food copy)
    {
        copy.energyContents = energyContents;
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
        energyContents = 0;
    }



    /*
        Overloaded/overridden method that just calls the base classes version of the method.

    public int applyItem(Inventory dest) throws Exception {
        return super.applyItem(dest);
    }
    */



    /*
        If the base class's display was false, then don't call this class's

    public boolean display()
    {
        if (super.display())
        {
            System.out.print("The energy boost is: ");
            if (energyContents > 0)
            {
                System.out.print("+");
            }
            System.out.println(energyContents);
        }
        else
        {
            return false;
        }
        return true;
    }

     */
}
