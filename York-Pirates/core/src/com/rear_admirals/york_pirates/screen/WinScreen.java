package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.base.BaseScreen;

public class WinScreen extends BaseScreen {

    public WinScreen(final PirateGame main){
        super(main);
        Label winLabel = new Label("CONGRATULATIONS! \n You won with "+ main.getPlayer().getPoints() + " points!", main.getSkin());
        mainStage.addActor(winLabel);
        winLabel.setPosition(viewwidth/2f,viewheight/2f, Align.center);
    }

    @Override
    public void update(float delta){

    }


}
