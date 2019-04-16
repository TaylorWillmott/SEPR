package com.rear_admirals.york_pirates;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rear_admirals.york_pirates.screen.MainMenu;
import com.rear_admirals.york_pirates.screen.SailingScreen;
import com.rear_admirals.york_pirates.screen.combat.attacks.DoubleShot;
import com.rear_admirals.york_pirates.screen.combat.attacks.ExplosiveShell;
import com.rear_admirals.york_pirates.screen.combat.attacks.GrapeShot;

public class PirateGame extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    private Skin skin;

	private Player player;
	public static Department Chemistry;
	public static Department Physics;
	public static Department Economics;

	public Preferences getSave_file() {
		return save_file;
	}

	public void setSave_file(Preferences save_file) {
		this.save_file = save_file;
	}

	private Preferences save_file;


	/**
	 * Keep track of the coordinates and angle of the player on the map
	 */
	private float sailingShipX;
	private float sailingShipY;
	private float sailingShipRotation;

	public float getSailingShipX() { return this.sailingShipX; }
	public float getSailingShipY() { return this.sailingShipY; }
	public float getSailingShipRotation() { return this.sailingShipRotation; }

	public void setSailingShipX(float sailingShipX) { this.sailingShipX = sailingShipX; }
	public void setSailingShipY(float sailingShipY) { this.sailingShipY = sailingShipY; }
	public void setSailingShipRotation(float sailingShipRotation) { this.sailingShipRotation = sailingShipRotation; }

	public void create(){
		this.skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
		batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        player = new Player();
		Chemistry = new Department("Chemistry", "attack", ExplosiveShell.attackExplosive, this);
		Physics = new Department("Physics", "defence", DoubleShot.attackDouble, this);
		Economics = new Department("Economics", "accuracy", GrapeShot.attackGrape, this);
        setScreen(new MainMenu(this));
		Gdx.app.setLogLevel(Application.LOG_INFO);
		save_file = Gdx.app.getPreferences("save_file");

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

	public Skin getSkin() { return this.skin; }

	public void setSkin(Skin skin) { this.skin = skin; }

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() { return this.player; }

}
