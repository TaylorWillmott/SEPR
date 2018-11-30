package com.rear_admirals.york_pirates.Attacks;

import com.rear_admirals.york_pirates.Ship;

public class Ram extends Attack {

	protected Ram() {
		name = "Ram";
		desc = "Ram your ship into your enemy, causing damage to both of you.";
	}

	@Override
	public boolean doAttack(Ship attacker, Ship defender) {
		if ( doesHit(attacker.getAccuracy(), 10, 100) ) {
			defender.damage(attacker.getAttack()*2);
			attacker.damage(defender.getDefence());
			return true;
		}
		return false;
	}

	public static final Attack attackRam = new Ram();

}