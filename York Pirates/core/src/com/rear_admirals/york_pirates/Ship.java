package com.rear_admirals.york_pirates;

public class Ship {
    public int attack;
    public int defence;
    public int accuracy;
    public int health;
    public string type;

    public void attack(Ship target){

    }
    public void damage(int amt){

    }
    public int getMaxHealth(){

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
    public string getType() {
        return type;
    }

    public void setType(string type) {
        this.type = type;
    }
}
