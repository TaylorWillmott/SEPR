package com.rear_admirals.york_pirates;

public class College {

    public final String name;
    public College ally;
    public College enemy;

    public College(String name, College ally, College enemy) {
    	this.name = name;
    	this.ally = ally;
    	this.enemy = enemy;
    }

    public College(String name) {
    	this(name, null, null);
    }

    public String getName() { return name; }
    public College getAlly() { return ally; }
    public College getEnemy() { return enemy; }

    public void setAlly(College ally) { this.ally = ally; }
    public void setEnemy(College enemy) { this.enemy = enemy; }

}
