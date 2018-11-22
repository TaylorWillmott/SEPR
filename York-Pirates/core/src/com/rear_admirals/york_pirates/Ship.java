package com.rear_admirals.york_pirates;

import com.badlogic.gdx.graphics.Texture;

public class Ship extends MoveableObject {
    public int attack;
    public int defence;
    public int accuracy;
    public int health;
    private shipType type;

    public Ship(int attack, int defence, int accuracy, int health, shipType type) {
        this.attack = attack;
        this.defence = defence;
        this.accuracy = accuracy;
        this.health = health;
        this.type = type;
        this.tex = type.texture;
    }

    public Ship(shipType type) {
        this.type = type;
        this.attack = type.getAttack();
        this.defence = type.getDefence();
        this.accuracy = type.getAccuracy();
        this.health = type.getHealth();
    }

    public void forward(int distance) { angularMove(a, distance); }

    public void forward() { angularMove(a,3); }

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
        return type.getName();
    }

    public void setType(shipType type) {
        this.type = type;
    }
}

class shipType {
    private int attack;
    private int defence;
    private int accuracy;
    private int health;
    public Texture texture;
    private String name;

    public shipType() {
        this.name = name;
        this.attack = attack;
        this.defence = defence;
        this.accuracy = accuracy;
        this.health = health;
        this.texture = texture;
    }

    public shipType (String name, int attack, int defence, int accuracy, int health) {
        this.name = name;
        this.attack = attack;
        this.defence = defence;
        this.accuracy = accuracy;
        this.health = health;
        this.texture = new Texture(name.toLowerCase() + ".png");
    }

    public shipType (String name, int attack, int defence, int accuracy) {
        this.name = name;
        this.attack = attack;
        this.defence = defence;
        this.accuracy = accuracy;
        this.health = 100;
        this.texture = new Texture(name.toLowerCase() + ".png");
    }

    public String getName() { return name; }

    public int getAttack() { return attack; }

    public int getDefence() { return defence; }

    public int getAccuracy() { return accuracy; }

    public int getHealth() { return health; }

}