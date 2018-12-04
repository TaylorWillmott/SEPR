package com.rear_admirals.york_pirates.Attacks;

import com.rear_admirals.york_pirates.Ship;

public class Swivel extends Attack {

	protected Swivel() {
		name = "Swivel";
		desc = "High accuracy, low damage attack.";
	}

    @Override
    public boolean doAttack(Ship attacker, Ship defender) {

        if (attacker.getAccuracy() > 0) {
            defender.damage(attacker.getAttack()*2);
            attacker.damage(defender.getDefence());
            return true;
        }
        return false;
    }

    public static final Swivel attackSwivel = new Swivel();
}
