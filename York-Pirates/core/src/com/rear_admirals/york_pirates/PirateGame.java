package com.rear_admirals.york_pirates;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PirateGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private ShapeRenderer shapeBatch;
	private Ship ship;
	private OrthographicCamera cam;
	private Viewport viewport;

	public static shipType sloop = new shipType("Sloop", 4, 4, 7);
	public static shipType brig = new shipType("Brig", 5, 5, 5);
	public static shipType galleon = new shipType("Galleon", 6, 6, 3);

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeBatch = new ShapeRenderer();
		ship = new Ship(brig);
		ship.tex = new Texture("ship.png");
		cam = new OrthographicCamera();
		viewport = new FitViewport(1920, 1080, cam);
		Gdx.graphics.setTitle("York Pirates!");
		//Gdx.graphics.setWindowedMode(4000, 3000);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { ship.rotate(1); }
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { ship.rotate(-1); }
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) { ship.forward(); }
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { ship.forward(-3); }

		cam.position.set(ship.x, ship.y, 0);
		cam.update();

		batch.setProjectionMatrix(cam.combined);

		shapeBatch.begin(ShapeRenderer.ShapeType.Filled);
		shapeBatch.setColor(Color.GRAY);
		shapeBatch.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		shapeBatch.end();

		batch.begin();
		ship.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) { viewport.update(width, height); }

	@Override
	public void dispose () {
		batch.dispose();
		shapeBatch.dispose();
		ship.tex.dispose();
	}

	private void customDraw(SpriteBatch batch, Texture img, int x, int y, int a) {
		batch.draw(img, x, y, img.getWidth() >> 1, img.getHeight() >> 1, img.getWidth(), img.getHeight(), 1, 1, a, 0,0, img.getWidth(), img.getHeight(), false, false);
	}

}
