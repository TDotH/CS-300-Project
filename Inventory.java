/*
   Author: Mustafa Radheyyan
   Course: CS300
   Date: 1/18/2020
   File: Implementation File
   Proj: This file contains the Inventory class.
*/

package com.company;

import java.util.Scanner;

public class Inventory
{
    protected Scanner input = null;
    // Private Fields //
    // The size of the CLL.
    // final private int ARRAY_SIZE = 5;
    // A reference to the rear of the circular linked list.
    private Item [] heroInv;
    // The name of the inventory
    private String name;
    // The name of the location the inventory is in.
    private String location;
    // The maximum amount of items allowed in the inventory
    private int maxItems;
    // The count of each of the three types of items, and the number of items total.
    private double items, food, tools; //entertainment;
    /*
       The quotas of the venue types, each quota is a ratio out of a total of 1, the
       lower the quota, the less frequent the venue type will show up in the inventory.
    */

    // Public Methods
    /*
       Calls on four methods to set their variables using user input,
       sets the rest to 0 and null.
    */
    Inventory()
    {
        setName();
        setLocation();
        setMaximum();
        items = 0;
        food = 0;
        tools = 0;
        //     entertainment = 0;
    }



    /*
        Copies the variables in the inventory class.
     */
    Inventory(Inventory copy)
    {
        int type, i;
        Item [] inv = new Item[maxItems];

        for (i = 0; i < maxItems; ++i) {
            type = copy.heroInv[i].checkType();
            if (type == 1)
            {
                inv[i] = new Food(copy.heroInv[i]);
            }
            else if (type == 2)
            {
                inv[i] = new Tool(copy.heroInv[i]);
            }
/*        else if (type == 3)
        {
                inv[i] = new Entertainment(copy.heroInv[i]);
        }
 */
        }

        name = copy.name;
        location = copy.location;
        items = copy.items;
        food = copy.food;
        tools = copy.tools;
//      entertainment = copy.entertainment;
        maxItems = copy.maxItems;
    }



    /*
        Uses argument to set the name of the inventory.
    */
    public void setName(String name)
    {
        this.name = name;
    }



    /*
        Uses argument to set the location of the inventory.
    */
    public void setLocation(String location)
    {
        this.location = location;
    }


    /*
        Uses user input to set the name of the inventory.
    */
    public void setName()
    {
        System.out.println("What is the inventory's name?");
        input = new Scanner(System.in);
        name = input.nextLine();
    }



    /*
        Uses user input to set the location of the inventory.
    */
    public void setLocation()
    {
        System.out.println("Where is the inventory located?");
        input = new Scanner(System.in);
        location = input.nextLine();
    }



    public int checkItem(Item add)
    {
        return searchList(add);
    }

    /*
            Checks if the item is already in the inventory

            If it is in the inventory, then its quantity is increased and an
            error is returned.
     */
    public int checkItem(String add)
    {
        return searchList(add);
    }



    public int searchList(Item check)
    {
        int i;
        int found = -1;

        i = 0;
        while (i < maxItems && found == -1)
        {
            if (heroInv[i] != null)
            {
                if (heroInv[i].compareEquality(check) == 0)
                {
                    found = i;
                }
                else
                {
                    ++i;
                }
            }
            else
            {
                ++i;
            }
        }
        return found;
    }



    public int searchList(String name)
    {
        int i;
        int found = -1;

        i = 0;
        while (i < maxItems && found == -1)
        {
            if (heroInv[i] != null && heroInv[i].compareEquality(name) == 0)
            {
                found = i;
            }
            else
            {
                ++i;
            }
        }
        return found;
    }



    /*
        This method resets the CLL.
    */
    public void removeAll()
    {
        if (items > 0)
        {
            System.out.println("All items were removed");
            int i;
            for (i = 0; i < maxItems; ++i) {
                heroInv[i] = null;
            }
            items = 0;
            tools = 0;
            food = 0;
            //    entertainment = 0;
        }
        else
        {
            System.out.println("Your inventory is empty.");
        }
    }



    /*
        The method checks if the items in the inventory have
        exceeded the maximum capacity. If they have, then
        false is returned.
    */
    public boolean checkStatus()
    {
        if (items >= maxItems)
        {
            System.out.println("The " + name + " is at maximum capacity, we are no longer accepting new" +
                    " items!");
            return false;
        }
        else
        {
            return true;
        }
    }



    /*
        The method sets the passed in venue argument to the
        inventory. If there is room for the venue type, it will be added to
        a location that is appropriately spaced away from the last
        venue of that type that is already in the inventory.
    */
    public int setItem(Item add)
    {
        boolean met = false;
        int type, i;
        int success = 0;
        String temp;

        // If the maximum items has been exceeded, return -1.
        if (items >= maxItems)
        {
            System.out.println("The " + name + " is at maximum capacity, we are no longer accepting new" +
                    " venue applications!");
            success = -1;
        }
        // If there are no items in the inventory yet, push the venue in.
        else if (items == 0)
        {
            heroInv = new Item[maxItems];
            type = add.checkType();
            if (type == 1)
            {
                heroInv[0] = new Food(add);
            }
            else
            {
                heroInv[0] = new Tool(add);
            }
            heroInv[0].incrementQuantity();
            ++items;

            System.out.println("com.company.Item successfully added to the inventory.");
            success = 1;
        }
        else
        {
            // Check if the item type is already in the inventory.
            type = checkItem(add);
            if (type >= 0)
            // If it is then continue.
            {
                do
                {
                    System.out.println("You already have one of these ");
                    add.displayName();
                    System.out.println(" in your inventory. Do you want to add another one or cancel? (yes or no)");
                    temp = input.nextLine();

                } while (temp.compareToIgnoreCase("Yes") != 0 &&
                        temp.compareToIgnoreCase("No") != 0);
                if (temp.compareToIgnoreCase("Yes") == 0)
                {
                    heroInv[type].incrementQuantity();
                    success = 1;
                    System.out.println("com.company.Item quantity successfully increased.");
                }
                else
                {
                    System.out.println("com.company.Item is already in the inventory. Not added.");
                }
            }
            else
            {
                i = 0;
                while (!met && i < maxItems)
                {
                    if (heroInv[i] == null)
                    {
                        met = true;
                    }
                    else
                    {
                        ++i;
                    }
                }
                type = add.checkType();
                if (type == 1)
                {
                    heroInv[i] = new Food(add);
                }
                else
                {
                    heroInv[i] = new Tool(add);
                }
                heroInv[i].incrementQuantity();
                ++items;
                System.out.println("com.company.Item successfully added to the inventory.");
            }
        }
        return success;
    }



    /*
        This method is a wrapper method to find items with matching name from
        the inventory, using input to determine the venue to be displayed.
    */
    public boolean search()
    {
        String tempName;
        boolean found = false;
        int value;

        if (items == 0)
        {
            System.out.println("Your inventory is empty.");
        }
        else
        {
            input = new Scanner(System.in);
            System.out.print("What is the name of the venue that you want to search for? ");
            tempName = input.nextLine();

            value = searchList(tempName);

            if (value != -1)
            {
                System.out.println("\ncom.company.Item found. Here are it's details:\n");
                heroInv[value].display();
                System.out.println();
                found = true;
            }
            else
            {
                System.out.println("There are no items with that name in the inventory.");
            }
        }
        return found;
    }



    /*
        This method is a wrapper method to remove a single venue from
        the inventory, using input to determine the venue to be removed.

        It will call both the method to remove the venue from the
        CLL of arrays as well as the 2-3 tree.

        Afterwards, the venue count will be decremented, and by a larger
        amount than 1 if there was a head node of similar data removed.
    */
    public boolean removeItem()
    {
        String tempName;
        int check = 0;
        boolean success = false;

        if (items == 0)
        {
            System.out.println("Your inventory is empty.");
        }
        else
        {
            input = new Scanner(System.in);
            System.out.print("What is the name of the item that you want to discard? ");
            tempName = input.nextLine();

            // This method will search the array for the venue to be deleted.
            check = searchList(tempName);

            if (check >= 0)
            {
                heroInv[check] = null;
                --items;
                System.out.println("com.company.Item removed.");
                success = true;
            }
            else
            {
                System.out.println("There are no items with that name in the inventory.");
            }
        }
        return success;
    }



    /*
        This method is a wrapper method to display all of the
        items in the inventory CLL.
    */
    public void display()
    {
        int i;

        if (items > 0)
        {
            for (i = 0; i < maxItems; ++i) {
                if (heroInv[i] != null)
                {
                    heroInv[i].display();
                    System.out.println();
                }
            }
        }
        else
        {
            System.out.println("The inventory is empty.");
        }
    }



    public void Alphabetize()
    {
        int offset, offset1, offset2, lo1, lo2, hi1, hi2, i, j, index;
        Item [] arranged1;
        Item [] arranged2;

        if (items == 0)
        {
            System.out.println("Your inventory is empty.");
        }
        else if (items == 1)
        {
            System.out.println("The inventory is already sorted!");
        }
        else
        {
            offset = maxItems / 2;

            lo1 = 0;
            if (maxItems % 2 == 0)
            {
                hi1 = lo1 + offset - 1;
            }
            else
            {
                hi1 = lo1 + offset;
            }
            lo2 = hi1 + 1;
            hi2 = maxItems - 1;

            offset1 = (hi1 - lo1) + 1;
            offset2 = (hi2 - lo2) + 1;

            arranged1 = new Item[offset1];
            arranged2 = new Item[offset2];

            AlphabetizeRec(lo1, hi1, arranged1);
            AlphabetizeRec(lo2, hi2, arranged2);

            index = 0;
            i = 0;
            j = 0;
            while (i < offset1 && j < offset2)
            {
                if (arranged1[i] == null)
                {
                    if (arranged2[j] == null)
                    {
                        ++j;
                    }
                    ++i;
                }
                else if (arranged2[j] == null)
                {
                    ++j;
                }
                else
                {
                    if (arranged1[i].compareName(arranged2[j]))
                    {
                        heroInv[index] = arranged2[j];
                        ++j;
                    }
                    else
                    {
                        heroInv[index] = arranged1[i];
                        ++i;
                    }
                    ++index;
                }
            }
            while (i < offset1)
            {
                if (arranged1[i] != null)
                {
                    heroInv[index] = arranged1[i];
                    ++index;
                }
                ++i;
            }
            while (j < offset2)
            {
                if (arranged2[j] != null)
                {
                    heroInv[index] = arranged2[j];
                    ++index;
                }
                ++j;
            }
            if (index < maxItems)
            {
                for (i = index; i < maxItems; ++i) {
                    heroInv[i] = null;
                }
            }
        }
    }


    public void AlphabetizeRec(int low, int high, Item [] des)
    {
        int offset, offset1, offset2, lo1, lo2, hi1, hi2, i, j, index, items = high - low;
        Item [] arranged1;
        Item [] arranged2;

        if (items == 0)
        {
            des[0] = heroInv[low];
        }
        else
        {
            offset = (items) / 2;

            lo1 = low;
            if (items % 2 == 0)
            {
                hi1 = lo1 + offset - 1;
            }
            else
            {
                hi1 = lo1 + offset;
            }
            lo2 = hi1 + 1;
            hi2 = high;

            offset1 = (hi1 - lo1) + 1;
            offset2 = (hi2 - lo2) + 1;

            arranged1 = new Item[offset1];
            arranged2 = new Item[offset2];

            AlphabetizeRec(lo1, hi1, arranged1);
            AlphabetizeRec(lo2, hi2, arranged2);

            index = 0;
            i = 0;
            j = 0;
            while (i < offset1 && j < offset2)
            {
                if (arranged1[i] == null)
                {
                    if (arranged2[j] == null)
                    {
                        ++j;
                    }
                    ++i;
                }
                else if (arranged2[j] == null)
                {
                    ++j;
                }
                else
                {
                    if (arranged1[i].compareName(arranged2[j]))
                    {
                        des[index] = arranged2[j];
                        ++j;
                    }
                    else
                    {
                        des[index] = arranged1[i];
                        ++i;
                    }
                    ++index;
                }
            }
            while (i < offset1)
            {
                if (arranged1[i] != null)
                {
                    des[index] = arranged1[i];
                    ++index;
                }
                ++i;
            }
            while (j < offset2)
            {
                if (arranged2[j] != null)
                {
                    des[index] = arranged2[j];
                    ++index;
                }
                ++j;
            }
        }
    }


    // Protected Methods

    /*
        This method removes the items that share the same name as the passed in string argument.
    */


    // Private Methods
    /*
        Uses input to set the maximum number of items.
    */
    private void setMaximum()
    {
        input = new Scanner(System.in);
        do
        {
            System.out.println("What is the maximum number of items allowed?");
            if (input.hasNextInt())
            {
                maxItems = input.nextInt();
            }
            else
            {
                System.out.println("Please type an integer value.");
                input.next();
            }
        } while (maxItems <= 0);
    }
}
