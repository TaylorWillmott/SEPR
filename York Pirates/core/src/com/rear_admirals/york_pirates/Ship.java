package com.rear_admirals.york_pirates;

import java.util.HashMap;

public class Ship {
    public int attack;
    public int defence;
    public int accuracy;
    public int health;
    private String type;

    public Ship(int attack, int defence, int accuracy, int health, String type) {
        this.attack = attack;
        this.defence = defence;
        this.accuracy = accuracy;
        this.health = health;
        this.type = type;
    }

    public void attack(Ship target){

    }
    public void damage(int amt){

    }
    public int getMaxHealth(){
		return 0;
    }

    // Getters and Setters


    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getHealth() {
        return health;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
