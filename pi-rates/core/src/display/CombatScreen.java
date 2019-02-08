package display;

import banks.CoordBank;
import base.BaseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.compression.lzma.Base;
import combat.actors.CombatEnemy;
import combat.actors.CombatPlayer;
import combat.items.Weapon;
import combat.manager.CombatManager;
import combat.ship.Room;
import combat.ship.RoomFunction;
import combat.ship.Ship;
import game_manager.GameManager;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static banks.CoordBank.*;
import static combat.ship.RoomFunction.*;
import static other.Constants.COOLDOWN_TICKS_PER_TURN;
import static other.Constants.EASY_SCORE_MULTIPLIER;

public class CombatScreen extends BaseScreen {

    /**
     * Sets up all required managers to access Methods and Cause Combat
     */

    //NOT SURE WHY YOU WOULD EVER CREATE A NEW SHIP
//    private GameManager gameManager = new GameManager(null, null);
    private Ship playerShip;
    private CombatPlayer combatPlayer = game.getCombatPlayer();
    private Ship enemyShip;
    private CombatEnemy combatEnemy;
    private CombatManager combatManager = game.getCombatManager();

    private int randInt = pickRandom(3);

    /**
     * Used to Draw Assets on the Screen
     */
    private SpriteBatch batch = new SpriteBatch();
//    private Stage stage = new Stage();

    /**
     * Main style used for buttons
     */
    private BitmapFont buttonFont = new BitmapFont();
    private TextButton.TextButtonStyle textButtonStyle;

    private Skin skin = new Skin();

    /**
     * Tracks the Room and Weapon Selected
     */
    private Room roomSelected;
    private Weapon weaponSelected;

    /**
     * Groups used to control sets of Buttons and Track number Checked
     */
    private ButtonGroup weaponButtonGroup = new ButtonGroup();
    private ButtonGroup roomButtonGroup = new ButtonGroup();

    private List<TextButton> weaponButtons = new ArrayList<TextButton>();

    /**
     * Booleans used to track if the Game is over and won/lost, plus buttons to display the info
     */
    private Boolean gameOver = false;
    private Boolean gameWon;
    private TextButton youWin;
    private TextButton youLose;
    private int a = 0;

    /**
     * Buttons to Display if Hit or Missed, FeedbackTime measures how long this is displayed
     */
    private int hitFeedbackTime;
    private TextButton youHit;
    private TextButton youMissed;
    private TextButton enemyHit;
    private TextButton enemyMissed;

    /**
     * Used to set values to the same no. decimal places
     */
    private DecimalFormat df;

    /**
     * Initialising sounds used in combat
     */
    private Sound cannon_1;
    private Sound cannon_2;
    private Sound cannon_3;

    /**
     * Constructor requiring instance of GameManager (to switch screen) and is college battle
     */

    private Boolean isCollegeBattle;
    public CombatScreen(GameManager game, Boolean isCollegeBattle){
        super(game);
        playerShip = game.getPlayerShip();
        this.isCollegeBattle = isCollegeBattle;
        TextureAtlas buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.up = skin.getDrawable("buttonUp");
        textButtonStyle.down = skin.getDrawable("buttonDown");
        setUpTextures();
        buttonToMenu();
    }

    @Override
    public void update(float delta){ }

    @Override
    public void show() {
        //Sets the Appropriate ship for if a College or Standard battle are happening
        if (isCollegeBattle) {
            enemyShip = game.getCollegeShip();
            combatEnemy = game.getCombatCollege();
            setMusic(makeMusic("the-buccaneers-haul.mp3"));
        } else {
            enemyShip = game.getEnemyShip();
            combatEnemy = game.getCombatEnemy();
            setMusic(makeMusic("the-buccaneers-haul.mp3"));
        }

        cannon_1 = makeSound("cannon_1.mp3");
        cannon_2 = makeSound("cannon_2.mp3");
        cannon_3 = makeSound("cannon_3.mp3");

        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        TextureAtlas buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
        skin.addRegions(buttonAtlas);

        textButtonStyle.font = buttonFont;
        textButtonStyle.up = skin.getDrawable("buttonUp");
        textButtonStyle.down = skin.getDrawable("buttonDown");

        drawEnemyShip();
        drawWeaponButtons();
        drawEndButtons();
        drawHitMissButtons();
    }

    private Texture background;
    private TextureAtlas roomSpriteAtlas;
    private TextButton toMenu;
    private Texture hpBackground;
    private Texture hpBar;
    private BitmapFont indicatorFont;
    private BitmapFont collegeFont;

    private Texture constantineTexture;
    private Sprite constantineSprite;
    private Texture constantineShipBackground;

    private Texture langwithTexture;
    private Sprite langwithSprite;
    private Texture langwithShipBackground;

    private Texture goodrickeTexture;
    private Sprite goodrickeSprite;
    private Texture goodrickeShipBackground;

    private BitmapFont roomHealthFont;
    private BitmapFont cooldownFont;

    public void setUpTextures(){
        background = new Texture("battleBackground.png");
        roomSpriteAtlas = new TextureAtlas("roomSpriteSheet.txt");
        toMenu = new TextButton("To Menu", textButtonStyle);
        hpBar = new Texture("background.png");
        hpBackground = new Texture("disabledBackground.png");
        indicatorFont = new BitmapFont();
        collegeFont = new BitmapFont();

        constantineTexture = new Texture("Constantine.png");
        constantineSprite = new Sprite(constantineTexture);
        constantineShipBackground = new Texture("constantineShipBackground.png");

        langwithTexture = new Texture("langwidth.png");
        langwithSprite = new Sprite(langwithTexture);
        langwithShipBackground = new Texture("langwidthShipBackground.png");

        goodrickeTexture = new Texture("goodricke.png");
        goodrickeSprite = new Sprite(goodrickeTexture);
        goodrickeShipBackground = new Texture("goodrickeShipBackground.png");

        roomHealthFont = new BitmapFont();
        cooldownFont = new BitmapFont();


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();
        this.stage.act();
        if (!gamePaused) {
            Gdx.input.setInputProcessor(stage);
            batch.begin();

            drawBackground();
            drawFriendlyShip();

            drawHealthBar();
            drawIndicators();

            if (isCollegeBattle) {
                drawCollege();
            }

            drawRoomHPEffectivness();
            drawEnemyEffectivness();
            drawWeaponCooldowns();

            batch.end();

            //Checks if the Game is Over and Displays Message if You Won/Lost
            if (gameOver) {
                if (gameWon) {
                    youWin.setVisible(true);

                    if (isCollegeBattle) {
                        game.addPoints((int) (1000 * EASY_SCORE_MULTIPLIER));
                        game.addGold((int) (1000 * EASY_SCORE_MULTIPLIER));
                    } else {
                        game.addPoints((int) (100 * EASY_SCORE_MULTIPLIER));
                        game.addGold((int) (100 * EASY_SCORE_MULTIPLIER));
                    }
                } else {
                    youLose.setVisible(true);
                }

                //Waits 5 Loops to ensure Above messages render, Sleeps, then returns to menu
                if (a == 5) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {

                    }
                    changeScreen(new MenuScreen(game));
                }
                a++;
            }

            //Used to control how long the Hit/Miss messages are displayed, hides them after the time
            if (hitFeedbackTime == 25) {
                youHit.setVisible(false);
                youMissed.setVisible(false);
                enemyHit.setVisible(false);
                enemyMissed.setVisible(false);
            }
            hitFeedbackTime++;
        }
        else {
            pauseProcess();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("Toggle");
            toggleGamePaused();
        }

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
        cannon_1.dispose();
        cannon_2.dispose();
        cannon_3.dispose();
        batch.dispose();
    }

    /**
     * Picks a Random integer
     */
    private int pickRandom(int max){
        Random rand = new Random();
        return rand.nextInt(max);
    }

    /**
     * Checks which college was chosen and Draws the CollegeSprite, ShipBackground and ShipText
     */
    private void drawCollege(){
//        collegeFont.getData().setScale(2);
        switch (randInt) {
            case 0:
                batch.draw(constantineSprite, (viewwidth*375)/1024, (viewheight*750)/1024, viewwidth/4, viewheight/4);
                collegeFont.draw(batch, "Constantine Defender", (viewwidth*690)/1024, (viewheight*850)/1024);
                batch.draw(constantineShipBackground,(viewwidth*636)/1024,(viewheight*207)/1024);
                break;
            case 1:

                batch.draw(langwithSprite, (viewwidth*375)/1024, (viewheight*750)/1024, viewwidth/4, viewwidth/4);
                collegeFont.draw(batch, "Langwith Defender", (viewwidth*690)/1024, (viewheight*850)/1024);


                batch.draw(langwithShipBackground,(viewwidth*636)/1024,(viewheight*207)/1024);
                break;
            case 2:
                batch.draw(goodrickeSprite, (viewwidth*375)/1024, (viewheight*750)/1024, viewwidth/4, viewwidth/4);
                collegeFont.draw(batch, "Goodricke Defender", (viewwidth*690)/1024, (viewheight*850)/1024);

                batch.draw(goodrickeShipBackground,(viewwidth*636)/1024,(viewheight*207)/1024);
                break;
        }
    }

    /**
     * Creates and Draws the Combat Background
     */
    private void drawBackground() {
        batch.draw(background, 0, 0);
    }

    /**
     * Draws the friendly ship from room textures and constant coordinates
     */
    private void drawFriendlyShip(){

        Image friendlyCrewQuaters = new Image(roomSpriteAtlas.findRegion("crewQuaters"));
        friendlyCrewQuaters.setPosition(CoordBank.FRIENDLY_CREWQUATERS.getX(),CoordBank.FRIENDLY_CREWQUATERS.getY());
        stage.addActor(friendlyCrewQuaters);

        Image friendlyEmptyRoom1 = new Image(roomSpriteAtlas.createSprite("EmptyRoom"));
        friendlyEmptyRoom1.setPosition(CoordBank.FRIENDLY_EMPTYROOM1.getX(),CoordBank.FRIENDLY_EMPTYROOM1.getY());
        stage.addActor(friendlyEmptyRoom1);

        Image friendlyCrowsNest = new Image(roomSpriteAtlas.createSprite("crowsNest"));
        friendlyCrowsNest.setPosition(CoordBank.FRIENDLY_CROWSNEST.getX(),CoordBank.FRIENDLY_CROWSNEST.getY());
        stage.addActor(friendlyCrowsNest);

        Image friendlyGunDeck = new Image(roomSpriteAtlas.createSprite("gunDeck"));
        friendlyGunDeck.setPosition(CoordBank.FRIENDLY_GUNDECK.getX(),CoordBank.FRIENDLY_GUNDECK.getY());
        stage.addActor(friendlyGunDeck);

        Image friendlyEmptyRoom2 = new Image(roomSpriteAtlas.createSprite("EmptyRoom"));
        friendlyEmptyRoom2.setPosition(CoordBank.FRIENDLY_EMPTYROOM2.getX(),CoordBank.FRIENDLY_EMPTYROOM2.getY());
        stage.addActor(friendlyEmptyRoom2);

        Image friendlyHelm = new Image(roomSpriteAtlas.createSprite("helm"));
        friendlyHelm.setPosition(CoordBank.FRIENDLY_HELM.getX(),CoordBank.FRIENDLY_HELM.getY());
        stage.addActor(friendlyHelm);

        Image friendlyEmptyRoom3 = new Image(roomSpriteAtlas.createSprite("EmptyRoom"));
        friendlyEmptyRoom3.setPosition(CoordBank.FRIENDLY_EMPTYROOM3.getX(),CoordBank.FRIENDLY_EMPTYROOM3.getY());
        stage.addActor(friendlyEmptyRoom3);

        Image friendlyEmptyRoom4 = new Image(roomSpriteAtlas.createSprite("EmptyRoom"));
        friendlyEmptyRoom4.setPosition(CoordBank.FRIENDLY_EMPTYROOM4.getX(),CoordBank.FRIENDLY_EMPTYROOM4.getY());
        stage.addActor(friendlyEmptyRoom4);
    }

    /**
     * Draws Hp bars for both ships
     */
    private void drawHealthBar() {


        double defaultWidth = 320;
        int playerWidth = (int)(defaultWidth * ((double)playerShip.getHullHP() / (double)playerShip.getBaseHullHP()));
        int enemyWidth = (int)(defaultWidth * ((double)enemyShip.getHullHP() / (double)enemyShip.getBaseHullHP()));

        batch.draw(hpBackground,(viewwidth*25)/1024, (viewheight*900)/1024, 320, 16);
        batch.draw(hpBar,(viewwidth*25)/1024, (viewheight*900)/1024, playerWidth, 16);

        batch.draw(hpBackground, (viewwidth*675)/1024, (viewheight*900)/1024, 320, 16);
        batch.draw(hpBar,(viewwidth*675)/1024, (viewheight*900)/1024, enemyWidth, 16);
    }

    /**
     * Draws resource indicators for player
     */
    private void drawIndicators(){

        indicatorFont.setColor(1,1,1,1);

        indicatorFont.draw(batch, "Score: " + game.getPoints(), (viewwidth*25)/1024, (viewheight*890)/1024);
        indicatorFont.draw(batch, "Gold: " + game.getGold(), (viewwidth*110)/1024, (viewheight*890)/1024);
        indicatorFont.draw(batch, "Food: " + game.getFood(), (viewwidth*195)/1024, (viewheight*890)/1024);
        indicatorFont.draw(batch, "Crew: " + playerShip.getCrew(), (viewwidth*280)/1024, (viewheight*890)/1024);
    }

    /**
     * Draws the Button returning to menu, taking the style button
     */
    public void buttonToMenu(){
        toMenu.setPosition((viewwidth*880)/1024, (viewheight*980)/1024);
        toMenu.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button Pressed");
                changeScreen(new MenuScreen(game));
            }
        });
        stage.addActor(toMenu);
    }
    /**
     * Draws the Enemy Ship of buttonsFrom Textures using Constant Coordinates, adds listeners which track the selected room
     */
    private void drawEnemyShip(){
        TextureAtlas roomButtonAtlas = new TextureAtlas("roomSpriteSheet.txt");
        Skin roomButtonSkin = new Skin();
        roomButtonSkin.addRegions(roomButtonAtlas);

        ImageButton.ImageButtonStyle crewQuatersStyle = new ImageButton.ImageButtonStyle(), crowsNestStyle = new ImageButton.ImageButtonStyle(), gunDeckStyle = new ImageButton.ImageButtonStyle(), helmStyle = new ImageButton.ImageButtonStyle(), emptyStyle = new ImageButton.ImageButtonStyle();
        crewQuatersStyle.up = roomButtonSkin.getDrawable("crewQuaters");
        crewQuatersStyle.checked = roomButtonSkin.getDrawable("crewQuatersTargetted");
        crowsNestStyle.up = roomButtonSkin.getDrawable("crowsNest");
        crowsNestStyle.checked = roomButtonSkin.getDrawable("crowsNestTargetted");
        gunDeckStyle.up = roomButtonSkin.getDrawable("gunDeck");
        gunDeckStyle.checked = roomButtonSkin.getDrawable("gunDeckTargetted");
        helmStyle.up = roomButtonSkin.getDrawable("helm");
        helmStyle.checked = roomButtonSkin.getDrawable("helmTargetted");
        emptyStyle.up = roomButtonSkin.getDrawable("EmptyRoom");
        emptyStyle.checked = roomButtonSkin.getDrawable("EmptyRoomTargetted");

        ImageButton enemyCrewQuarters = new ImageButton(crewQuatersStyle), enemyCrowsNest = new ImageButton(crowsNestStyle), enemyGunDeck = new ImageButton(gunDeckStyle), enemyHelm = new ImageButton(helmStyle),
                enemyEmpty1 = new ImageButton(emptyStyle), enemyEmpty2 = new ImageButton(emptyStyle), enemyEmpty3 = new ImageButton(emptyStyle), enemyEmpty4 = new ImageButton(emptyStyle);


        roomButtonGroup.add(enemyCrewQuarters, enemyCrowsNest, enemyGunDeck, enemyHelm, enemyEmpty1, enemyEmpty2, enemyEmpty3, enemyEmpty4);
        roomButtonGroup.setMaxCheckCount(1);
        roomButtonGroup.uncheckAll();

        enemyCrewQuarters.setPosition(ENEMY_CREWQUATERS.getX(), ENEMY_CREWQUATERS.getY());
        enemyEmpty1.setPosition(ENEMY_EMPTYROOM1.getX(), ENEMY_EMPTYROOM1.getY());
        enemyCrowsNest.setPosition(ENEMY_CROWSNEST.getX(), ENEMY_CROWSNEST.getY());
        enemyEmpty2.setPosition(ENEMY_EMPTYROOM2.getX(), ENEMY_EMPTYROOM2.getY());
        enemyGunDeck.setPosition(ENEMY_GUNDECK.getX(), ENEMY_GUNDECK.getY());
        enemyEmpty3.setPosition(ENEMY_EMPTYROOM3.getX(), ENEMY_EMPTYROOM3.getY());
        enemyEmpty4.setPosition(ENEMY_EMPTYROOM4.getX(), ENEMY_EMPTYROOM4.getY());
        enemyHelm.setPosition(ENEMY_HELM.getX(), ENEMY_HELM.getY());

        enemyCrewQuarters.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = enemyShip.getRoom(CREW_QUARTERS);
                return true;
            }
        });
        enemyCrowsNest.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = enemyShip.getRoom(CROWS_NEST);
                return true;
            }
        });
        enemyGunDeck.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = enemyShip.getRoom(GUN_DECK);
                return true;
            }
        });
        enemyHelm.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = enemyShip.getRoom(HELM);
                return true;
            }
        });
        enemyEmpty1.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = enemyShip.getRoom(NON_FUNCTIONAL);
                return true;
            }
        });
        enemyEmpty2.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = enemyShip.getRoom(NON_FUNCTIONAL);
                return true;
            }
        });
        enemyEmpty3.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = enemyShip.getRoom(NON_FUNCTIONAL);
                return true;
            }
        });
        enemyEmpty4.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                roomSelected = enemyShip.getRoom(NON_FUNCTIONAL);
                return true;
            }
        });

        stage.addActor(enemyCrewQuarters);
        stage.addActor(enemyCrowsNest);
        stage.addActor(enemyGunDeck);
        stage.addActor(enemyHelm);
        stage.addActor(enemyEmpty1);
        stage.addActor(enemyEmpty2);
        stage.addActor(enemyEmpty3);
        stage.addActor(enemyEmpty4);
    }

    /**
     * Draws buttons for Each weapon the user has which can be pressed to fire
     * Also adds listeners to these controlling the weaponSelected by the user
     * This also contains the Fire button, when pressed this runs the main combat loop
     */
    private void drawWeaponButtons() {
        TextureAtlas weaponButtonAtlas = new TextureAtlas("weaponButtonSpriteSheet.txt");
        Skin weaponButtonSkin = new Skin();
        weaponButtonSkin.addRegions(weaponButtonAtlas);

        final TextButton.TextButtonStyle weaponButtonStyle = new TextButton.TextButtonStyle();
        weaponButtonStyle.up = weaponButtonSkin.getDrawable("weaponButtonUp");
        weaponButtonStyle.down = weaponButtonSkin.getDrawable("weaponButtonDown");
        weaponButtonStyle.checked = weaponButtonSkin.getDrawable("weaponButtonChecked");
        weaponButtonStyle.font = new BitmapFont();

        final List<Weapon> playerWeapons = playerShip.getWeapons();

        weaponButtonGroup.setMaxCheckCount(1);

        int i = 0;
        while (i < 4) {
            try {
                weaponButtons.add(new TextButton(playerWeapons.get(i).getName(), weaponButtonStyle));
            } catch (IndexOutOfBoundsException e) {
                weaponButtons.add(new TextButton("Empty Slot", weaponButtonStyle));
                weaponButtons.get(i).setDisabled(true);
            }
            weaponButtons.get(i).setTransform(true);
//            weaponButtons.get(i).setScale(1, 1.5f);

            weaponButtonGroup.add(weaponButtons.get(i));

            weaponButtons.get(i).setPosition(( viewwidth*(50 + (125 * i)))/1024, (viewheight*50)/1024);
            stage.addActor(weaponButtons.get(i));
            i++;
        }


        final TextButton fire = new TextButton("Fire", weaponButtonStyle);
        fire.setTransform(true);
//        fire.setScale(1,1.5f);
        fire.setPosition((viewwidth*575)/1024,(viewheight*50)/1024);
        stage.addActor(fire);

        weaponButtons.get(0).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    weaponSelected = playerWeapons.get(0);
                } catch (IndexOutOfBoundsException e) {
                }
                return true;
            }
        });
        weaponButtons.get(1).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    weaponSelected = playerWeapons.get(1);
                } catch (IndexOutOfBoundsException e) {
                }
                return true;
            }
        });
        weaponButtons.get(2).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    weaponSelected = playerWeapons.get(2);
                } catch (IndexOutOfBoundsException e) {
                }
                return true;
            }
        });
        weaponButtons.get(3).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    weaponSelected = playerWeapons.get(3);
                } catch (IndexOutOfBoundsException e) {
                }
                return true;
            }
        });

        fire.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (weaponSelected instanceof Weapon && roomSelected instanceof  Room) {
                    if (weaponSelected.getCurrentCooldown() > 0) {
                        for (Weapon weapon : playerShip.getWeapons()) {
                            weapon.decrementCooldown(COOLDOWN_TICKS_PER_TURN);
                        }
                        playerShip.combatRepair();
                    } else {
                        //Runs the players Combat Loop
                        combatManager.combatLoop(combatPlayer, combatEnemy, roomSelected, weaponSelected);
                        playSound(cannon_1);
                        //Displays if the Player Hit or Missed
                        if (combatManager.getShotHit()){
                            youHit.setVisible(true);
                        } else {
                            youMissed.setVisible(true);
                        }
                    }

                    weaponButtonGroup.uncheckAll();
                    roomButtonGroup.uncheckAll();

                    //Runs enemy Combat Loop
                    if (combatEnemy.hasWepaonsReady()){
                        combatManager.enemyCombatLoop(combatEnemy, combatPlayer);
                        //Displays if Enemy Hit or Missed
                        if (combatManager.getShotHit()){
                            enemyHit.setVisible(true);
                        } else {
                            enemyMissed.setVisible(true);
                        }
                    } else {
                        for (Weapon weapon : enemyShip.getWeapons()) {
                            weapon.decrementCooldown(COOLDOWN_TICKS_PER_TURN);
                        }
                        enemyShip.combatRepair();
                    }

                    if (combatManager.checkFightEnd() && playerShip.getHullHP() <= 0) {
                        gameOver = true;
                        gameWon = false;
                    } else if (combatManager.checkFightEnd() && enemyShip.getHullHP() <= 0) {
                        gameOver = true;
                        gameWon = true;
                    }

                    fire.toggle();
                    hitFeedbackTime = 0;

                }
                return true;
            }
        });

        weaponButtonGroup.uncheckAll();
    }

    /**
     * Draws Text displaying weapon cooldowns to the user
     */
    private void drawWeaponCooldowns() {
        cooldownFont.getData().setScale(0.9f);

        int i = 0;
        for  (Weapon weapon : playerShip.getWeapons())
        {
            if (weapon.getCurrentCooldown() <= 0){
                cooldownFont.draw(batch, "Ready!", ( viewwidth*(55 + (125 * i)))/1024 ,(viewheight*115)/1024);
                weaponButtons.get(i).setTouchable(Touchable.enabled);
            } else {
                cooldownFont.draw(batch, "Cooldown: " + (weapon.getCurrentCooldown() / COOLDOWN_TICKS_PER_TURN) + "Turns", ( viewwidth*(55 + (125 * i)))/1024,(viewheight*115)/1024);
                weaponButtons.get(i).setTouchable(Touchable.disabled);
            }
            i++;
        }
    }

    /**
     * Draws the Player Room HP and a Stats display of Room Effectivness
     */
    private void drawRoomHPEffectivness(){

        roomHealthFont.setColor(1,1,1,1);

        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(CREW_QUARTERS).getHp(),FRIENDLY_CREWQUATERS.getX() + 10,FRIENDLY_CREWQUATERS.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(HELM).getHp(),FRIENDLY_HELM.getX() + 10,FRIENDLY_HELM.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(CROWS_NEST).getHp(),FRIENDLY_CROWSNEST.getX() + 10,FRIENDLY_CROWSNEST.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(GUN_DECK).getHp(),FRIENDLY_GUNDECK.getX() + 10,FRIENDLY_GUNDECK.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(NON_FUNCTIONAL).getHp(),FRIENDLY_EMPTYROOM1.getX() + 10,FRIENDLY_EMPTYROOM1.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(NON_FUNCTIONAL).getHp(),FRIENDLY_EMPTYROOM2.getX() + 10,FRIENDLY_EMPTYROOM2.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(NON_FUNCTIONAL).getHp(),FRIENDLY_EMPTYROOM3.getX() + 10,FRIENDLY_EMPTYROOM3.getY() + 22);
        roomHealthFont.draw(batch, "HP:" + playerShip.getRoom(NON_FUNCTIONAL).getHp(),FRIENDLY_EMPTYROOM4.getX() + 10,FRIENDLY_EMPTYROOM4.getY() + 22);

        roomHealthFont.draw(batch, "Ship Functionality",(viewwidth*100)/1024,(viewheight*250)/1024);
        roomHealthFont.draw(batch, "Evade: " + df.format(playerShip.calculateShipEvade() * 100) + "%",(viewwidth*100)/1024,(viewheight*230)/1024);
        roomHealthFont.draw(batch, "Accuracy:" + df.format(playerShip.calculateShipAccuracy() * 100) + "%",(viewwidth*100)/1024,(viewheight*210)/1024);
        roomHealthFont.draw(batch, "Weapon Effectivness: " + df.format(playerShip.getRoom(RoomFunction.GUN_DECK).getMultiplier() * 100) + "%",(viewwidth*100)/1024,(viewheight*190)/1024);
        roomHealthFont.draw(batch, "Repair % Per Turn: " + df.format(playerShip.calculateRepair()) + "%",(viewwidth*100)/1024,(viewheight*170)/1024);
    }

    /**
     * Draws the Enemy Room Effectivness Stats
     */
    private void drawEnemyEffectivness() {
        roomHealthFont.setColor(1,1,1,1);

        roomHealthFont.draw(batch, "Ship Functionality",(viewwidth*700)/1024,(viewheight*250)/1024);
        roomHealthFont.draw(batch, "Evade: " + df.format(enemyShip.calculateShipEvade() * 100) + "%",(viewwidth*700)/1024,(viewheight*230)/1024);
        roomHealthFont.draw(batch, "Accuracy:" + df.format(enemyShip.calculateShipAccuracy() * 100) + "%",(viewwidth*700)/1024,(viewheight*210)/1024);
        roomHealthFont.draw(batch, "Weapon Effectivness: " + df.format(enemyShip.getRoom(RoomFunction.GUN_DECK).getMultiplier() * 100) + "%",(viewwidth*700)/1024,(viewheight*190)/1024);
        roomHealthFont.draw(batch, "Repair % Per Turn: " + df.format(enemyShip.calculateRepair()) + "%",(viewwidth*700)/1024,(viewheight*170)/1024);
    }

    /**
     * Draws buttons which display if you Win or Lose
     */
    private void drawEndButtons(){
        youWin = new TextButton("You win!", textButtonStyle);
        youWin.setTransform(true);
//        youWin.setScale(3);
        youWin.setPosition((viewwidth/2)-175 ,(viewheight/2));
        stage.addActor(youWin);
        youWin.setVisible(false);

        youLose = new TextButton("You Lose :(", textButtonStyle);
        youLose.setTransform(true);
//        youLose.setScale(3);
        youLose.setPosition((viewwidth/2)-175,(viewheight/2));
        stage.addActor(youLose);
        youLose.setVisible(false);
    }

    /**
     * Draws buttons which display if the user/Enemy hit or missed
     */
    private void drawHitMissButtons(){
        youHit = new TextButton("You hit!", textButtonStyle);
        youHit.setTransform(true);
//        youHit.setScale(2);
        youHit.setPosition((viewwidth*700)/1024,(viewheight*512)/1024);
        stage.addActor(youHit);
        youHit.setVisible(false);

        youMissed = new TextButton("You Missed!", textButtonStyle);
        youMissed.setTransform(true);
//        youMissed.setScale(2);
        youMissed.setPosition((viewwidth*700)/1024,(viewheight*512)/1024);
        stage.addActor(youMissed);
        youMissed.setVisible(false);

        enemyHit = new TextButton("Enemy hit!", textButtonStyle);
        enemyHit.setTransform(true);
//        enemyHit.setScale(2);
        enemyHit.setPosition((viewwidth*700)/1024,(viewheight*512)/1024);
        stage.addActor(enemyHit);
        enemyHit.setVisible(false);

        enemyMissed = new TextButton("Enemy Missed!", textButtonStyle);
        enemyMissed.setTransform(true);
//        enemyMissed.setScale(2);
        enemyMissed.setPosition((viewwidth*700)/1024,(viewheight*512)/1024);
        stage.addActor(enemyMissed);
        enemyMissed.setVisible(false);
    }
}
