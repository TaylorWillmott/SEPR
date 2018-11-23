package com.rear_admirals.york_pirates.Attacks;

import com.rear_admirals.york_pirates.Ship;

public class Attack {
	private String name = "Broadside";
	private String desc = "Fire a broadside at your enemy.";

	public Attack() {};

	public boolean doAttack(Ship attacker, Ship defender) {
		if (attacker.accuracy > 0) { // Accuracy Check Goes Here
			defender.damage(attacker.attack);
			return true;
		}
		return false;
	}

	public String getName() { return name;	}
	public String getDesc() { return desc; }

	public static Attack attackMain = new Attack();

}