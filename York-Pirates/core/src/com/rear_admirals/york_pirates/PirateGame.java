package com.rear_admirals.york_pirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rear_admirals.york_pirates.Screen.ShipSailing;

public class PirateGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;
    public Player player;
	public ShipSailing sailing_scene;

	public void create(){
		Gdx.graphics.setTitle("York Pirates!");
        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
		player = new Player();
        this.setScreen(new MainMenu(this));

	}


	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
