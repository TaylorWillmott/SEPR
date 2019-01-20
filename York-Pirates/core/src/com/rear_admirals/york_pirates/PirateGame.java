package com.rear_admirals.york_pirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rear_admirals.york_pirates.Screen.SailingScreen;

public class PirateGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;
    public Player player;
	public SailingScreen sailing_scene;
	public static Department Chemistry;
	public static Department Physics;

	public void create(){
		Gdx.graphics.setTitle("York Pirates!");
		this.skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
		batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        player = new Player();
		Chemistry = new Department("Chemistry", "Damage", this);
		Physics = new Department("Physics", "Defence", this);
		this.sailing_scene = new SailingScreen(this);
        setScreen(new MainMenu(this));




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
