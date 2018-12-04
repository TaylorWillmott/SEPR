package com.rear_admirals.york_pirates.Combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.rear_admirals.york_pirates.Attacks.Attack;
import com.rear_admirals.york_pirates.Attacks.Flee;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Player;
import com.rear_admirals.york_pirates.Ship;

import static com.rear_admirals.york_pirates.ShipType.Brig;

public class ShipCombat implements Screen {

    final PirateGame main;
    private Stage stage;
    private SpriteBatch batch;
    float button_height;
    float button_width;
    float button_pad_bottom;
    float button_pad_right;


    public ShipCombat (final PirateGame main){
        this.main = main;
        Player player = new Player();

        stage = new Stage();
        batch = new SpriteBatch();
        main.skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
        Table attack_table = new Table();
        attack_table.setPosition(Gdx.graphics.getWidth()/1.5f,Gdx.graphics.getWidth()/9.6f);

        AttackButton button1 = new AttackButton(Attack.attackMain, main.skin);
        AttackButton button2 = new AttackButton(player.attacks.get(0), main.skin);
        AttackButton button3 = new AttackButton(player.attacks.get(1), main.skin);
        AttackButton button4 = new AttackButton(player.attacks.get(2), main.skin);
        AttackButton button5 = new AttackButton(player.attacks.get(3), main.skin);
        AttackButton button6 = new AttackButton(Flee.attackFlee, main.skin);

        button_height = Gdx.graphics.getHeight()/12f;
        button_width = Gdx.graphics.getWidth()/5.12f;
        button_pad_bottom = Gdx.graphics.getHeight()/24f;
        button_pad_right = Gdx.graphics.getWidth()/32f;

        attack_table.add(button1).pad(0,0,button_pad_bottom,button_pad_right).size(button_width,button_height).fill();
        attack_table.add(button2).pad(0,0,button_pad_bottom,button_pad_right).size(button_width,button_height).fill();
        attack_table.add(button3).pad(0,0,button_pad_bottom,button_pad_right).size(button_width,button_height).fill();
        attack_table.row();
        attack_table.add(button4).pad(0,0,button_pad_bottom,button_pad_right).size(button_width,button_height).fill();
        attack_table.add(button5).pad(0,0,button_pad_bottom,button_pad_right).size(button_width,button_height).fill();
        attack_table.add(button6).pad(0,0,button_pad_bottom,button_pad_right).size(button_width,button_height).fill();

        CombatShip myShip = new CombatShip(player.playerShip,"ship1.png",Gdx.graphics.getHeight()/8.5f,Gdx.graphics.getHeight()/1.7f);
        CombatShip enemyShip = new CombatShip(new Ship(Brig, "enemy"),"ship2.png",Gdx.graphics.getHeight()/1.4f ,Gdx.graphics.getHeight()/1.5f);

        stage.addActor(myShip);
        stage.addActor(enemyShip);
        stage.addActor(attack_table);

        Gdx.input.setInputProcessor(stage);

    }

	@Override
	public void render (float delta) {
	    Gdx.gl.glClearColor((float) 0.26,(float) 0.52,(float) 0.95,1);
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
        stage.getViewport().update(width,height);
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

