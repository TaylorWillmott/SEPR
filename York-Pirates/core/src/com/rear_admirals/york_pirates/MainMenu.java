package com.rear_admirals.york_pirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rear_admirals.york_pirates.Screen.Combat.CombatScreen;
import com.rear_admirals.york_pirates.Screen.CollegeScreen;
import com.rear_admirals.york_pirates.Screen.DepartmentScreen;

import static com.rear_admirals.york_pirates.PirateGame.Chemistry;
import static com.rear_admirals.york_pirates.ShipType.*;
import static com.rear_admirals.york_pirates.College.*;

public class MainMenu implements Screen {

    final PirateGame main;
//    OrthographicCamera camera;
    Stage stage;
    float screen_width;
    float screen_height;

    // Screens
    public CombatScreen combatScreen;

    public MainMenu(final PirateGame main){
        this.main = main;

        Gdx.graphics.setTitle("Main Menu - York Pirates!");
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, 800, 480);

        // Layout Properties
        Container<Table> tableContainer = new Container<Table>();
        tableContainer.setFillParent(true);
        tableContainer.setPosition(0,0);
        tableContainer.align(Align.center);
        Table table = new Table();
        stage = new Stage(new FitViewport(1920,1080));



        screen_width = stage.getWidth();
        screen_height = stage.getHeight();

        // Debugging
        System.out.println(screen_width + ", " + screen_height);

        Label title = new Label("Rear Admirals", main.skin, "title");
        title.setAlignment(Align.center);

        TextButton combat_mode = new TextButton("Go to Combat Mode", main.skin);
        TextButton sailing_mode = new TextButton("Go to Sailing Mode", main.skin);
        TextButton college_mode = new TextButton("Go to College Screen", main.skin);
        TextButton department_mode = new TextButton("Go to Department Screen", main.skin);


        // Allows button to be clickable, and sets process for when clicked.
        combat_mode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new CombatScreen(main, new Ship(Brig, Derwent)));
                dispose();
            }
        });

        sailing_mode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                main.setScreen(main.sailing_scene);
                dispose();
            }
        });

        college_mode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                main.setScreen(new CollegeScreen(main, Derwent));
                dispose();
            }
        });
        department_mode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                main.setScreen(new DepartmentScreen(main, Chemistry));
                dispose();
            }
        });

//        table.setDebug(true);

        tableContainer.setActor(table);

        table.add(title).padBottom(screen_height/20).width(screen_width/2);
        table.row(); // Ends the current row
        table.add(sailing_mode).uniform().padBottom(screen_height/40).size(screen_width/2,screen_height/10);
        table.row();
        table.add(new Label("These are for demo purposes, to show implementation of combat and colleges.", main.skin));
        table.row();
        table.add(combat_mode).uniform().padBottom(screen_height/40).fill();
        table.row();
        table.add(college_mode).uniform().fill().padBottom(screen_height/40);
        table.row();
        table.add(department_mode).uniform().fill();

        stage.addActor(tableContainer);

        Gdx.input.setInputProcessor(stage);
        System.out.println("IP: stage");
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act();

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


