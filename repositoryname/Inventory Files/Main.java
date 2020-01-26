/*
   Author: Mustafa Radheyyan
   Course: CS300
   Date: 1/18/2020
   File: Implementation File
   Project: This program tests the Frupal Inventory Management application.
*/

package com.company;

import java.util.Scanner;
import javax.swing.JFrame;

public class Main<str>
 {
    public static void main(String[] args)
    {
        // Variable Declarations //
        JFrame frame = new JFrame("Inventory");
        InventoryImage Frupal = new InventoryImage();
        frame.add(Frupal);
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Scanner input = new Scanner(System.in);
        // An inventory object that will contain the items and the circular linked list of arrays.
        Inventory rose = new Inventory();
        // An Tool object used to invoke the item class methods because that class is abstract.
        Tool add = new Tool();
        // The variable for the menu choices.
        char choice;
        /*
          Quit is for quitting the application, full is for indicating that
          the maximum number of items has been reached.
        */
        boolean quit = false, full = false;

        do {
            // Menu Interface Loop
            System.out.print("\nWhat would you like to do?\n\n");
            // If the inventory is full don't allow adding a new item
            if (!full)
            {
                System.out.print("(A)dd an item, ");
            }
            System.out.println("(D)isplay the inventory in order, A(l)phabetize inventory," +
                    " (S)earch for an item, (R)emove an item, (X)remove all items or (q)uit.\n");

            choice = input.next().charAt(0);

            switch (choice) {
                case 'A':
                case 'a':
                    // If the inventory is full and doesn't allow adding a new item
                    if (!full)
                    {
                        if (add.applyItem(rose) == -1)
                        {
                            full = true;
                        }
                    }
                    break;
                case 'D':
                case 'd':
                    rose.display();
                    break;
                case 'L':
                case 'l':
                    rose.Alphabetize();
                    break;
                case 'R':
                case 'r':
                    // If the inventory is full and an item was removed successfully
                    if (rose.removeItem() && full)
                    {
                        full = false;
                    }
                    break;
                case 'S':
                case 's':
                    rose.search();
                    break;
                case 'X':
                case 'x':
                    rose.removeAll();
                    full = false;
                    break;
                case 'Q':
                case 'q':
                    System.out.println("Are you sure you want to quit?");
                    choice = input.next().charAt(0);
                    if (choice == 'y')
                    {
                        quit = true;
                    }
                    break;
            }
        } while (!quit);
    }
}
