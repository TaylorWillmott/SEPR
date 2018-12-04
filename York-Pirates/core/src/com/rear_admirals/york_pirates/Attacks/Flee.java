package com.rear_admirals.york_pirates.Attacks;

import com.rear_admirals.york_pirates.Ship;

public class Flee extends Attack {

    protected Flee() {
        name = "FLEE";
        desc = "Attempt to escape enemy.";
    }

    @Override
    public boolean doAttack(Ship attacker, Ship defender) {
        //TODO Fill in Flee attacks
//        if (){
//        }
        return false; // Return false if it was not successful.
    }

    public static final Flee attackFlee = new Flee();

}
