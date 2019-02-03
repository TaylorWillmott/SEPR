package display;

import banks.CoordBank;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.Ship;
import game_manager.GameManager;
import location.Department;
import other.Resource;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static banks.RoomUpgradeSetBank.COMP_SCI_UPGRADES;
import static banks.RoomUpgradeSetBank.LMB_UPGRADES;
import static banks.WeaponSetBank.COMP_SCI_WEPS;
import static banks.WeaponSetBank.LMB_WEPS;
import static other.Constants.*;

public class DepartmentScreen extends BaseScreen {
    /**
     * Constructor for DepartmentScreen requiring game to switch screen
     */
    public DepartmentScreen(GameManager game){
        super(game);
        buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
        skin.addRegions(buttonAtlas);

        textButtonStyle.font = buttonFont;
        textButtonStyle.up = skin.getDrawable("buttonUp");
        textButtonStyle.down = skin.getDrawable("buttonDown");
        setUpTextures();
    }

    /**
     * Sets up gameManager to retrieve values and the playerShip
     */
    private GameManager gameManager = new GameManager(null, null);
    private Ship playerShip = gameManager.getPlayerShip();

    /**
     * Used to Draw Assets on the Screen
     */
    private SpriteBatch batch = new SpriteBatch();
    private Stage stage = new Stage();

    /**
     * Used to set values to the same no. decimal places
     */
    private DecimalFormat df;

    /**
     * Used to pick a random department to display
     */
    private int randInt = pickRandom(2);
    private Department department = assignDepartment(randInt);

    /**
     * Main style used for buttons
     */
    private BitmapFont buttonFont = new BitmapFont();
    private TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    private TextureAtlas buttonAtlas;
    private Skin skin = new Skin();

    /**
     * Sprite for Shopbackground and Fonts for Shop Information
     */
    private Sprite shopBackground;
    private BitmapFont titleFont = new BitmapFont();
    private BitmapFont bodyFont = new BitmapFont();

    @Override
    public void update(float delta){ }

    @Override
    public void show() {
        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        buttonAtlas = new TextureAtlas("buttonSpriteSheet.txt");
        skin.addRegions(buttonAtlas);

        textButtonStyle.font = buttonFont;
        textButtonStyle.up = skin.getDrawable("buttonUp");
        textButtonStyle.down = skin.getDrawable("buttonDown");

        shopBackground = createShopBackground();
    }

    private Texture background;
    private TextureAtlas roomSpriteAtlas;

    private Texture computerScienceTexture;
    private Sprite computerScienceSprite;
    private Texture lawAndManagementTexture;
    private Sprite lawAndManagementSprite;
    private TextButton toMenu;
    private Texture hpBar;
    private Texture hpBackground;
    private BitmapFont indicatorFont;
    private List<TextButton> buyButtonList;
    private List<TextButton> buyResourceButtonList;
    private List<Weapon> buyWeaponList;
    private List<TextButton> sellButtonList;
    private List<Weapon> sellWeaponList;
    private List<RoomUpgrade> roomUpgradeList;

    public void setUpTextures(){
        background = new Texture("battleBackground.png");
        roomSpriteAtlas = new TextureAtlas("roomSpriteSheet.txt");
        computerScienceTexture = new Texture("ComputerScIsland.png");
        computerScienceSprite = new Sprite(computerScienceTexture);
        lawAndManagementTexture = new Texture("LMI.png");
        lawAndManagementSprite = new Sprite(lawAndManagementTexture);
        toMenu = new TextButton("To Menu", textButtonStyle);
        hpBar = new Texture("background.png");
        hpBackground = new Texture("disabledBackground.png");
        indicatorFont = new BitmapFont();
        buyButtonList = new ArrayList<TextButton>();
        buyWeaponList = new ArrayList<Weapon>();
        sellButtonList = new ArrayList<TextButton>();
        sellWeaponList = new ArrayList<Weapon>();
        roomUpgradeList = new ArrayList<RoomUpgrade>();
        buyResourceButtonList = new ArrayList<TextButton>();
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);

        batch.begin();

        drawBackground();
        drawFriendlyShip();
        drawDepartment(randInt);

        buttonToMenu();

        drawHealthBar();
        drawIndicators();

        drawShopBackground(shopBackground);
        drawBuyWeaponFeatures(titleFont, bodyFont, textButtonStyle);
        drawSellWeaponFeatures(titleFont, bodyFont, textButtonStyle);
        drawBuyRoomUpgradeFeatures(titleFont, bodyFont, textButtonStyle);
        drawBuyResourceFeatures(titleFont);

        batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
        batch.dispose();
    }

    /**
     * Picks a random int
     * @param max
     * @return returns random int between 0 and max - 1
     */
    public int pickRandom(int max) {
        Random rand = new Random();
        return rand.nextInt(max);
    }

    /**
     * Draws the Shop background
     */
    public void drawBackground() {
        batch.draw(background, 0, 0);
    }

    /**
     * Draws the friendly ship from room textures and constant coordinates
     */
    public void drawFriendlyShip(){
        Sprite friendlyCrewQuaters = roomSpriteAtlas.createSprite("crewQuaters");
        friendlyCrewQuaters.setPosition(CoordBank.FRIENDLY_CREWQUATERS.getX(),CoordBank.FRIENDLY_CREWQUATERS.getY());

        Sprite friendlyEmptyRoom1 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom1.setPosition(CoordBank.FRIENDLY_EMPTYROOM1.getX(),CoordBank.FRIENDLY_EMPTYROOM1.getY());

        Sprite friendlyCrowsNest = roomSpriteAtlas.createSprite("crowsNest");
        friendlyCrowsNest.setPosition(CoordBank.FRIENDLY_CROWSNEST.getX(),CoordBank.FRIENDLY_CROWSNEST.getY());

        Sprite friendlyGunDeck = roomSpriteAtlas.createSprite("gunDeck");
        friendlyGunDeck.setPosition(CoordBank.FRIENDLY_GUNDECK.getX(),CoordBank.FRIENDLY_GUNDECK.getY());

        Sprite friendlyEmptyRoom2 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom2.setPosition(CoordBank.FRIENDLY_EMPTYROOM2.getX(),CoordBank.FRIENDLY_EMPTYROOM2.getY());

        Sprite friendlyHelm = roomSpriteAtlas.createSprite("helm");
        friendlyHelm.setPosition(CoordBank.FRIENDLY_HELM.getX(),CoordBank.FRIENDLY_HELM.getY());

        Sprite friendlyEmptyRoom3 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom3.setPosition(CoordBank.FRIENDLY_EMPTYROOM3.getX(),CoordBank.FRIENDLY_EMPTYROOM3.getY());

        Sprite friendlyEmptyRoom4 = roomSpriteAtlas.createSprite("EmptyRoom");
        friendlyEmptyRoom4.setPosition(CoordBank.FRIENDLY_EMPTYROOM4.getX(),CoordBank.FRIENDLY_EMPTYROOM4.getY());

        friendlyCrewQuaters.draw(batch);
        friendlyCrowsNest.draw(batch);
        friendlyGunDeck.draw(batch);
        friendlyHelm.draw(batch);
        friendlyEmptyRoom1.draw(batch);
        friendlyEmptyRoom2.draw(batch);
        friendlyEmptyRoom3.draw(batch);
        friendlyEmptyRoom4.draw(batch);
    }

    /**
     * Assigns a random department to be used
     * @param randInt
     * @return random department
     */
    public Department assignDepartment(int randInt) {
        switch (randInt) {
            case 0:
                return (new Department(COMP_SCI_WEPS.getWeaponList(), COMP_SCI_UPGRADES.getRoomUpgradeList(), gameManager));
            case 1:
                return (new Department(LMB_WEPS.getWeaponList(), LMB_UPGRADES.getRoomUpgradeList(), gameManager));
        }
        return null;
    }

    /**
     * Draws the Department generated above
     * @param randInt
     */
    public void drawDepartment(int randInt) {
        switch (randInt) {
            case 0:
                batch.draw(computerScienceSprite,500,256);
                break;
            case 1:
                batch.draw(lawAndManagementSprite,400,200);
                break;
        }
    }

    /**
     * Draws Hp bars for both ships
     */
    public void drawHealthBar() {
        double defaultWidth = 320;
        int width = (int)(defaultWidth * ((double)playerShip.getHullHP() / (double)playerShip.getBaseHullHP()));

        batch.draw(hpBackground,25, 970, 320, 16);
        batch.draw(hpBar,25, 970, width, 16);
    }

    /**
     * Draws resource indicators for player
     */
    public void drawIndicators(){
        indicatorFont.setColor(1,1,1,1);

        indicatorFont.draw(batch, "Score: " + gameManager.getPoints(), 25, 965);
        indicatorFont.draw(batch, "Gold: " + gameManager.getGold(), 110, 965);
        indicatorFont.draw(batch, "Food: " + gameManager.getFood(), 195, 965);
        indicatorFont.draw(batch, "Crew: " + playerShip.getCrew(), 280, 965);
    }

    /**
     * Draws the Button returning to menu, taking the style button
     */
    public void buttonToMenu(){
        toMenu.setPosition(880, 980);
        toMenu.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
                return true;
            }
        });
        stage.addActor(toMenu);
    }

    /**
     * Generates the shopBackground
     * @return return shop background sprite
     */
    public Sprite createShopBackground(){
        Texture shopBackgroundTexture = new Texture("shopBackground.png");
        return new Sprite(shopBackgroundTexture);
    }

    /**
     * Draws the shop background from given Sprite
     * @param shopBackground
     */
    public void drawShopBackground(Sprite shopBackground) {
        shopBackground.draw(batch);
        shopBackground.setScale(1.5f, 1.5f);
        shopBackground.setPosition(256, 256);
        shopBackground.setAlpha(0.85f);
    }

    /**
     * Takes button styles and Fonts, draws buttons to buy items in the shop and the item information
     * @param titleFont
     * @param bodyFont
     * @param textButtonStyle
     */
    public void drawBuyWeaponFeatures(BitmapFont titleFont, BitmapFont bodyFont, TextButton.TextButtonStyle textButtonStyle) {
        int i = 0;
        while (i <= department.getWeaponStock().size() - 1 && department.getWeaponStock().get(i) instanceof Weapon) {
            buyWeaponList.add(department.getWeaponStock().get(i));
            i++;
        }

        int j = 0;
        while (j <= buyWeaponList.size() - 1){
            titleFont.draw(batch, buyWeaponList.get(j).getName(), 160, 880 - (150 * j));
            bodyFont.draw(batch, "Damage: " + df.format(buyWeaponList.get(j).getBaseDamage()), 160, 850 - (150 * j));
            bodyFont.draw(batch, "Crit Chance: " + df.format(buyWeaponList.get(j).getCritChance()), 160, 830 - (150 * j));
            bodyFont.draw(batch, "Hit Chance: " + df.format(buyWeaponList.get(j).getAccuracy()), 160, 810 - (150 * j));
            bodyFont.draw(batch, "Cooldown: " + df.format(buyWeaponList.get(j).getCooldown()), 160, 790 - (150 * j));

            buyResourceButtonList.add(new TextButton("Buy (" + buyWeaponList.get(j).getCost() + "g)", textButtonStyle));
            buyResourceButtonList.get(j).setPosition(160, 740 - (j * 150));
            stage.addActor(buyResourceButtonList.get(j));
            j++;
        }

        buyWeaponButtonListener();
    }

    /**
     * Adds listeners to all weapon buy buttons
     */
    public void buyWeaponButtonListener() {
        int i = 0;
        while (i <= buyButtonList.size() - 1) {
            final int j = i;
            buyButtonList.get(j).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    try {
                        department.buyWeapon(sellWeaponList.get(j));
                        stage.clear();
                    } catch (IllegalStateException e) {
                        buyButtonList.get(j).setText("Insufficient Gold!");
                    } catch (IllegalArgumentException e) {
                        if (e.getMessage() == "Weapon does not exist") {
                            buyButtonList.get(j).setText("Out of Stock :(");
                        } else if (e.getMessage() == "Not enough gold") {
                            buyButtonList.get(j).setText("Insufficient Gold!");
                        }
                    }

                    return true;
                }
            });

            i++;
        }
    }

    /**
     * Takes button styles and Fonts, draws buttons to sell items in the shop and the item information
     * @param titleFont
     * @param bodyFont
     * @param textButtonStyle
     */
    public void drawSellWeaponFeatures(BitmapFont titleFont, BitmapFont bodyFont, TextButton.TextButtonStyle textButtonStyle) {
        int i = 0;
        while (i <= playerShip.getWeapons().size() - 1 && playerShip.getWeapons().get(i) instanceof Weapon) {
            buyWeaponList.add(playerShip.getWeapons().get(i));
            i++;
        }

        int j = 0;
        while (j <= buyWeaponList.size() - 1){
            titleFont.draw(batch, buyWeaponList.get(j).getName(), 360, 880 - (150 * j));
            bodyFont.draw(batch, "Damage: " + df.format(buyWeaponList.get(j).getBaseDamage()), 360, 850 - (150 * j));
            bodyFont.draw(batch, "Crit Chance: " + df.format(buyWeaponList.get(j).getCritChance()), 360, 830 - (150 * j));
            bodyFont.draw(batch, "Hit Chance: " + df.format(buyWeaponList.get(j).getAccuracy()), 360, 810 - (150 * j));
            bodyFont.draw(batch, "Cooldown: " + df.format(buyWeaponList.get(j).getCooldown()), 360, 790 - (150 * j));

            sellButtonList.add(new TextButton("Sell (" + df.format(buyWeaponList.get(j).getCost() * STORE_SELL_PRICE_MULTIPLIER) + "g)", textButtonStyle));
            sellButtonList.get(j).setPosition(360, 740 - (j * 150));
            stage.addActor(sellButtonList.get(j));
            j++;
        }
        sellButtonListener(sellButtonList, buyWeaponList);
    }

    /**
     * Adds listeners to all sell buttons
     * @param buyButtonList
     * @param weaponList
     */
    public void sellButtonListener(final List<TextButton> buyButtonList, final List<Weapon> weaponList) {
        int i = 0;
        while (i <= buyButtonList.size() - 1) {
            final int j = i;
            buyButtonList.get(j).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    try {
                        department.sellWeapon(sellWeaponList.get(j));
                        stage.clear();
                    } catch (IllegalArgumentException e) {
                        buyButtonList.get(j).setText("Empty Slot!");
                    }
                    return true;
                }
            });

            i++;
        }

    }

    /**
     * Takes button styles and Fonts, draws buttons to buy room upgrades in the shop and the item information
     * @param titleFont
     * @param bodyFont
     * @param textButtonStyle
     */
    public void drawBuyRoomUpgradeFeatures(BitmapFont titleFont, BitmapFont bodyFont, TextButton.TextButtonStyle textButtonStyle) {
        int i = 0;
        while (i <= department.getUpgradeStock().size() - 1 && department.getUpgradeStock().get(i) instanceof RoomUpgrade) {
            roomUpgradeList.add(department.getUpgradeStock().get(i));
            i++;
        }

        int j = 0;
        while (j <= roomUpgradeList.size() - 1){
            titleFont.draw(batch, roomUpgradeList.get(j).getName(), 560, 880 - (150 * j));
            bodyFont.draw(batch, "Room: " + roomUpgradeList.get(j).getAffectsRoom(), 560, 830 - (150 * j));
            bodyFont.draw(batch, "Multiplier: " + df.format(roomUpgradeList.get(j).getMultiplier()), 560, 850 - (150 * j));

            buyResourceButtonList.add(new TextButton("Buy (" + df.format(roomUpgradeList.get(j).getCost()) + "g)", textButtonStyle));
            buyResourceButtonList.get(j).setPosition(560, 740 - (j * 150));
            stage.addActor(buyResourceButtonList.get(j));
            j++;
        }
        buyRoomUpgradeButtonListener(buyResourceButtonList, roomUpgradeList);
    }

    /**
     * Adds listeners to all buy room upgrade buttons
     * @param buyButtonList
     * @param roomUpgradeList
     */
    public void buyRoomUpgradeButtonListener(final List<TextButton> buyButtonList, final List<RoomUpgrade> roomUpgradeList) {
        int i = 0;
        while (i <= buyButtonList.size() - 1) {
            final int j = i;
            buyButtonList.get(j).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    try {
                        department.buyRoomUpgrade(roomUpgradeList.get(j));
                        stage.clear();
                    } catch (IllegalStateException e) {
                        buyButtonList.get(j).setText("Insufficient Gold!");
                    } catch (IllegalArgumentException e) {
                        if (e.getMessage() == "Room does not exist") {
                            buyButtonList.get(j).setText("Out of Stock :(");
                        } else if (e.getMessage() == "Not enough gold") {
                            buyButtonList.get(j).setText("Insufficient Gold!");
                        }
                    }
                    return true;
                }
            });

            i++;
        }

    }

    /**
     * Draws buttons and information for buying resources
     * @param titleFont
     */
    public void drawBuyResourceFeatures(BitmapFont titleFont){
        titleFont.draw(batch, "Crew", 160, 200);
        titleFont.draw(batch, "Food", 360, 200);
        titleFont.draw(batch, "Repair", 560, 200);

        buyResourceButtonList.add(new TextButton("Buy (" + CREW_COST + "g)", textButtonStyle));
        buyResourceButtonList.get(0).setPosition(220, 180);
        stage.addActor(buyResourceButtonList.get(0));

        buyResourceButtonList.add(new TextButton("Buy (" + FOOD_COST + "g)", textButtonStyle));
        buyResourceButtonList.get(1).setPosition(420, 180);
        stage.addActor(buyResourceButtonList.get(1));

        buyResourceButtonList.add(new TextButton("Buy (" + REPAIR_COST + "g)", textButtonStyle));
        buyResourceButtonList.get(2).setPosition(620, 180);
        stage.addActor(buyResourceButtonList.get(2));

        buyResourceButtonListener(buyResourceButtonList);
    }

    /**
     * Adds listeners to the buy resource buttons
     * @param buyButtonList
     */
    public void buyResourceButtonListener(final List<TextButton> buyButtonList){
        buyButtonList.get(0).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    try {
                        department.buyResource(Resource.CREW, SHOP_CREW_AMOUNT);
                        stage.clear();
                    } catch (IllegalStateException e) {
                    } catch (IllegalArgumentException e) {
                    }
                    return true;
                }
            });
        buyButtonList.get(1).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    department.buyResource(Resource.FOOD, SHOP_FOOD_AMOUNT);
                    stage.clear();
                } catch (IllegalStateException e) {
                } catch (IllegalArgumentException e) {
                }
                return true;
            }
        });
        buyButtonList.get(2).addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    department.buyResource(Resource.REPAIR, SHOP_REPAIR_AMOUNT);
                    stage.clear();
                } catch (IllegalStateException e) {
                } catch (IllegalArgumentException e) {
                }
                return true;
            }
        });
    }


}

