package com.rear_admirals.york_pirates.Attacks;

import com.rear_admirals.york_pirates.Ship;

public class Board extends Attack {

    private String name = "NAME"; // Formatted name of the attack. This will be displayed in-game.
    private String desc = "DESCRIPTION"; // Description of the attack. This will be displayed in-game so try not to be too technical.

    protected Board() {
        name = "Board";
        desc = "DESC";
    }

    @Override
    public boolean doAttack(Ship attacker, Ship defender) {

        if (attacker.getAccuracy() > 0) { // Accuracy Check Goes Here
            defender.damage(attacker.getAttack()*2);
            attacker.damage(defender.getDefence());
            return true; // Return true if the attack was successful.
        }
        return false; // Return false if it was not successful.
    }

    public static final Board attackBoard = new Board();
}
