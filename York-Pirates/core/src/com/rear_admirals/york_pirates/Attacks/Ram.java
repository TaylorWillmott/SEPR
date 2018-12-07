package com.rear_admirals.york_pirates.Attacks;

import com.rear_admirals.york_pirates.Ship;

public class Ram extends Attack {

	protected Ram(String name, String desc, int dmgMultiplier, double accMultiplier, boolean skipMove) {
		super(name, desc, dmgMultiplier, accMultiplier, skipMove);
	}

	@Override
	public int doAttack(Ship attacker, Ship defender) {
		if ( doesHit(attacker.getAccuracy(), (int)(10*accMultiplier), 100) ) {
			damage = attacker.getAttack()*dmgMultiplier;
			defender.damage(damage);
			attacker.damage(damage/2);
			return damage;
		}
		return 0;
	}

//	public static final Attack attackRam = new Ram();
	public static Attack attackRam = new Ram("Ram","Ram your ship into your enemy, causing damage to both of you.", 4, 1, false);


}