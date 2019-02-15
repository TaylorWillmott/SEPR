package game_manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import combat.actors.CombatEnemy;
import combat.actors.CombatPlayer;
import combat.manager.CombatManager;
import combat.ship.Ship;
import location.Department;
import other.Difficulty;
import display.*;

import static banks.RoomUpgradeSetBank.COMP_SCI_UPGRADES;
import static banks.RoomUpgradeSetBank.LMB_UPGRADES;
import static banks.RoomUpgradeSetBank.PHYS_UPGRADES;
import static banks.ShipBank.COLLEGE_SHIP;
import static banks.ShipBank.STARTER_SHIP;
import static banks.WeaponSetBank.COMP_SCI_WEPS;
import static banks.WeaponSetBank.LMB_WEPS;
import static banks.WeaponSetBank.PHYS_WEPS;
import static com.badlogic.gdx.Application.LOG_DEBUG;
import static com.badlogic.gdx.Application.LOG_INFO;
import static com.badlogic.gdx.Application.LOG_ERROR;
import static other.Constants.STARTING_FOOD;
import static other.Constants.STARTING_GOLD;

/**
 * Controls the overall process of the game, handing control to various sub-managers (eg combat manager) as necessary.
 * It also stores the information about the game which will be needed in lots of places, eg the amount of gold the
 * player has or the points.
 */
public class GameManager extends Game {
    /**
     * Currency of the game
     */
    private int gold;
    /**
     * A resource tied to crew and travelling. As you travel you use up food. The more crew you have, the faster you use
     * it. This stops you ending up with a massive crew and means that you cant stay at sea for ever, progressing the
     * game.
     */
    private int food;
    /**
     * Points are accumulated by killing ships etc. They go toward recording the high scores.
     */
    private int points;
    /**
     * The name of the current player for saves and hi-scores
     */
    private String playerName;

    /**
     * The difficulty that the player is playing on.
     */
    private Difficulty difficulty;

    /**
     * Instance of LIBGDX Game used to allow setScreen to be used
     */
    // DONT NEED THIS THIS CLASS EXTENDS GAME
//    private Game game;

    /**
     * Creates Instances of enemyShip, playerShip and their Actors to be used in the game
     */
    private Ship playerShip = STARTER_SHIP.getShip();
    private CombatPlayer combatPlayer = new CombatPlayer(playerShip);

    private Ship enemyShip = STARTER_SHIP.getShip();
    private CombatEnemy combatEnemy = new CombatEnemy(enemyShip);

    private Ship collegeShip = COLLEGE_SHIP.getShip();
    private CombatEnemy combatCollege = new CombatEnemy(collegeShip);

    private CombatManager combatManager = new CombatManager(combatPlayer, combatEnemy);

    /**
     * Variables to manage the global volume of sounds/music in the game
     */
    private float masterVolume;
    private float soundVolume;
    private float musicVolume;

    /**
     * Different departments in the game
     */
    public static Department ComputerScience;
    public static Department LawAndManagement;
    public static Department Physics;

    public CombatManager getCombatManager() { return combatManager; }

    public int getGold() {
        return gold;
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    @Deprecated // This function is deprecated. Use payGold instead where possible.
    public void deductGold(int amount) {
        this.gold -= amount;
        if (gold < 0) {
            gold = 0;
        }
    }

    /**
     * Checks if the player can afford to pay the amount given. Charges them and returns true if they can, just returns false if they cannot.
     * @param amount
     * @return
     */
    public boolean payGold(int amount) {
        if (gold < amount) { return false; }
        gold = gold - amount;
        return true;
    }

    public int getFood() {
        return food;
    }

    public void addFood(int amount) {
        this.food += amount;
    }

    public void deductFood(int amount) {
        food -= amount;
        if (food < 0) {
            food = 0;
        }
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int amount) {
        this.points += amount;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Ship getPlayerShip() {
        return playerShip;
    }

    public Ship getEnemyShip() {
        return enemyShip;
    }

    public Ship getCollegeShip() { return collegeShip; }

    public CombatPlayer getCombatPlayer(){return combatPlayer; }

    public CombatEnemy getCombatEnemy() {return combatEnemy; }

    public CombatEnemy getCombatCollege() { return combatCollege; }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Skin getSkin() {
        return skin;
    }

    private Skin skin;

    public void setMasterVolume(float volume) {
        if (volume < 0) { volume = 0; }
        else if (volume > 1) {volume = 1;}
        masterVolume = volume;
    }

    public void setSoundVolume(float volume) {
        if (volume < 0) { volume = 0; }
        else if (volume > 1) {volume = 1;}
        soundVolume = volume;
    }

    public void setMusicVolume(float volume) {
        if (volume < 0) { volume = 0; }
        else if (volume > 1) {volume = 1;}
        musicVolume = volume;
    }

    public float getMasterValue() { return masterVolume; }

    public float getMusicVolume() { return musicVolume * masterVolume; }

    public float getSoundVolume() { return soundVolume * masterVolume; }

    public float getMusicValue() { return musicVolume; }

    public float getSoundValue() { return soundVolume; }

    public GameManager(String playerName, Difficulty difficulty) {
        this.playerName = playerName;
        this.difficulty = difficulty;
        this.gold = STARTING_GOLD;
        this.food = STARTING_FOOD;
        this.points = 0;
        this.masterVolume = 0.5f;
        this.soundVolume = 0.5f;
        this.musicVolume = 0.5f;

        this.ComputerScience = new Department(COMP_SCI_WEPS.getWeaponList(), COMP_SCI_UPGRADES.getRoomUpgradeList(), this);
        this.LawAndManagement = new Department(LMB_WEPS.getWeaponList(), LMB_UPGRADES.getRoomUpgradeList(), this);
        this.Physics = new Department(PHYS_WEPS.getWeaponList(), PHYS_UPGRADES.getRoomUpgradeList(), this);
    }

    /**
     * Creates an Instance of Screen and all the different Screens used
     */
    @Override
    public void create() { //Called when the application is
        Gdx.app.setLogLevel(LOG_DEBUG); // Sets level of logs to display.
        Gdx.app.debug("Game DEBUG","Initialising Application");
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        MenuScreen menuScreen =  new MenuScreen(this);
        this.setScreen(menuScreen);
    }

    @Override
    public void render() { //Called when the Application should render, Called continuously
        super.render();
    }

    @Override
    public void dispose() { //Called when the application is destroyed, resources must be disposed of from Memory
    }


}
