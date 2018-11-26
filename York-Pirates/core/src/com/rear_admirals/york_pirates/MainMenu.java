package com.rear_admirals.york_pirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rear_admirals.york_pirates.Combat.ShipCombat;

public class MainMenu implements Screen {

    final PirateGame main;
//    OrthographicCamera camera;
    Stage stage;

    public MainMenu(final PirateGame main){
        this.main = main;
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, 800, 480);

        stage = new Stage();
        main.batch = new SpriteBatch();
        main.skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));


        Table table = new Table();

        TextButton welcome_text = new TextButton("Welcome to Rear Admirals", main.skin);

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

        table.add(welcome_text).padBottom(20f);
        table.row(); // Ends the current row
        table.add(combat_mode).fill();
        table.row();
        table.add(sailing_mode).fill();
        table.row();


        table.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/1.5f);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();
        stage.draw();
        main.batch.end();

    }
    @Override
    public void resize(int width, int height) {
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


