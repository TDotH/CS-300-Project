package com.inventory;

public enum Items {

        DEFAULT(0, " ", " "), BINOCULARS(1, "Binoculars", "These let you see to a distance 2x greater than normal."),
    POWERBAR(2, "PowerBar", "This nutritious bar gives you an extra 3 energy."), WEEDWHACKER(3, "WeedWhacker", "This tool can cut through bushes in no time."),
    JACKHAMMER(4, "Jack Hammer", "This tool can cut through rocks in no time"), CHAINSAW(5, "Chainsaw", "This tool can cut down trees in no time."),
    BOAT(6, "Boat", "This will get you over the water safely."), JEWEL(7, "Jewel", "The ultimate treasure, this is what you've been looking for all along."),
    FISH(8, "Fish", "This tasty fish gives you an extra 1 energy"), GOLD(9, "Gold", "You have stumbled upon an ancient pirate's stash, enjoy."),
    ROPE(10, "Rope", "This will allow you to scale a rocky cliff, or conversely, a difficult tree");

        int itemID;
        String name;
        String description;

        Items(int itemID, String name, String description) {
            this.itemID = itemID;
            this.name = name;
            this.description = description;
        }

        int getItemID() { return itemID; }
        String getName() { return name; }
        String getDescription() { return description; }

}
