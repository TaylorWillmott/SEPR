package com.rear_admirals.york_pirates;

public class Ship extends MoveableObject {
	private String name;
    private int attack;
	private int defence;
	private int accuracy;
	private int health;
    private ShipType type;
    private int healthMax;

    public int getHealthMax() {
        return healthMax;
    }

    public Ship(int attack, int defence, int accuracy, int health, ShipType type) {
    	this.name = type.name;
        this.attack = attack;
        this.defence = defence;
        this.accuracy = accuracy;
        this.health = health;
        this.type = type;
        this.tex = type.texture;
        this.healthMax = defence*20;
    }

    public Ship(ShipType type) {
    	this(type.getAttack(), type.getDefence(), type.getAccuracy(), 100, type);
    	this.health = getMaxHealth();
    	this.healthMax = defence*20;
    }

    public Ship(ShipType type, String name) {
	    this(type);
	    this.name = name;
    }

    public void forward(int distance) { angularMove(pos.z, distance); }

    public void forward() { forward(3); }

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

	public String getName() { return name; }

    public int getMaxHealth(){
		return healthMax;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public int getAccuracy() { return accuracy; }

    public int getHealth() {
        return health;
    }

	public String getType() {
		return type.getName();
	}

	public void setName(String name) { this.name = name; }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefence(int defence) {
        this.defence = defence;
        this.healthMax = defence * 20;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setType(ShipType type) { this.type = type; }

}