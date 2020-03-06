package com.obstacles;
import com.inventory.Items;

public enum Obstacles {

    DEFAULT(0, Items.DEFAULT, " ",  " ", true, 0, 0, 0),
    BUSHES(1, Items.WEEDWHACKER, "Bushes", "A thorny entanglement of bushes, the thorn in the side of any hero.", true, 1, 0, 0),
    ROCKS(2, Items.JACKHAMMER, "Rock", "A Larger-than-life rock which happens to be blocking the path.", false, -1, 1, 0),
    TREES(3, Items.CHAINSAW, "Trees", "A thicket of trees, closely gathered together and impassable.", false, -1, 2, 0);
    //WATER(6, "Boat", "The raging waters engulf all those who try to cross without proper equipment."),
    //CLIFF(10, "Rope", "A vertical mountainside, my freeclimbing skills aren't yet good enough to scale this without a rope.");

    int obstacleID;
    Items requiredItem;
    String description;
    String name;
    boolean passable;
    int energyCost;
    int imagePosX;
    int imagePosY;

    Obstacles(int obstacleID, Items requiredItem, String name, String description, boolean passable, int energyCost, int imagePosX, int imagePosY) {
        this.obstacleID = obstacleID;
        this.requiredItem = requiredItem;
        this.name = name;
        this.description = description;
        this.passable = passable;
        this.energyCost = energyCost;
        this.imagePosX = imagePosX;
        this.imagePosY = imagePosY;
    }

    public int getObstacleID() { return obstacleID; }
    public Items requiredItem() { return requiredItem; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean getPassable() { return passable; }
    public int getEnergyCost() { return energyCost; }
    public int getImagePosX() { return imagePosX; }
    public int getImagePosY() { return imagePosY; }

}
