package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.College;
import com.rear_admirals.york_pirates.screen.combat.CombatScreen;
import com.rear_admirals.york_pirates.base.BaseActor;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.base.BaseScreen;
import com.rear_admirals.york_pirates.Ship;
import com.rear_admirals.york_pirates.screen.combat.attacks.Attack;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.*;
import java.util.concurrent.*;

import static com.rear_admirals.york_pirates.College.*;
import static com.rear_admirals.york_pirates.PirateGame.*;
import static com.rear_admirals.york_pirates.ShipType.*;

public class SailingScreen extends BaseScreen {

    private Ship playerShip;

    //Map Variables
    private ArrayList<BaseActor> obstacleList;
    private ArrayList<BaseActor> removeList;
    private ArrayList<BaseActor> regionList;


    //calculate game world dimensions
    private TiledMap tiledMap;
    private MapProperties mapProperties;
    private int mapTileWidth;
    private int mapTileHeight;

    private int tilePixelSize;
    private final int mapPixelWidth;
    private final int mapPixelHeight;


    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera tiledCamera;
    private int[] backgroundLayers = {0,1,2};
    private int[] foregroundLayers = {3};

    private Label sailsHealthValueLabel, sailsHealthTextLabel;
    private Label hullHealthValueLabel, hullHealthTextLabel;
    private Label goldValueLabel, goldTextLabel;
    private Label pointsValueLabel, pointsTextLabel;

    private Label mapMessage;
    private Label hintMessage;

    private Float timer;

    public SailingScreen(final PirateGame main, boolean isFirstSailingInstance){
        super(main);

        playerShip = main.getPlayer().getPlayerShip();
        Gdx.app.debug("Sailing","Player ship's name is "+playerShip.getName());

        mainStage.addActor(playerShip);
        Gdx.app.debug("Sailing","playerShip added to mainStage");

        Table uiTable = new Table();

        sailsHealthTextLabel = new Label("Sails Health: ", main.getSkin(), "default_black");
        sailsHealthValueLabel = new Label(Integer.toString(main.getPlayer().getPlayerShip().getSailsHealth()), main.getSkin(), "default_black");
        sailsHealthValueLabel.setAlignment(Align.left);

        hullHealthTextLabel = new Label("Hull Health: ", main.getSkin(), "default_black");
        hullHealthValueLabel = new Label(Integer.toString(main.getPlayer().getPlayerShip().getHullHealth()), main.getSkin(),"default_black");
        hullHealthValueLabel.setAlignment(Align.left);

        pointsTextLabel = new Label("Points: ", main.getSkin(),"default_black");
        pointsValueLabel = new Label(Integer.toString(main.getPlayer().getPoints()), main.getSkin(), "default_black");
        pointsValueLabel.setAlignment(Align.left);

        goldTextLabel = new Label("Gold:", main.getSkin(),"default_black");
        goldValueLabel = new Label(Integer.toString(main.getPlayer().getGold()), main.getSkin(), "default_black");
        goldValueLabel.setAlignment(Align.left);

        uiTable.add(sailsHealthTextLabel).fill();
        uiTable.add(sailsHealthValueLabel).fill();
        uiTable.row();
        uiTable.add(hullHealthTextLabel).fill();
        uiTable.add(hullHealthValueLabel).fill();
        uiTable.row();
        uiTable.add(goldTextLabel).fill();
        uiTable.add(goldValueLabel).fill();
        uiTable.row();
        uiTable.add(pointsTextLabel).fill();
        uiTable.add(pointsValueLabel).width(pointsTextLabel.getWidth());
        uiTable.setDebug(true);
        uiTable.align(Align.topRight);
        uiTable.setFillParent(true);

        uiStage.addActor(uiTable);

        mapMessage = new Label("", main.getSkin(), "default_black");
        hintMessage = new Label("", main.getSkin(),"default_black");

        Table messageTable = new Table();
        messageTable.add(mapMessage);
        messageTable.row();
        messageTable.add(hintMessage);

        messageTable.setFillParent(true);
        messageTable.top();

        uiStage.addActor(messageTable);

        obstacleList = new ArrayList<BaseActor>();
        removeList = new ArrayList<BaseActor>();
        regionList = new ArrayList<BaseActor>();

        // set up Tiled Map and associated properties/attributes (width/height)
        tiledMap = new TmxMapLoader().load("new_game_map.tmx");
        mapProperties = tiledMap.getProperties();
        mapTileWidth = mapProperties.get("width", Integer.class);
        mapTileHeight = mapProperties.get("height", Integer.class);
        tilePixelSize = mapProperties.get("tilewidth", Integer.class);
        mapPixelWidth = tilePixelSize * mapTileWidth;
        mapPixelHeight = tilePixelSize * mapTileHeight;


        // Setup renderer
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        // Setup camera
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, viewwidth, viewheight);
        tiledCamera.update();

        MapObjects objects = tiledMap.getLayers().get("ObjectData").getObjects();
        for (MapObject object : objects) {
            String name = object.getName();

            // all object data assumed to be stored as rectangles
            RectangleMapObject rectangleObject = (RectangleMapObject)object;
            Rectangle r = rectangleObject.getRectangle();

            if (name.equals("player") && isFirstSailingInstance){
                playerShip.setPosition(r.x, r.y);
            }
            else if (name.equals("player") && !isFirstSailingInstance) {
                playerShip.setPosition(pirateGame.getSailingShipX(), pirateGame.getSailingShipY());
                playerShip.setRotation(pirateGame.getSailingShipRotation());
            }
            else{
                System.err.println("Unknown tilemap object: " + name);
            }
        }

        objects = tiledMap.getLayers().get("PhysicsData").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                Rectangle r = rectangleObject.getRectangle();

                BaseActor solid = new BaseActor();
                solid.setPosition(r.x, r.y);
                solid.setSize(r.width, r.height);
                solid.setName(object.getName());
                solid.setRectangleBoundary();
                String objectName = object.getName();

                if (objectName.equals("derwent")) solid.setCollege(Derwent);
                else if (objectName.equals("james")) solid.setCollege(James);
                else if (objectName.equals("vanbrugh")) solid.setCollege(Vanbrugh);
                else if (objectName.equals("alcuin")) solid.setCollege(Alcuin);
                else if (objectName.equals("wentworth")) solid.setCollege(Wentworth);
                else if (objectName.equals("chemistry"))solid.setDepartment(Chemistry);
                else if (objectName.equals("physics")) solid.setDepartment(Physics);
                else if (objectName.equals("economics")) solid.setDepartment(Economics);
                else{
                    Gdx.app.debug("Sailing","Not college/department: " + solid.getName());
                }
                obstacleList.add(solid);
            } else {
                Gdx.app.error("Sailing","Unknown PhysicsData object.");
            }
        }

        objects = tiledMap.getLayers().get("RegionData").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                Rectangle r = rectangleObject.getRectangle();

                BaseActor region = new BaseActor();
                region.setPosition(r.x, r.y);
                region.setSize(r.width, r.height);
                region.setRectangleBoundary();
                region.setName(object.getName());

                if (object.getName().equals("derwentregion")) region.setCollege(Derwent);
                else if (object.getName().equals("jamesregion")) region.setCollege(James);
                else if (object.getName().equals("vanbrughregion")) region.setCollege(Vanbrugh);
                else if (object.getName().equals("alcuinregion")) region.setCollege(Alcuin);
                else if (object.getName().equals("wentworthregion")) region.setCollege(Wentworth);
                regionList.add(region);
            } else {
                System.err.println("Unknown RegionData object.");
            }
        }

        timer = 0f;

        InputMultiplexer im = new InputMultiplexer(uiStage, mainStage);
        Gdx.input.setInputProcessor(im);
    }

    @Override
    public void update(float delta) {
        removeList.clear();
        goldValueLabel.setText(Integer.toString(pirateGame.getPlayer().getGold()));
        this.playerShip.playerMove(delta);

        pirateGame.setSailingShipX(playerShip.getX());
        pirateGame.setSailingShipY(playerShip.getY());
        pirateGame.setSailingShipRotation(playerShip.getRotation());

        Boolean x = false;
        for (BaseActor region : regionList) {
            String name = region.getName();
            if (playerShip.overlaps(region, false)) {
                x = true;
                mapMessage.setText(capitalizeFirstLetter(name.substring(0, name.length() - 6)) + " Territory");
                int enemyChance = ThreadLocalRandom.current().nextInt(0, 10001);
                if (enemyChance <= 10) {
                    pirateGame.setSailingShipX(playerShip.getX());
                    pirateGame.setSailingShipY(playerShip.getY());
                    pirateGame.setSailingShipRotation(playerShip.getRotation());
                    Gdx.app.log("Sailing","Enemy encountered in " + name);
                    College college = region.getCollege();
                    if (!playerShip.getCollege().getAlly().contains(college)) {
                        Gdx.app.debug("Sailing",name);
                        pirateGame.setScreen(new CombatScreen(pirateGame, new Ship(Brig, college)));
                    }
                }
            }
        }

        if (!x) {
            mapMessage.setText("Neutral Territory");
        }


        Boolean y = false;
        for (BaseActor obstacle : obstacleList) {
            String name = obstacle.getName();
            if (playerShip.overlaps(obstacle, true)) {
                y = true;
                if (!(obstacle.getDepartment() == null)) {
                    mapMessage.setText(capitalizeFirstLetter(name) + " Island");
                    hintMessage.setText("Press F to interact");
                    if (Gdx.input.isKeyPressed(Input.Keys.F)) pirateGame.setScreen(new DepartmentScreen(pirateGame, obstacle.getDepartment()));
                }
                // Obstacle must be a college if college not null
                else if (!(obstacle.getCollege() == null)) {
                    mapMessage.setText(capitalizeFirstLetter(name) + " Island");
                    hintMessage.setText("Press F to interact");
                    College college = obstacle.getCollege();
                    if (Gdx.input.isKeyPressed(Input.Keys.F)) {
                        Gdx.app.debug("Sailing","Interacted with a college");
                        if (!playerShip.getCollege().getAlly().contains(college) && !obstacle.getCollege().isBossDead()) {
                            Gdx.app.debug("Sailing","College is hostile.");
                            pirateGame.setScreen(new CombatScreen(pirateGame, new Ship(15, 15, 15, Brig, college, college.getName() + " Boss", true)));
                        } else {
                            Gdx.app.debug("Sailing","College is friendly.");
                            pirateGame.setScreen(new CollegeScreen(pirateGame, college));
                        }
                    }
                } else {
//                    Gdx.app.debug("Sailing","Pure obstacle encountered");
                }
            }
        }

        if (!y) hintMessage.setText("");

        for (BaseActor object : removeList) {
            object.remove();
        }

        // camera adjustment
        Camera mainCamera = mainStage.getCamera();

        // center camera on player
        mainCamera.position.x = playerShip.getX() + playerShip.getOriginX();
        mainCamera.position.y = playerShip.getY() + playerShip.getOriginY();

        // bound camera to layout
        mainCamera.position.x = MathUtils.clamp(mainCamera.position.x, viewwidth / 2, mapPixelWidth - viewwidth / 2);
        mainCamera.position.y = MathUtils.clamp(mainCamera.position.y, viewheight / 2, mapPixelHeight - viewheight / 2);
        mainCamera.update();

        // adjust tilemap camera to stay in sync with main camera
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);

        timer += delta;
        if (timer > 1) {
            // Only give the player points when not sailing in neutral territory.
            if (x){
                pirateGame.getPlayer().addPoints(1);
            }
            timer -= 1;
        }

        pointsValueLabel.setText(Integer.toString(pirateGame.getPlayer().getPoints()));
        sailsHealthValueLabel.setText(Integer.toString(pirateGame.getPlayer().getPlayerShip().getSailsHealth()));
        hullHealthValueLabel.setText(Integer.toString(pirateGame.getPlayer().getPlayerShip().getHullHealth()));
    }

    @Override
    public void render(float delta) {
        uiStage.act(delta);

        mainStage.act(delta);

        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.render(backgroundLayers);
        mainStage.draw();

        tiledMapRenderer.render(foregroundLayers);

        uiStage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            System.out.println("SAVED");
            saveFile(pirateGame.getSave_file());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
            pirateGame.getPlayer().addGold(100);
        }
        if (!playerShip.isAnchor()){
            playerShip.addAccelerationAS(playerShip.getRotation(), 10000);
        } else{
            playerShip.setAccelerationXY(0,0);
            playerShip.setDeceleration(250);
        }
    }

    @Override
    public void dispose () {
        mainStage.dispose();
        uiStage.dispose();
        playerShip.getSailingTexture().dispose();
    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public void saveFile(Preferences file){

        //
        byte[] ownedAttacks = SerializationUtils.serialize(new ArrayList<Attack>(pirateGame.getPlayer().getOwnedAttacks()));
        String encodedOwnedAttacks = Base64.getEncoder().encodeToString(ownedAttacks);

        byte[] equippedAttacks = SerializationUtils.serialize(new ArrayList<Attack>(pirateGame.getPlayer().getEquippedAttacks()));
        String encodedEquippedAttacks = Base64.getEncoder().encodeToString(equippedAttacks);

        byte[] derwentData = SerializationUtils.serialize(Derwent);
        String encodedDerwent = Base64.getEncoder().encodeToString(derwentData);

        byte[] vanbrughData = SerializationUtils.serialize(Vanbrugh);
        String encodedVanbrugh = Base64.getEncoder().encodeToString(vanbrughData);

        byte[] jamesData = SerializationUtils.serialize(James);
        String encodedJames = Base64.getEncoder().encodeToString(jamesData);

        byte[] alcuinData = SerializationUtils.serialize(Alcuin);
        String encodedAlcuin = Base64.getEncoder().encodeToString(alcuinData);

        byte[] wentworthData = SerializationUtils.serialize(Wentworth);
        String encodedWentworth = Base64.getEncoder().encodeToString(wentworthData);



        file.putString("owned attacks", encodedOwnedAttacks);
        file.putString("equipped attacks", encodedEquippedAttacks);

        file.putString("derwent", encodedDerwent);
        file.putString("vanbrugh", encodedVanbrugh);
        file.putString("james", encodedJames);
        file.putString("alcuin", encodedAlcuin);
        file.putString("wentworth", encodedWentworth);


        // Player Data
        file.putInteger("gold", pirateGame.getPlayer().getGold());
        file.putInteger("points", pirateGame.getPlayer().getPoints());


        //Ship Data: float atkMultiplier, int defence, int accMultiplier, ShipType type, College college, String name, boolean isBoss
        file.putFloat("atkMultiplier", playerShip.getAtkMultiplier());
        file.putInteger("defence", playerShip.getDefence());
        file.putFloat("accMultiplier", playerShip.getAccMultiplier());
        file.putString("name", playerShip.getName());
        file.putInteger("sail health", playerShip.getSailsHealth());
        file.putInteger("hull health", playerShip.getHullHealth());


        // Ship Position Data
        file.putFloat("shipX", pirateGame.getSailingShipX());
        file.putFloat("shipY", pirateGame.getSailingShipY());
        file.putFloat("shipRotation", pirateGame.getSailingShipRotation());

        file.flush();
    }

}
