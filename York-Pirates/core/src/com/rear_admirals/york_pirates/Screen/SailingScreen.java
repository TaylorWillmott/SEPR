package com.rear_admirals.york_pirates.Screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.Screen.Combat.CombatScreen;
import com.rear_admirals.york_pirates.GameObject;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Ship;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static com.rear_admirals.york_pirates.College.*;
import static com.rear_admirals.york_pirates.PirateGame.Chemistry;
import static com.rear_admirals.york_pirates.PirateGame.Physics;
import static com.rear_admirals.york_pirates.ShipType.*;

public class SailingScreen extends AbstractScreen {

    private Ship playerShip;

    //Map Variables

    private ArrayList<GameObject> obstacleList;
    private ArrayList<GameObject> removeList;
    private ArrayList<GameObject> regionList;

    private int tileSize = 64;
    private int tileCountWidth = 80;
    private int tileCountHeight = 80;
    //calculate game world dimensions
    final int mapWidth = tileSize * tileCountWidth;
    final int mapHeight = tileSize * tileCountHeight;
    private TiledMap tiledMap;

    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera tiledCamera;
    private int[] backgroundLayers = {0,1,2};
    private int[] foregroundLayers = {3};

    private Label pointsLabel;
    private Label mapMessage;

    private Float timer;



    public SailingScreen(final PirateGame main){
        super(main);

	    Gdx.graphics.setTitle("Sailing Demo - York Pirates!");

        playerShip = main.player.getPlayerShip();
        System.out.println(playerShip.getName());

        mainStage.addActor(playerShip);
        System.out.println("playerShip added");

        Table uiTable = new Table();

        Label pointsTextLabel = new Label("Points: ", main.skin,"default_black");
        pointsLabel = new Label(Integer.toString(main.player.getPoints()), main.skin, "default_black");
        pointsLabel.setAlignment(Align.left);
        mapMessage = new Label("", main.skin, "default_black");

        Table messageTable = new Table();
        messageTable.add(mapMessage);
        messageTable.setFillParent(true);
        messageTable.top();

        uiStage.addActor(messageTable);

        uiTable.add(pointsTextLabel);
        uiTable.add(pointsLabel).width(pointsTextLabel.getWidth());
        uiTable.align(Align.topRight);
        uiTable.setFillParent(true);



        uiStage.addActor(uiTable);

        pointsLabel.setDebug(true);
        uiTable.setDebug(true);


        obstacleList = new ArrayList<GameObject>();
        removeList = new ArrayList<GameObject>();
        regionList = new ArrayList<GameObject>();

        // set up tile map, renderer and camera
        tiledMap = new TmxMapLoader().load("game_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, viewwidth, viewheight);
        tiledCamera.update();

        MapObjects objects = tiledMap.getLayers().get("ObjectData").getObjects();
        for (MapObject object : objects) {
            String name = object.getName();

            // all object data assumed to be stored as rectangles
            RectangleMapObject rectangleObject = (RectangleMapObject)object;
            Rectangle r = rectangleObject.getRectangle();

            if (name.equals("player")){
                playerShip.setPosition(r.x, r.y);
                System.out.println(r.x + " " + r.y);
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

                GameObject solid = new GameObject();
                solid.setPosition(r.x, r.y);
                solid.setSize(r.width, r.height);
                solid.setName(object.getName());
                solid.setRectangleBoundary();
                obstacleList.add(solid);
            } else {
                System.err.println("Unknown PhysicsData object.");
            }
        }

        objects = tiledMap.getLayers().get("RegionData").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                Rectangle r = rectangleObject.getRectangle();

                GameObject region = new GameObject();
                region.setPosition(r.x, r.y);
                region.setSize(r.width, r.height);
                region.setRectangleBoundary();
                region.setName(object.getName());

                regionList.add(region);
            } else {
                System.err.println("Unknown RegionData object.");
            }
        }


        timer = 0f;

        InputMultiplexer im = new InputMultiplexer(uiStage, mainStage);
        Gdx.input.setInputProcessor(im);
        System.out.println("IP: im");

    }

    @Override
    public void update(float delta) {
        removeList.clear();

        this.playerShip.playerMove(delta);

        Boolean x = false;
        for (GameObject region : regionList) {
            String name = region.getName();
            if (playerShip.overlaps(region, false)) {
                x = true;
                mapMessage.setText("You are in " + capitalizeFirstLetter(name.substring(0, name.length() - 6)) + " territory");
                int enemyChance = ThreadLocalRandom.current().nextInt(0, 10001);
                if (enemyChance <= 10) {
                    System.out.println("Enemy Found");
                    if (name.equals("derwentregion") && !playerShip.getCollege().getAlly().contains(Derwent)) {
                        System.out.println(name);
                        main.setScreen(new CombatScreen(main, new Ship(Brig, Derwent)));
                    } else if (name.equals("vanbrughregion") && !playerShip.getCollege().getAlly().contains(Vanbrugh)) {
                        System.out.println(name);
                        main.setScreen(new CombatScreen(main, new Ship(Brig, Vanbrugh)));
                    } else if (name.equals("jamesregion") && !playerShip.getCollege().getAlly().contains(James)) {
                        System.out.println(name);
                        main.setScreen(new CombatScreen(main, new Ship(Brig, James)));
                    }
                }

            }
        }

        for (GameObject obstacle : obstacleList) {
            if (playerShip.overlaps(obstacle, true)) {
                String name = obstacle.getName();
                System.out.println(name);
                if (name.equals("chemistry")) {
                    if (Gdx.input.isKeyPressed(Input.Keys.S)) main.setScreen(new DepartmentScreen(main, Chemistry));
                } else if (name.equals("physics")) {
                    if (Gdx.input.isKeyPressed(Input.Keys.S)) main.setScreen(new DepartmentScreen(main, Physics));
                } else if (name.equals("derwent")) {
                    if (Gdx.input.isKeyPressed(Input.Keys.F) && Derwent.isBossDead() == false) {
                        main.setScreen(new CombatScreen(main, new Ship(15, 15, 15, 150, Brig, Derwent, "Derwent Boss")));
                        dispose();
                    }
                } else if (name.equals("vanbrugh")) {
                    if (Gdx.input.isKeyPressed(Input.Keys.F) && Vanbrugh.isBossDead() == false) {
                        main.setScreen(new CombatScreen(main, new Ship(15, 15, 15, 150, Brig, Vanbrugh, "Vanbrugh Boss")));
                        dispose();
                    }

                } else if (name.equals("james")) {
                    if (Gdx.input.isKeyPressed(Input.Keys.F) && James.isBossDead() == false) {
                        main.setScreen(new CombatScreen(main, new Ship(15, 15, 15, 150, Brig, James, "James Boss")));
                        dispose();
                    }
                } else {
                    System.out.println("Pure obstacle");
                }
            }
        }
        if (!x){
            mapMessage.setText("");
        }

        for (GameObject object : removeList) {
            object.remove();
        }

        // camera adjustment
        Camera mainCamera = mainStage.getCamera();

        // center camera on player
        mainCamera.position.x = playerShip.getX() + playerShip.getOriginX();
        mainCamera.position.y = playerShip.getY() + playerShip.getOriginY();

        // bound camera to layout
        mainCamera.position.x = MathUtils.clamp(mainCamera.position.x, viewwidth / 2, mapWidth - viewwidth / 2);
        mainCamera.position.y = MathUtils.clamp(mainCamera.position.y, viewheight / 2, mapHeight - viewheight / 2);
        mainCamera.update();

        // adjust tilemap camera to stay in sync with main camera
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);

        timer += delta;
        if (timer > 1) {
            main.player.addPoints(1);
            timer -= 1;
        }

        pointsLabel.setText(Integer.toString(main.player.getPoints()));
    }


    @Override
    public void render(float delta) {
        // pause only gameplay events, not UI events
        if (true) {
            mainStage.act(delta);
            update(delta);
        }

        // render
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.render(backgroundLayers);
        mainStage.draw();

        tiledMapRenderer.render(foregroundLayers);

        uiStage.draw();
        uiStage.act(delta);

        if (!playerShip.isAnchor()){
            playerShip.addAccelerationAS(playerShip.getRotation(), 10000);
        }
        else{
            playerShip.setAccelerationXY(0,0);
            playerShip.setDeceleration(100);
        }

    }

    //TODO implement base screen potentially
//    public boolean keyDown(int keycode) {

//        if (keycode == Input.Keys.P) togglePaused();
//
//        if (keycode == Input.Keys.R) game.setScreen(new SailingScreen(game));
//
//        return false;
//    }


    @Override
    public void dispose () {
        mainStage.dispose();
        uiStage.dispose();
        playerShip.sailingTexture.dispose();
    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

}
