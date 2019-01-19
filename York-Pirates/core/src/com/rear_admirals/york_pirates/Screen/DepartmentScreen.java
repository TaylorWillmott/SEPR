package com.rear_admirals.york_pirates.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rear_admirals.york_pirates.College;
import com.rear_admirals.york_pirates.Department;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Ship;

public class DepartmentScreen extends AbstractScreen {

    public Ship playerShip;

    public DepartmentScreen(final PirateGame main, final Department department){
        super(main);
        playerShip = main.player.getPlayerShip();
        Table optionsTable = new Table();
        optionsTable.setFillParent(true);
        Label title = new Label(department.name, main.skin);
        TextButton upgrade = new TextButton("Upgrade Ship "+ department.name, main.skin);
        TextButton heal = new TextButton("Heal Ship", main.skin);
        Label message = new Label("", main.skin);

        upgrade.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                department.purchase();
            }
        });

        heal.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (main.player.payGold(10)) {
                        playerShip.setHealth(playerShip.getHealthMax());
                    }
                    else{

                    }
                }
            });

        optionsTable.add(title);
        optionsTable.row();
        optionsTable.add(upgrade);
        optionsTable.row();
        optionsTable.add(heal);
        mainStage.addActor(optionsTable);
        Gdx.input.setInputProcessor(mainStage);

    }

    @Override
    public void update(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            System.out.println("ESCAPE");
            main.setScreen(main.sailing_scene);
        }
    }



}



