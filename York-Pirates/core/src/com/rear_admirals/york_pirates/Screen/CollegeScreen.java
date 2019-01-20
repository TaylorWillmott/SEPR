package com.rear_admirals.york_pirates.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Player;

public class CollegeScreen extends AbstractScreen {
    private Player player;
    public CollegeScreen(PirateGame main, College college){
        super(main);
        this.player = main.player;
        Table optionsTable = new Table();
        optionsTable.setFillParent(true);
        Label title = new Label("Welcome to " + college.name, main.skin, "title");
        final Label message = new Label("", main.skin);
        optionsTable.add(title);
        optionsTable.row();
        if (player.playerShip.getCollege().getAlly().contains(college)){
            final int toHeal = player.playerShip.getHealthMax() - player.playerShip.getHealth();
            TextButton heal = new TextButton("Repair Ship for "+Integer.toString(toHeal)+" gold", main.skin);
            if (toHeal == 0) { heal.setText("Your ship is already fully repaired!"); }
            heal.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (player.payGold(toHeal/10)) {
                        player.playerShip.setHealth(player.playerShip.getHealthMax());
                        message.setText("Successful repair");
                    }
                    else{
                        message.setText("You don't have the funds to repair your ship");
                    }
                }
            });
            optionsTable.add(heal);
        }
        optionsTable.row();
        optionsTable.add(message);




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
