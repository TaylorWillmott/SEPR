package com.rear_admirals.york_pirates;


import static com.rear_admirals.york_pirates.ShipType.Brig;

public class Player {
    public Ship playerShip;
    private int gold;
    private int points;

    public Player() {
	    playerShip = new Ship(Brig);
        gold = 0;
        points = 0;
    }

    public Player(Ship ship) {
        playerShip = ship;
        gold = 0;
        points = 0;
    }

    public int getPoints() { return points; }

	public int getGold() { return gold; }

    public void addPoints(int amount) { points += amount; }

    public void addGold(int amount) { gold = gold + amount; }

}
