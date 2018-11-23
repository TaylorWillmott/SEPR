package com.rear_admirals.york_pirates.Attacks;

import com.rear_admirals.york_pirates.Ship;

public class Template extends Attack {
	// This is a template for how to structure attacks.
	// Ideally remove the comments in the actual attack to make things a bit cleaner.

	private String name = "NAME"; // Formatted name of the attack. This will be displayed in-game.
	private String desc = "DESCRIPTION"; // Description of the attack. This will be displayed in-game so try not to be too technical.

	@Override
	public boolean doAttack(Ship attacker, Ship defender) {
		// This is the main function for this attack. Make sure you leave the name as doAttack (@Override tag helps).
		// It takes the attacking and defending ships as parameters, get any relevant stats from there.

		if (attacker.accuracy > 0) { // Accuracy Check Goes Here
			defender.damage(attacker.getAttack()*2);
			attacker.damage(defender.getDefence());
			return true; // Return true if the attack was successful.
		}
		return false; // Return false if it was not successful.
	}

	public static Template attackTemplate = new Template();

}
