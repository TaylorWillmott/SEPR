package com.rear_admirals.york_pirates.Combat;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rear_admirals.york_pirates.PirateGame;

public class ShipCombat implements Screen {

    final PirateGame main;
    private Stage stage;
    private SpriteBatch batch;

    public ShipCombat (final PirateGame main){
        this.main = main;

        stage = new Stage();
        batch = new SpriteBatch();
        main.skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
        AttackButton button1 = new AttackButton("Basic",200f,50f,main.skin);
        AttackButton button2 = new AttackButton("Charge",350f,50f,main.skin);
        AttackButton button3 = new AttackButton("Rapid",500f,50f,main.skin);
        AttackButton button4 = new AttackButton("Board",200f,20f,main.skin);
        AttackButton button5 = new AttackButton("Ram",350f,20f,main.skin);
        AttackButton button6 = new AttackButton("FLEE",500f,20f,main.skin);


        MyShip myShip = new MyShip("ship1.png",75,275);
        MyShip enemyShip = new MyShip("ship2.png",450 ,325);

        stage.addActor(myShip);
        stage.addActor(enemyShip);
        stage.addActor(button1);
        stage.addActor(button2);
        stage.addActor(button3);
        stage.addActor(button4);
        stage.addActor(button5);
        stage.addActor(button6);

        Gdx.input.setInputProcessor(stage);

    }

	@Override
	public void render (float delta) {
	    Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        stage.draw();
        batch.end();
    }

	@Override
	public void show(){
    }
	
	@Override
	public void dispose () {
		stage.dispose();
	}

    @Override
    public void resize(int width, int height) {
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

