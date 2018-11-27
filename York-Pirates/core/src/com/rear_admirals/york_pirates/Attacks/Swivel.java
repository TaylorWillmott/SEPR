package com.rear_admirals.york_pirates.Attacks;

import com.rear_admirals.york_pirates.Ship;

public class Swivel extends Attack {
    private String name = "Swivel"; // Formatted name of the attack. This will be displayed in-game.
    private String desc = "High accuracy, low damage attack."; // Description of the attack. This will be displayed in-game so try not to be too technical.

    @Override
    public boolean doAttack(Ship attacker, Ship defender) {
        // This is the main function for this attack. Make sure you leave the name as doAttack (@Override tag helps).
        // It takes the attacking and defending ships as parameters, get any relevant stats from there.

        if (attacker.getAccuracy() > 0) { // Accuracy Check Goes Here
            defender.damage(attacker.getAttack()*2);
            attacker.damage(defender.getDefence());
            return true; // Return true if the attack was successful.
        }
        return false; // Return false if it was not successful.
    }

    public static Attack attackTemplate = new Template();
}
