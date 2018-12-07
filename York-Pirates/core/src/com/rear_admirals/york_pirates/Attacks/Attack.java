package com.rear_admirals.york_pirates.Attacks;

import com.rear_admirals.york_pirates.Ship;

public class Attack {
	protected String name;
	protected String desc;
	protected int damage;
	protected int dmgMultiplier;
	protected double accMultiplier;
	protected boolean skipMoveStatus;
	protected boolean skipMove;

	protected Attack() {
		name = "Broadside";
		desc = "Fire a broadside at your enemy.";
		dmgMultiplier = 3;
		accMultiplier = 1;
		skipMove = true;
		skipMoveStatus = skipMove;
	}

	protected Attack(String name, String desc, int dmgMultiplier, double accMultiplier, boolean skipMove) {
		this.name = name;
		this.desc = desc;
		this.dmgMultiplier = dmgMultiplier;
		this.accMultiplier = accMultiplier;
		this.skipMove = skipMove;
		this.skipMoveStatus = skipMove;
	}

	protected boolean doesHit( int accuracy, int mult, int bound) {
		if ( accuracy * mult > Math.random() * bound ) { return true; }
		else { return false; }
	}

	public int doAttack(Ship attacker, Ship defender) {
		if ( doesHit(attacker.getAccuracy(), (int) (10 * accMultiplier), 100) ) {
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

//	public Attack attackMain = new Attack();
	public static Attack attackMain = new Attack("Broadside","Fire a broadside at your enemy.",3,1,false);
	public static Attack attackGrape = new Attack("Grape Shot","Fire a bundle of smaller cannonballs at the enemy.",1,0.5,false);
	public static Attack attackSwivel = new Attack("Swivel","High accuracy, low damage attack.",2,4,false);

}