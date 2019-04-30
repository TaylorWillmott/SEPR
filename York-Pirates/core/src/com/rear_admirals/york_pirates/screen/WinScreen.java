package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.base.BaseScreen;

public class WinScreen extends BaseScreen {

    public WinScreen(final PirateGame main){
        super(main);
        Table messageTable = new Table();
        Label winLabel = new Label("CONGRATULATIONS!", main.getSkin());
        Label pointsLabel = new Label("You won with "+ main.getPlayer().getPoints() + " points!", main.getSkin());
        messageTable.add(winLabel);
        messageTable.row();
        messageTable.add(pointsLabel);
        mainStage.addActor(messageTable);
        messageTable.setPosition(viewwidth/2f,viewheight/2f, Align.center);
    }

    @Override
    public void update(float delta){

    }


}
