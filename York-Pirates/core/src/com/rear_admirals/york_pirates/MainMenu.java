package com.rear_admirals.york_pirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.rear_admirals.york_pirates.Combat.ShipCombat;

public class MainMenu implements Screen {

    final PirateGame main;
//    OrthographicCamera camera;
    Stage stage;
    float screen_width;
    float screen_height;

    public MainMenu(final PirateGame main){
        this.main = main;
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, 800, 480);

        Container<Table> tableContainer = new Container<Table>();
        tableContainer.setFillParent(true);
        tableContainer.setPosition(0,0);
        tableContainer.align(Align.center);

        stage = new Stage(new FitViewport(1920,1080));

        main.skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));

        screen_width = stage.getWidth();
        screen_height = stage.getHeight();

        System.out.println(screen_width + ", " + screen_height);

        Table table = new Table();

        Label title = new Label("Rear Admirals", main.skin, "title");
        title.setAlignment(Align.center);
        TextButton combat_mode = new TextButton("Go to Combat Mode", main.skin);

        // Allows button to be clickable, and sets process for when clicked.
        combat_mode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new ShipCombat(main));
                dispose();
            }
        });

        TextButton sailing_mode = new TextButton("Go to Sailing Mode", main.skin);

        //TODO Implement ShipSailing constructor to work with scene switching
//        combat_mode.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y){
//                main.setScreen(new ShipSailing(main));
//                dispose();
//            }
//        });

        table.setDebug(true);

        tableContainer.setActor(table);

        table.add(title).padBottom(screen_height/20).width(screen_width/4);
        table.row(); // Ends the current row
        table.add(combat_mode).uniform().padBottom(screen_height/40).size(screen_width/2,screen_height/10);
        table.row();
        table.add(sailing_mode).uniform().fill();
        table.row();

        stage.addActor(tableContainer);
        Gdx.input.setInputProcessor(stage);

    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}


