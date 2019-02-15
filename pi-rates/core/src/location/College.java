package location;

import combat.actors.CombatEnemy;
import combat.actors.CombatPlayer;
import combat.manager.CombatManager;
import combat.ship.Ship;

import java.util.ArrayList;


public class College {
    String name;
    private ArrayList<College> ally;
    private boolean bossDead;

    public College(String name) {
        this.name = name;
        this.ally = new ArrayList<College>();
        this.ally.add(this);
        this.bossDead = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<College> getAlly() { return ally; }
    public void addAlly(College newAlly){
        ally.add(newAlly);
    }

    public boolean isBossDead() {
        return bossDead;
    }
    public void setBossDead(boolean bossDead) {
        this.bossDead = bossDead;
    }

    public static College Derwent = new College("Derwent");
    public static College Vanbrugh = new College("Vanbrugh");
    public static College James = new College("James");
    public static College Alcuin = new College("Alcuin");
    public static College Langwith = new College("Langwith");
    public static College Goodricke = new College("Goodricke");
}
