package com.rear_admirals.york_pirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.rear_admirals.york_pirates.ShipType.Brig;

public class ShipSailing {
    private SpriteBatch batch;
    private ShapeRenderer shapeBatch;
    private Ship ship;
    private OrthographicCamera cam;
    private Viewport viewport;

//    @Override
    public void create () {
        batch = new SpriteBatch();
        shapeBatch = new ShapeRenderer();
        ship = new Ship(Brig);
        ship.tex = new Texture("ship.png");
        cam = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, cam);
        Gdx.graphics.setTitle("York Pirates!");
        //Gdx.graphics.setWindowedMode(4000, 3000);
    }

//    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { ship.rotate(1); }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { ship.rotate(-1); }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) { ship.forward(); }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { ship.forward(-3); }

        cam.position.set(ship.pos.x, ship.pos.y, 0);
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

//    @Override
    public void resize(int width, int height) { viewport.update(width, height); }

//    @Override
    public void dispose () {
        batch.dispose();
        shapeBatch.dispose();
        ship.tex.dispose();
    }
}
