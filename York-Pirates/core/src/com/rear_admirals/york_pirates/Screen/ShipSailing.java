package com.rear_admirals.york_pirates.Screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rear_admirals.york_pirates.Combat.ShipCombat;
import com.rear_admirals.york_pirates.GameObject;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Ship;

import java.util.ArrayList;

import static com.rear_admirals.york_pirates.College.*;
import static com.rear_admirals.york_pirates.ShipType.*;

public class ShipSailing implements Screen {

    final PirateGame main;
    private Ship playerShip;
    private Ship enemy;

    //Map Variables

    private ArrayList<GameObject> obstacleList;
    private ArrayList<GameObject> removeList;

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

    private Stage mainStage;
    private Stage uiStage;
    private float viewwidth;
    private float viewheight;

    private Label pointsLabel;

    private Float timer;

    public ShipSailing(final PirateGame main){
        this.main = main;
	    Gdx.graphics.setTitle("Sailing Demo - York Pirates!");
        viewwidth = 1920;
        viewheight = 1080;
        playerShip = main.player.getPlayerShip();
        System.out.println(playerShip.getName());
        mainStage = new Stage(new FitViewport(viewwidth,viewheight));
        uiStage = new Stage(new FitViewport(viewwidth,viewheight));

        mainStage.addActor(playerShip);
        System.out.println("playerShip added");
        enemy = new Ship(Brig, Derwent, "ship (2).png");
        mainStage.addActor(enemy);
        System.out.println("enemy added");

        Table uiTable = new Table();

        Label pointsTextLabel = new Label("Points: ", main.skin,"default_black");
        pointsLabel = new Label(Integer.toString(main.player.getPoints()), main.skin, "default_black");
        pointsLabel.setAlignment(Align.left);

        uiTable.add(pointsTextLabel);
        uiTable.add(pointsLabel).width(pointsTextLabel.getWidth());
        uiTable.align(Align.topRight);
        uiTable.setFillParent(true);

        uiStage.addActor(uiTable);

        pointsLabel.setDebug(true);
        uiTable.setDebug(true);


        obstacleList = new ArrayList<GameObject>();
        removeList = new ArrayList<GameObject>();

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
            else if( name.equals("enemy")){
                enemy.setPosition(r.x, r.y);
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
                solid.setRectangleBoundary();
                obstacleList.add(solid);
            } else {
                System.err.println("Unknown PhysicsData object.");
            }
        }

        timer = 0f;

        InputMultiplexer im = new InputMultiplexer(uiStage, mainStage);
        Gdx.input.setInputProcessor(im);

    }

//    @Override
    public void update(float delta) {
        removeList.clear();

        this.playerShip.playerMove(delta);

        for (GameObject obstacle : obstacleList) {
            playerShip.overlaps(obstacle, true);
        }

        if (playerShip.overlaps(enemy, true)) {
            playerShip.setAccelerationXY(0,0);
            playerShip.setSpeed(0);
            main.setScreen(new ShipCombat(main, enemy));
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
        mainCamera.position.x = MathUtils.clamp(mainCamera.position.x, viewwidth/2, mapWidth-viewwidth/2);
        mainCamera.position.y = MathUtils.clamp(mainCamera.position.y, viewheight/2, mapHeight-viewheight/2);
        mainCamera.update();

        // adjust tilemap camera to stay in sync with main camera
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);

        timer += delta;
        if (timer > 1){
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
    public void resize(int width, int height) {
        mainStage.getViewport().update(width,height);
        uiStage.getViewport().update(width,height);
        this.viewwidth = mainStage.getWidth();
        this.viewheight = mainStage.getHeight();
    }

    @Override
    public void dispose () {
        mainStage.dispose();
        uiStage.dispose();
        playerShip.sailingTexture.dispose();
    }

    @Override
    public void show(){
    }


    @Override
    public void pause() {
    }

    @Override
    public void hide(){
    }

    @Override
    public void resume() {
    }

    public Ship getEnemy() {
        return enemy;
    }

    public void setEnemy(Ship enemy) {
        this.enemy = enemy;
    }
}
