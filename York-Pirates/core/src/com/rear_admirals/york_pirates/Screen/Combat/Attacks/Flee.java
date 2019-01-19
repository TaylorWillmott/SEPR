package com.rear_admirals.york_pirates.Screen.Combat.Attacks;

import com.rear_admirals.york_pirates.Ship;

import java.util.concurrent.ThreadLocalRandom;

public class Flee extends Attack {

    protected Flee() {
        name = "FLEE";
        desc = "Attempt to escape enemy.";
    }

    // Flee requires a custom doAttack function and as such has its own class.
    @Override
    public int doAttack(Ship attacker, Ship defender) {
        int fleeSuccess = ThreadLocalRandom.current().nextInt(0, 101);
        if (fleeSuccess >= 30) {
            return 1;
        } else {
            return 0;
        }
    }

    public static final Flee attackFlee = new Flee();

}
