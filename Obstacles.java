package com.inventory;

public enum Obstacles {

    DEFAULT(0, " ", " "),
    BUSHES(3, "WeedWhacker", "A thorny entanglement of bushes, the thorn in the side of any hero."),
    ROCKS(4, "Jack Hammer", "Larger-than-life rocks which happen to be blocking my path."),
    TREES(5, "Chainsaw", "A thicket of trees, closely gathered together and impassable."),
    WATER(6, "Boat", "The raging waters engulf all those who try to cross without proper equipment."),
    CLIFF(10, "Rope", "A vertical mountainside, my freeclimbing skills aren't yet good enough to scale this without a rope.");

    int obstacleID;
    String weakness;
    String description;

    Obstacles(int obstacleID, String weakness, String description) {
        this.obstacleID = obstacleID;
        this.weakness = weakness;
        this.description = description;
    }

    public int getObstacleID() { return obstacleID; }
    public String getWeakness() { return weakness; }
    public String getDescription() { return description; }

}
