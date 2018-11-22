package com.rear_admirals.york_pirates;

import com.badlogic.gdx.graphics.Texture;

public class ShipType {
	int attack;
	int defence;
	int accuracy;
	int health;
	String name;
	Texture texture;

	public ShipType (String name, int attack, int defence, int accuracy, int health) {
		this.name = name;
		this.attack = attack;
		this.defence = defence;
		this.accuracy = accuracy;
		this.health = health;
		this.texture = new Texture(name.toLowerCase() + ".png");
	}

	public String getName() { return name; }

	public int getAttack() { return attack; }

	public int getDefence() { return defence; }

	public int getAccuracy() { return accuracy; }

	public int getHealth() { return health; }

	public static ShipType Sloop = new ShipType("Sloop", 4, 4, 7, 80);
	public static ShipType Brig = new ShipType("Brig", 5, 5, 5, 100);
	public static ShipType Galleon = new ShipType("Galleon", 6, 6, 3, 120);

}
