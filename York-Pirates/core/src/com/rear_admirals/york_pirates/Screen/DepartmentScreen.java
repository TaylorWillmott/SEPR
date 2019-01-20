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
import com.rear_admirals.york_pirates.*;

public class DepartmentScreen extends AbstractScreen {

    private Player player;

    public DepartmentScreen(final PirateGame main, final Department department){
        super(main);
        player = main.player;
        Table optionsTable = new Table();
        optionsTable.setFillParent(true);
        Label title = new Label(department.name, main.skin);
        TextButton upgrade = new TextButton("Upgrade Ship "+ department.getProduct() + " for " + department.getPrice() + " gold", main.skin);
        final Label message = new Label("", main.skin);
        final int toHeal = player.playerShip.getHealthMax() - player.playerShip.getHealth();
        TextButton heal = new TextButton("Repair Ship for "+Integer.toString(toHeal)+" gold", main.skin);
        upgrade.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                department.purchase();
            }
        });

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



