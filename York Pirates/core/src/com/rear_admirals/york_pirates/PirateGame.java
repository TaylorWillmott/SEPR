package com.rear_admirals.york_pirates;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
	SpriteBatch batch;
	ShapeRenderer shapeBatch;
	MoveableObject ship;
	OrthographicCamera cam =  new OrthographicCamera();
	Viewport viewport = new FitViewport(1920, 1080, cam);
	int x = 0;
	int y = 0;
	int a = 0;
	int speed = 3;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeBatch = new ShapeRenderer();
		//img = new Texture("ship.png");
		Texture shipTexture = new Texture("ship.png");
		ship = new MoveableObject(x, y, a, shipTexture);
		Gdx.graphics.setTitle("York Pirates!");
		//Gdx.graphics.setWindowedMode(4000, 3000);
	}

	private void customDraw(SpriteBatch batch, Texture img, int x, int y, int a) {
		batch.draw(img, x, y, img.getWidth()/2, img.getHeight()/2, img.getWidth(), img.getHeight(), 1, 1, a, 0,0, img.getWidth(), img.getHeight(), false, false);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(cam.combined);

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			ship.rotate(1);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			ship.rotate(-1);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			ship.angularMove(ship.a, speed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			ship.angularMove(ship.a, -speed);
		}

		cam.position.set(ship.x, ship.y, 0);
		cam.update();

		shapeBatch.begin(ShapeRenderer.ShapeType.Filled);
		shapeBatch.setColor(Color.GRAY);
		shapeBatch.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		shapeBatch.end();

		batch.begin();
		//batch.draw(img, x, y);
		ship.draw(batch);
		//batch.draw(img, x, y, img.getWidth()/2, img.getHeight()/2, img.getWidth(), img.getHeight(), 1, 1, a, 0,0, img.getWidth(), img.getHeight(), false, false);
		batch.end();
	}

	public void resize(int width, int height) { viewport.update(width, height); }

	@Override
	public void dispose () {
		batch.dispose();
		ship.tex.dispose();
	}

}
