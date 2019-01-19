package com.rear_admirals.york_pirates.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rear_admirals.york_pirates.PirateGame;

public abstract class AbstractScreen implements Screen {

    protected Stage mainStage;
    protected Stage uiStage;
    public final int viewwidth = 1920;
    public final int viewheight = 1080;
    private boolean paused;
    public PirateGame main;

    public AbstractScreen(PirateGame game){
        this.main = game;
        paused = false;
        mainStage = new Stage(new FitViewport(viewwidth, viewheight));
        uiStage = new Stage(new FitViewport(viewwidth, viewheight));

    }

    public abstract void update(float delta);

    public void render (float delta) {
        uiStage.act(delta);

        if (!isPaused()){
            mainStage.act(delta);
            update(delta);
        }
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainStage.draw();
        uiStage.draw();
    }

    //Pause Method

    public void setPaused(boolean b){
        paused = b;
    }

    public boolean isPaused() {
        return paused;
    }

    public void togglePaused(){
        paused = !paused;
    }

    @Override
    public void show(){

    }

    @Override
    public void dispose () {
        mainStage.dispose();
        uiStage.dispose();
    }

    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height);
        mainStage.getViewport().update(width, height);
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

