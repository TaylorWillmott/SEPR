package com.rear_admirals.york_pirates.Combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private SpriteBatch batch;
    float button_pad_bottom;
    float button_pad_right;
    public Label descriptionLabel;
    private float width;
    private float height;


    public ShipCombat (final PirateGame main){

        // This constructor also replaces the create function that a stage would typically have.

        this.main = main;
        Player player = new Player();
        stage = new Stage(new ScreenViewport());

        height = stage.getHeight();
        width = stage.getWidth();

        Container<Table> tableContainer = new Container<Table>();
        tableContainer.setSize(width,height);
        tableContainer.setPosition(0,0);
        tableContainer.align(Align.bottom);
//        tableContainer.fillX();

        main.skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));

        Table leftTable = new Table();
        Table attackTable = new Table();
        Table rootTable = new Table();

        System.out.println(width + "," + height + " AND " + Gdx.graphics.getWidth() + "," + Gdx.graphics.getHeight());

        final AttackButton button1 = new AttackButton(Attack.attackMain, main.skin);
        button1.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                descriptionLabel.setText(button1.getDesc());
            };
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                descriptionLabel.setText("Choose an option");
            };
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button1.setText("Attacking");
//                attack.doAttack();
            }
        });

        final AttackButton button2 = new AttackButton(player.attacks.get(0), main.skin);
        button2.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                descriptionLabel.setText(button2.getDesc());
            };
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                descriptionLabel.setText("Choose an option");
            };
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button2.setText("Attacking");
//                attack.doAttack();
            }
        });

        final AttackButton button3 = new AttackButton(player.attacks.get(1), main.skin);
        button3.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                descriptionLabel.setText(button3.getDesc());
            };
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                descriptionLabel.setText("Choose an option");
            };
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button3.setText("Attacking");
//                attack.doAttack();
            }
        });

        final AttackButton button4 = new AttackButton(player.attacks.get(2), main.skin);
        button4.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                descriptionLabel.setText(button4.getDesc());
            };
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                descriptionLabel.setText("Choose an option");
            };
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button4.setText("Attacking");
//                attack.doAttack();
            }
        });

        final AttackButton fleeButton = new AttackButton(Flee.attackFlee, main.skin);
        fleeButton.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                descriptionLabel.setText(fleeButton.getDesc());
            };
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                descriptionLabel.setText("Choose an option");
            };
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fleeButton.setText("Attacking");
//                attack.doAttack();
            }
        });

        descriptionLabel = new Label("Please choose your attack", main.skin);
        descriptionLabel.setWrap(true);

        button_pad_bottom = height/24f;
        button_pad_right = width/32f;

        leftTable.center();
        leftTable.add(descriptionLabel).uniform().expandX().left().pad(0,button_pad_right,0,button_pad_right);
        leftTable.row();
        leftTable.add(fleeButton).uniform().center();


        attackTable.add(button1).uniform().width(width/5);
        attackTable.add(button2).uniform().width(width/5);
        attackTable.row();
        attackTable.add(button3).uniform().width(width/5);
        attackTable.add(button4).uniform().width(width/5);

        CombatShip myShip = new CombatShip(player.playerShip,"ship1.png");
        CombatShip enemyShip = new CombatShip(new Ship(Brig, "enemy"),"ship2.png");

        rootTable.row().expandX().fillX();
        rootTable.add(leftTable).width(width/2);
        rootTable.add(attackTable).width(width/2);
        rootTable.padBottom(height/10f);

        tableContainer.setActor(rootTable);

        stage.addActor(myShip);
        stage.addActor(enemyShip);
        stage.addActor(tableContainer);
        Gdx.input.setInputProcessor(stage);

        //Allow debugging of layout
        leftTable.setDebug(true);
        attackTable.setDebug(true);
        rootTable.setDebug(false);
        myShip.setDebug(true);
        enemyShip.setDebug(true);
        tableContainer.setDebug(true);
    }

	@Override
	public void render (float delta) {
	    Gdx.gl.glClearColor((float) 0.26,(float) 0.52,(float) 0.95,1);
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
}
