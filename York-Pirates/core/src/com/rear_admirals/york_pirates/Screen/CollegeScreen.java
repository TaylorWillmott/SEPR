package com.rear_admirals.york_pirates.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rear_admirals.york_pirates.College;
import com.rear_admirals.york_pirates.PirateGame;

public class CollegeScreen extends AbstractScreen {

    public CollegeScreen(PirateGame main, College college){
        super(main);
        Table optionsTable = new Table();
        optionsTable.setFillParent(true);
        Label title = new Label("Welcome to " + college.name, main.skin, "title");
        optionsTable.add(title);
        optionsTable.row();
        if (main.player.playerShip.getCollege().getAlly().contains(college)){
            TextButton heal = new TextButton("Heal Ship", main.skin);
            optionsTable.add(heal);
        }




        mainStage.addActor(optionsTable);

        Gdx.input.setInputProcessor(mainStage);
    }

    @Override
    public void update(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            System.out.println("ESCAPE");
            main.setScreen(main.sailing_scene);
            dispose();
        }
    }
}
