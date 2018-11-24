package com.rear_admirals.york_pirates.Attacks;

import com.rear_admirals.york_pirates.Ship;

public class Ram extends Attack {
	private String name = "Ram";
	private String desc = "Ram your ship into your enemy, causing damage to both of you.";

	@Override
	public boolean doAttack(Ship attacker, Ship defender) {
		if ( doesHit(attacker.getAccuracy(), 10, 100) ) {
			defender.damage(attacker.getAttack()*2);
			attacker.damage(defender.getDefence());
			return true;
		}
		return false;
	}

	public static Ram attackRam = new Ram();

}