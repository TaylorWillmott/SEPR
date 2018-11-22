package com.rear_admirals.york_pirates.Attacks;

import com.rear_admirals.york_pirates.Ship;

public class Ram extends Attack {
	private String name = "Ram";
	private String desc = "Ram your ship into your enemy, causing damage to both of you.";

	@Override
	public boolean doAttack(Ship attacker, Ship defender) {
		if (attacker.accuracy > 0) { // Accuracy Check Goes Here
			defender.damage(attacker.attack*2);
			attacker.damage(defender.defence);
			return true;
		}
		return false;
	}
}