package com.rear_admirals.york_pirates.Combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.*;
import com.rear_admirals.york_pirates.Attacks.Attack;
import com.rear_admirals.york_pirates.Attacks.Flee;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Player;
import com.rear_admirals.york_pirates.Ship;

import static com.rear_admirals.york_pirates.ShipType.Brig;

public class ShipCombat implements Screen {

    final PirateGame main;
    private Stage stage;
    float button_pad_bottom;
    float button_pad_right;
    public Label descriptionLabel;
    public float width;
    public float height;
    private Texture bg_texture;
    public Player player;
    public Ship enemy;

    public ShipCombat (final PirateGame main, Player player, Ship enemy){

        // This constructor also replaces the create function that a stage would typically have.

        this.main = main;
        this.player = player;
        this.enemy = enemy;

        stage = new Stage(new FitViewport(1920,1080));

        height = stage.getHeight();
        width = stage.getWidth();

        bg_texture = new Texture("water_texture.png");
        Image background = new Image(bg_texture);

        Container<Table> tableContainer = new Container<Table>();
//        tableContainer.setSize(width,height);
        tableContainer.setFillParent(true);
        tableContainer.setPosition(0,0);
        tableContainer.align(Align.bottom);
//        tableContainer.fillX();

        main.skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));

        Table rootTable = new Table();
        Table descriptionTable = new Table();
        Table attackTable = new Table();

        System.out.println(width + "," + height + " AND " + Gdx.graphics.getWidth() + "," + Gdx.graphics.getHeight());

        // Instantiation of the combat buttons. Attack and Flee are default attacks, the rest can be modified by within player class.

        final AttackButton button1 = new AttackButton(Attack.attackMain, main.skin);
        buttonListener(button1);
        final AttackButton button2 = new AttackButton(player.attacks.get(0), main.skin);
        buttonListener(button2);
        final AttackButton button3 = new AttackButton(player.attacks.get(1), main.skin);
        buttonListener(button3);
        final AttackButton button4 = new AttackButton(player.attacks.get(2), main.skin);
        buttonListener(button4);
        final AttackButton fleeButton = new AttackButton(Flee.attackFlee, main.skin, "flee");
        buttonListener(fleeButton, "Attempting to flee");

        descriptionLabel = new Label("Please choose your attack", main.skin);
        descriptionLabel.setWrap(true);
        descriptionLabel.setAlignment(Align.center);

        button_pad_bottom = height/24f;
        button_pad_right = width/32f;

        descriptionTable.center();
        descriptionTable.add(descriptionLabel).uniform().pad(0,button_pad_right,0,button_pad_right).size(width/2 - button_pad_right*2, height/12).top();
        descriptionTable.row();
        descriptionTable.add(fleeButton).uniform().bottom();

        attackTable.row();
        attackTable.add(button1).uniform().width(width/5).padRight(button_pad_right);
        attackTable.add(button2).uniform().width(width/5);
        attackTable.row().padTop(button_pad_bottom);
        attackTable.add(button3).uniform().width(width/5).padRight(button_pad_right);
        attackTable.add(button4).uniform().width(width/5);

        CombatShip myShip = new CombatShip(player.playerShip,"ship1.png", width/3);
        CombatShip enemyShip = new CombatShip(enemy,"ship2.png",width/3);

        Label shipName = new Label(player.playerShip.getName(),main.skin);
        ProgressBar playerHP = new ProgressBar(0, player.playerShip.getHealthMax(),0.1f,false,main.skin);
        Label playerHPLabel = new Label(player.playerShip.getHealth()+"/"+player.playerShip.getHealthMax(), main.skin);

        playerHP.getStyle().background.setMinHeight(playerHP.getPrefHeight()*2); //Setting vertical size of progress slider (Class implementation is slightly weird)
        playerHP.getStyle().knobBefore.setMinHeight(playerHP.getPrefHeight());

        Label enemyName = new Label("Enemy "+enemy.getName(),main.skin);
        ProgressBar enemyHP = new ProgressBar(0,enemy.getHealthMax(),0.1f,false,main.skin);
        Label enemyHPLabel = new Label(enemy.getHealth()+"/"+enemy.getHealthMax(), main.skin);

        enemyHP.setValue(enemy.getDefence());

        Table playerHPTable = new Table();
        Table enemyHPTable = new Table();

        playerHPTable.add(playerHPLabel).padRight(width/36f);
        playerHPTable.add(playerHP).width(width/5);

        enemyHPTable.add(enemyHPLabel).padRight(width/36f);
        enemyHPTable.add(enemyHP).width(width/5);

        Label screenTitle = new Label("Combat Mode", main.skin,"title");

        rootTable.add(screenTitle).colspan(2);
        rootTable.row();
        rootTable.add(shipName);
        rootTable.add(enemyName);
        rootTable.row().fillX();
        rootTable.add(myShip);
        rootTable.add(enemyShip);
        rootTable.row().padBottom(height/18f);
        rootTable.add(playerHPTable);
        rootTable.add(enemyHPTable);
        rootTable.row().expandX().padBottom(height/12f);
        rootTable.add(descriptionTable).width(width/2);
        rootTable.add(attackTable).width(width/2).bottom();

        tableContainer.setActor(rootTable);

//        stage.addActor(myShip);
//        stage.addActor(enemyShip);
        stage.addActor(background);
        stage.addActor(tableContainer);
        Gdx.input.setInputProcessor(stage);

        //Allow debugging of layout

//        descriptionTable.setDebug(true);
//        attackTable.setDebug(true);
//        rootTable.setDebug(true);
//        myShip.setDebug(true);
//        enemyShip.setDebug(true);
//        tableContainer.setDebug(true);
    }

	@Override
	public void render (float delta) {
//	    Gdx.gl.glClearColor((float) 0.26,(float) 0.52,(float) 0.95,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
        stage.draw();
        stage.act();

//        stage.setDebugAll(true);
//        batch.end();
    }

	@Override
	public void show(){
    }
	
	@Override
	public void dispose () {
        stage.dispose();
        bg_texture.dispose();
	}

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
        this.width = stage.getWidth();
        this.height = stage.getHeight();

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

    // Combat Handler
    public void combatHandler(String status){
        switch(status) {
            case "none":
                return;
            case "player attack":

            case "enemy attack":

            case "player charge turn":

            case "enemy charge turn":

            case "player dies":

            case "enemy dies":

            case "player flees":
                

        }
    }

    // Button Listener Classes

    public void buttonListener(final AttackButton button){
        button.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                descriptionLabel.setText(button.getDesc());
            };
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                descriptionLabel.setText("Choose an option");
            };
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button.setText("Attacking");
                button.attack.doAttack(player.playerShip, enemy);
            }
        });
    }
    public void buttonListener(final AttackButton button, final String message){
        button.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                descriptionLabel.setText(button.getDesc());
            };
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                descriptionLabel.setText("Choose an option");
            };
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button.setText(message);
                button.attack.doAttack(player.playerShip, enemy);
            }
        });
    }
}
