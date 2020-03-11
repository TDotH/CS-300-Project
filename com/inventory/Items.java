package com.inventory;

public enum Items {

    DEFAULT(0, " ", " ",0,0, 0),
    PLAYER(-1, "Player", "Our hero or heroine of the game", 9999, -1, -1),
    BINOCULARS(1, "Binoculars", "These let you see to a distance 2x greater than normal.", 5,0, 0),
    POWERBAR(2, "PowerBar", "This nutritious bar gives you an extra 3 energy.", 3,3, 1),
    WEEDWHACKER(3, "WeedWhacker", "This tool can cut through bushes in no time.", 10,0, 2),
    JACKHAMMER(4, "Jack Hammer", "This tool can cut through rocks in no time", 15,1, 1),
    CHAINSAW(5, "Chainsaw", "This tool can cut down trees in no time.", 15,2, 0),
    BOAT(6, "Boat", "This will get you over the water safely.", 25,1, 0),
    JEWEL(7, "Jewel", "The ultimate treasure, this is what you've been looking for all along.", 9999,2, 1),
    FISH(8, "Fish", "This tasty fish gives you an extra 1 energy", 1,3, 0),
    GOLD(9, "Gold", "You have stumbled upon an ancient pirate's stash, enjoy.", 100,0, 1),
    ROPE(10, "Rope", "This will allow you to scale a rocky cliff, or conversely, a difficult tree", 5,0, 0);

        int itemID;
        int imagePosX;
        int imagePosY;
        int value;
        String name;
        String description;

        Items(int itemID, String name, String description, int value, int imagePosX, int imagePosY) {
            this.itemID = itemID;
            this.name = name;
            this.value = value;
            this.description = description;
            this.imagePosX = imagePosX;
            this.imagePosY = imagePosY;
        }

        public int getItemID() { return itemID; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public int getValue(){ return value;}
        public int getImagePosX() { return imagePosX; }
        public int getImagePosY() { return imagePosY; }

}
