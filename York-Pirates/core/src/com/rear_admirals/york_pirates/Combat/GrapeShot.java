package com.rear_admirals.york_pirates.Combat;

import com.rear_admirals.york_pirates.Attacks.Attack;
import com.rear_admirals.york_pirates.Ship;

public class GrapeShot extends Attack {
    public GrapeShot(String name, String desc, int dmgMultiplier, double accMultiplier, boolean skipMove) {
        super(name, desc, dmgMultiplier, accMultiplier, skipMove);
    }

    @Override
    public int doAttack(Ship attacker, Ship defender) {
        damage = 0;
        for (int i = 0; i < 4; i++) { // Fires 4 shots.
            if (doesHit(attacker.getAccuracy(), (int) (5 * accMultiplier), 100)) { // Multiplier is halved, so each shot is half as likely to hit.
                damage += attacker.getAttack() * dmgMultiplier; // Landed shots do half as much damage as a regular shot.
                System.out.println("GRAPE HIT");
            }
            else{
                System.out.println("GRAPE MISSED");
            }
        }
        defender.damage(damage);
        return damage;
    }

    public static Attack attackGrape = new GrapeShot("Grape Shot","Fire a bundle of smaller cannonballs at the enemy.",1,1,false);

}

