package com.actors;

import javax.swing.*;
import com.inventory.*;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ShopKeeper implements Objects {

    //ShopKeep Inventory
    private ArrayList<Item> inventory = new ArrayList<Item>();

    //Max Inventory size
    //Didn't wanna do this but I'm sick of trying think of a better way. I've already invested a good five minutes.
    int FINAL_INVENTORY_SIZE = 6;

    //Starting money 100. Can change to whatever.
    private int money = 100;

    private int posX, posY;


    public ShopKeeper(int posX, int posY){
        this.posX = posX;
        this.posY = posY;

    }


    public Pair<String, ArrayList<Item>> initDialogue(){
        setRandomInv(FINAL_INVENTORY_SIZE);
        String hello = "ShopKeep has wares if you have coin...";
        ArrayList<Item> tempInv = getInventory();

        Pair<String, ArrayList<Item>> dialoguePackage = new Pair<String, ArrayList<Item>>(hello, tempInv);

        return dialoguePackage;
    }

    public ArrayList<Item> getInventory(){
        return this.inventory;
    }

    private void setRandomInv(int inventorySize){
        int MAX_SIZE = inventorySize;
        int possibleItems = (Items.values()).length-2;
        int maxValue = 100;
        Random random = new Random();

        ArrayList<Item> tempInventory = new ArrayList<Item>();

        for (int x = 0; x < MAX_SIZE; x++){
            Item thisItem = new Item(Items.itemByID(random.nextInt(possibleItems)));
            int maxCount = thisItem.getInventoryLimit();
            int curCount = 0;

            for (int y = 0; y < tempInventory.size(); y++){
                if (tempInventory.get(y).getName() == thisItem.getName()){
                    curCount++;
                }
            }

            if (curCount < maxCount && thisItem.getValue() < maxValue){
                tempInventory.add(thisItem);
            } else {
                x--;
            }
        }

        this.inventory = tempInventory;
    }


    @Override
    public void draw(Graphics2D g, int tile_size, int centerPosX, int centerPosY) {

    }

    @Override
    public void draw(Graphics2D g, int tile_size, int centerPosX, int centerPosY, Image anImage) {

    }
}

