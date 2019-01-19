package com.rear_admirals.york_pirates.Screen.Combat.Attacks;

import com.rear_admirals.york_pirates.Ship;

import java.util.concurrent.ThreadLocalRandom;

public class Attack {
	protected String name;
	protected String desc;
	protected int damage;
	protected int dmgMultiplier;
	protected double accMultiplier;
	protected boolean skipMoveStatus;
	protected boolean skipMove;
	protected int accPercent;

	// Generic constructor. Creates simple broadside attack.
	protected Attack() {
		name = "Broadside";
		desc = "Fire a broadside at your enemy.";
		dmgMultiplier = 3;
		accMultiplier = 1;
		skipMove = false;
		skipMoveStatus = skipMove;
	}

	// Custom constructor. Can be used to create any attack which applies a multiple of the attacker's damage
	// to the defender. Can also take a turn to charge and have custom accuracy.
	protected Attack(String name, String desc, int dmgMultiplier, double accMultiplier, boolean skipMove, int accPercent) {
		this.name = name;
		this.desc = desc;
		this.dmgMultiplier = dmgMultiplier;
		this.accMultiplier = accMultiplier;
		this.skipMove = skipMove;
		this.skipMoveStatus = skipMove;
		this.accPercent = accPercent;
	}

	// Old function used to check if an attack hits the enemy.
	@Deprecated
	protected boolean doesHit( int accuracy, int mult, int bound) {
		if ( accuracy * mult > Math.random() * bound) { return true; }
		else { return false; }
	}

	// New function used to check if an attack hits the enemy.
	protected boolean doesHit( int shipAcc, int accPercent) {
		int random = ThreadLocalRandom.current().nextInt(0, 101);
		if (accPercent * (1+(shipAcc-3)*0.02) > random){
			return true;
		}
		else{
			return false;
		}
	}

	// Function called to actually perform the attack.
	public int doAttack(Ship attacker, Ship defender) {
		if ( doesHit(attacker.getAccuracy(), accPercent) ) {
			damage = attacker.getAttack() * dmgMultiplier;
			defender.damage(damage);
			return damage;
		}
		return 0;
	}

	public String getName() { return name;	}
	public String getDesc() { return desc; }
	public boolean isSkipMove() {
		return skipMove;
	}
	public boolean isSkipMoveStatus() {
		return skipMoveStatus;
	}
	public void setSkipMoveStatus(boolean skipMoveStatus) {
		this.skipMoveStatus = skipMoveStatus;
	}

	// Attacks to be used in the game are defined here.
	public static Attack attackMain = new Attack("Broadside","Normal cannons. Fire a broadside at your enemy.",3,2,false,60);
	public static Attack attackSwivel = new Attack("Swivel","Lightweight cannons. High accuracy, low damage attack.",2,3,false,75);
	public static Attack attackBoard = new Attack("Board","Board enemy ship. Charges attack over a turn, medium - high damage and very high accuracy", 4,2,true,90);
}