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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rear_admirals.york_pirates.Attacks.Attack;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Player;
import com.rear_admirals.york_pirates.Ship;

import static com.rear_admirals.york_pirates.ShipType.Brig;

public class ShipCombat implements Screen {

    final PirateGame main;
    private Stage stage;
    private SpriteBatch batch;

    public ShipCombat (final PirateGame main){
        this.main = main;
        Ship playerShip = new Ship(Brig);
        Player player = new Player(playerShip);

        stage = new Stage();
        batch = new SpriteBatch();
        main.skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
        Table attack_table = new Table();
        attack_table.setPosition(Gdx.graphics.getWidth()/1.5f,50);
        AttackButton button1 = new AttackButton(player.attacks.get(0),main.skin);
        AttackButton button2 = new AttackButton("Charge",main.skin,"Insert Desc.");
        AttackButton button3 = new AttackButton("Rapid",main.skin,"Insert Desc.");
        AttackButton button4 = new AttackButton("Board",main.skin,"Insert Desc.");
        AttackButton button5 = new AttackButton("Ram",main.skin,"Insert Desc.");
        AttackButton button6 = new AttackButton("FLEE",main.skin,"Insert Desc.");

        attack_table.add(button1).pad(0,0,20,20).size(125,40).fill();
        attack_table.add(button2).pad(0,0,20,20).size(125,40).fill();
        attack_table.add(button3).pad(0,0,20,20).size(125,40).fill();
        attack_table.row();
        attack_table.add(button4).pad(0,0,20,20).size(125,40).fill();
        attack_table.add(button5).pad(0,0,20,20).size(125,40).fill();
        attack_table.add(button6).pad(0,0,20,20).size(125,40).fill();

        MyShip myShip = new MyShip("ship1.png",75,275);
        MyShip enemyShip = new MyShip("ship2.png",450 ,325);

        stage.addActor(myShip);
        stage.addActor(enemyShip);
        stage.addActor(attack_table);

        Gdx.input.setInputProcessor(stage);

    }

	@Override
	public void render (float delta) {
	    Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        stage.draw();
        stage.act();
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

