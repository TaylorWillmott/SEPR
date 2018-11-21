package com.rear_admirals.york_pirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 position;
    private double angle;
    private int gold;
    private int points;

    public Player() {
        this.position = new Vector2();
        this.points = 0;
//        Starting angle and gold can be changed based on game, just put 0 here for example
        this.angle = 0.0;
        this.gold = 0;
    }
    
    public int getPoints() { return this.points;}
    public void addPoints(int points) { this.points += points;}

    public int getGold() {return this.gold;}
    public void addGold(int gold) {this.gold += gold;}
    public void subtractGold(int gold) {this.gold -= gold;};

}
