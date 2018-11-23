package com.rear_admirals.york_pirates;

public class Ship extends MoveableObject {
    public int attack;
    public int defence;
    public int accuracy;
    public int health;
    private ShipType type;

    public Ship(int attack, int defence, int accuracy, int health, ShipType type) {
        this.attack = attack;
        this.defence = defence;
        this.accuracy = accuracy;
        this.health = health;
        this.type = type;
        this.tex = type.texture;
    }

    public Ship(ShipType type) {
        this.type = type;
        this.attack = type.getAttack();
        this.defence = type.getDefence();
        this.accuracy = type.getAccuracy();
        this.health = type.getHealth();
    }

    public void forward(int distance) { angularMove(pos.z, distance); }

    public void forward() { angularMove(pos.z,3); }

    public void damage(int amt){
    	health = health - amt;
    	if (health <= 0) {
    		sink();
	    }
    }

    public void sink() {
        //TODO Sinking function.
    }

    // Getters and Setters

    public int getMaxHealth(){
		return type.health;
    }

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

	public String getType() {
		return type.getName();
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

    public void setType(ShipType type) { this.type = type; }

}