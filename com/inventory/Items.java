package com.inventory;

public enum Items {

    DEFAULT		(0, " ", " ",9999,0, 0, 0, false),
    PLAYER		(-1, "Player", "Our hero or heroine of the game", 9999, 0,-1, -1, false),
    BINOCULARS	(1, "Binoculars", "These let you see to a distance 2x greater than normal.", 5,1,0, 0, true),
    POWERBAR	(2, "PowerBar", "This nutritious bar gives you an extra 3 energy.", 3, 10,3, 1, true),
    WEEDWHACKER	(3, "WeedWhacker", "This tool can cut through bushes in no time.", 10,1,0, 2, true),
    JACKHAMMER	(4, "Jack Hammer", "This tool can cut through rocks in no time", 15,1,1, 1, true),
    CHAINSAW	(5, "Chainsaw", "This tool can cut down trees in no time.", 15,1,2, 0, true),
    BOAT		(6, "Boat", "This will get you over the water safely.", 25, 1,1, 0, true),
    JEWEL		(7, "Jewel", "The ultimate treasure, this is what you've been looking for all along.", 9999,1,2, 1, false),
    FISH		(8, "Fish", "This tasty fish gives you an extra 1 energy", 1, 10,3, 0, true),
    GOLD		(9, "Gold", "You have stumbled upon an ancient pirate's stash, enjoy.", 100, 1, 0, 1, false),
    WALLET		(10, "Magic Wallet", "This handy wallet keeps your money safe", 9999, 0, -1, -1, false),
    SHOPKEEP	(11, "Shopkeep", "A place to buy things for your adventure", 9999, 0, 1, 2, false);
    
    //ROPE(10, "Rope", "This will allow you to scale a rocky cliff, or conversely, a difficult tree",0, 0);

    int itemID;
    int imagePosX;
    int imagePosY;
    int value;
    int inventoryLimit;
    String name;
    String description;
    boolean buyable;

    Items(int itemID, String name, String description, int value, int inventoryLimit, int imagePosX, int imagePosY, boolean buyable) {
        this.itemID = itemID;
        this.name = name;
        this.value = value;
        this.description = description;
        this.imagePosX = imagePosX;
        this.imagePosY = imagePosY;
        this.inventoryLimit = inventoryLimit;
        this.buyable = buyable;
    }

    public static Items itemByID(int n){
        for (Items i : values()){
            if (i.itemID == n){
                return i;
            }
        }
        throw new IllegalArgumentException(String.valueOf(n));
    }


    public int getItemID() { return itemID; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getValue(){ return value;}
    public int getImagePosX() { return imagePosX; }
    public int getImagePosY() { return imagePosY; }
    public boolean getBuyable() { return buyable; }

}
