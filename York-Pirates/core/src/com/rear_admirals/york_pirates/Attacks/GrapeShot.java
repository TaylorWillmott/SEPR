package com.rear_admirals.york_pirates.Attacks;

import com.rear_admirals.york_pirates.Ship;

public class GrapeShot extends Attack {
	private String name = "Grape Shot";
	private String desc = "Fire a bundle of smaller cannonballs at the enemy.";

	@Override
	public boolean doAttack(Ship attacker, Ship defender) {
		boolean toReturn = false; // Tracks if any shots have hit.

		for (int i = 0; i < 4; i++) { // Fires 4 shots.
			if (doesHit(attacker.getAccuracy(), 5, 100)) { // Multiplier is halved, so each shot is half as likely to hit.
				defender.damage(attacker.getAttack() / 2); // Landed shots do half as much damage as a regular shot.
				toReturn = true;
			}
		}
		return toReturn;
	}

	public static final Attack attackGrape = new GrapeShot();

}
