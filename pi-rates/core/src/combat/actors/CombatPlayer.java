package combat.actors;

import combat.items.Weapon;
import combat.ship.Ship;

import static other.Constants.COOLDOWN_TICKS_PER_TURN;

/**
 * Interacts with the player to get their move
 */
public class CombatPlayer extends CombatActor implements java.io.Serializable {
    public CombatPlayer(Ship ship) {
        super(ship);
    }


    /**
     * Takes a damage report telling you what has been attacked, decrements cooldowns of weapons, repairs the ship
     * and fires on enemy by returning an attack report.
     */
    public void takeTurn(Weapon weaponSelected) {
        for (Weapon weapon : getShip().getWeapons()) {
            weapon.decrementCooldown(COOLDOWN_TICKS_PER_TURN);
        }

        weaponSelected.fire();

        getShip().combatRepair();
    }
}
