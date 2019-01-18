package com.rear_admirals.york_pirates.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rear_admirals.york_pirates.College;
import com.rear_admirals.york_pirates.PirateGame;

public class CollegeScreen implements Screen {
    private Stage menuStage;
    private float width;
    private float height;

    public CollegeScreen(PirateGame main, College college){
        width = 1920;
        height = 1080;
        menuStage = new Stage(new FitViewport(1920,1080));
        Table optionsTable = new Table();
        optionsTable.setFillParent(true);
        TextButton heal = new TextButton("Heal Ship", main.skin);
        optionsTable.add(heal);
        menuStage.addActor(optionsTable);

    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuStage.draw();
        menuStage.act();
   }

    @Override
    public void show(){

    }

    @Override
    public void dispose () {
        menuStage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        menuStage.getViewport().update(width,height);
        this.width = menuStage.getWidth();
        this.height = menuStage.getHeight();

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

}
