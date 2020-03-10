package com.map;

import javax.swing.*;
import com.inventory.*;

import java.util.HashMap;

public class ShopKeeper extends JPanel {

    HashMap<Item, Integer> valueInv = new HashMap<Item, Integer>();


    //Starting money 100. Can change to whatever.
    private int money = 100;

    public ShopKeeper(){
        //When map loaded
        initInventory();
    }

    //Shop Keeper Inventory functions------------------------------------------

    public int getPriceOf(Item anItem){
        return valueInv.get(anItem);
    }

    public HashMap<Item, Integer> getItemsAndPrices(){
        return valueInv;
    }

    public void setShopKeeperInv(HashMap<Item, Integer> inv){
        valueInv = inv;
    }

    public void sellItemToPlayer(Item anItem){
        setMoney(getMoney() - getPriceOf(anItem));
        removeFromShopKeeperInv(anItem);
    }

    private void addToShopKeeperInv(Item anItem, int Price){
        valueInv.put(anItem, Price);
    }

    private void removeFromShopKeeperInv(Item anItem) {
        valueInv.remove(anItem);
    }

    private void initInventory(){
        Food powerBar = new Food(Items.POWERBAR);
        Food fish = new Food(Items.FISH);
        Tool binoculars = new Tool(Items.BINOCULARS);
        Tool weedWhacker = new Tool(Items.WEEDWHACKER);
        Tool jackHammer = new Tool(Items.JACKHAMMER);
        Tool chainSaw = new Tool(Items.CHAINSAW);
        Tool boat = new Tool(Items.BOAT);
        Tool rope = new Tool(Items.ROPE);


        addToShopKeeperInv(powerBar, 3);
        addToShopKeeperInv(fish, 1);
        addToShopKeeperInv(binoculars, 5);
        addToShopKeeperInv(weedWhacker, 10);
        addToShopKeeperInv(jackHammer, 15);
        addToShopKeeperInv(chainSaw, 15);
        addToShopKeeperInv(rope, 5);
        addToShopKeeperInv(boat, 25);
    }

    //Shop Keeper Money functions-----------------------------------------------

    public int getMoney() {
        return money;
    }

    public void setMoney(int newMoneyValue){
        money = newMoneyValue;
    }
}

