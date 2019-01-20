package com.rear_admirals.york_pirates;


import com.badlogic.gdx.graphics.Texture;
import com.rear_admirals.york_pirates.Screen.Combat.Attacks.*;
import com.rear_admirals.york_pirates.Screen.Combat.Attacks.GrapeShot;

import java.util.ArrayList;
import java.util.List;

import static com.rear_admirals.york_pirates.College.*;
import static com.rear_admirals.york_pirates.ShipType.*;

public class Player {
    public Ship getPlayerShip() {
        return playerShip;
    }

    public Ship playerShip;
    private int gold;
    private int points;
    public static List<Attack> attacks = new ArrayList<Attack>();
    private Texture sailingTexture;

    public Player() {
	    playerShip = new Ship(Brig, "Your Ship", Derwent);
        gold = 10000;
        points = 0;

        attacks.add(Ram.attackRam);
        attacks.add(GrapeShot.attackSwivel);
        attacks.add(Attack.attackBoard);


    }

    public Player(Ship ship) {
        playerShip = ship;
        gold = 0;
        points = 0;

        attacks.add(Ram.attackRam);
        attacks.add(Attack.attackSwivel);
        attacks.add(Attack.attackBoard);


    }

    public int getPoints() { return points; }

	public int getGold() { return gold; }

	public boolean payGold(int amount){
        if (amount > gold){
            return false;
        }
        else{
            addGold(-amount);
            return true;
        }
    }

	public List<Attack> getAttacks() { return attacks; }

    public void addPoints(int amount) { points += amount; }

    public void addGold(int amount) { gold = gold + amount; }

    public void setAttacks( List<Attack> attacks ) { this.attacks = attacks; }

}