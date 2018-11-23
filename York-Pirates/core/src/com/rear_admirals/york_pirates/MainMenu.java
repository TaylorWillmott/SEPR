package com.rear_admirals.york_pirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.rear_admirals.york_pirates.Combat.ShipCombat;

public class MainMenu implements Screen {

    final PirateGame main;
    OrthographicCamera camera;

    public MainMenu(final PirateGame main){
        this.main = main;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        main.batch.setProjectionMatrix(camera.combined);

        main.batch.begin();
        main.font.draw(main.batch, "Welcome to Rear Admirals!!! ", 100, 150);
        main.font.draw(main.batch, "Tap anywhere to begin!", 100, 100);
        main.batch.end();

        if (Gdx.input.isTouched()) {
            main.setScreen(new ShipCombat(main));
            dispose();
        }
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


