package com.rear_admirals.york_pirates.Combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.*;
import com.rear_admirals.york_pirates.Attacks.*;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Player;
import com.rear_admirals.york_pirates.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class ShipCombat implements Screen {

    final PirateGame main;
    private Stage stage;
    float button_pad_bottom;
    float button_pad_right;
    public Label descriptionLabel;
    public float width;
    public float height;
    private Texture bg_texture;
    private Texture wood_texture;
    public Player player;
    public Ship enemy;
    private Stage attackStage;
    private Table completeAttackTable;
    private Image background;
    private Image background_wood;
    private Stack<Attack> combatStack;
    private TextButton textBox;
    private ProgressBar playerHP;
    private ProgressBar enemyHP;
    private Label playerHPLabel;
    private Label enemyHPLabel;
    private static List<Attack> enemyAttacks;
    private Attack currentAttack;


    public ShipCombat (final PirateGame main, Player player, Ship enemy){

        // This constructor also replaces the create function that a stage would typically have.

        this.main = main;
        this.player = player;
        this.enemy = enemy;
        this.combatStack = new Stack();

        stage = new Stage(new FitViewport(1920,1080));

        height = stage.getHeight();
        width = stage.getWidth();
        button_pad_bottom = height/24f;
        button_pad_right = width/32f;

        bg_texture = new Texture("water_texture.png");
        background = new Image(bg_texture);
        wood_texture = new Texture("wood_texture.png");
        background_wood = new Image(wood_texture);

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

        CombatShip myShip = new CombatShip(player.playerShip,"ship1.png", width/3);
        CombatShip enemyShip = new CombatShip(enemy,"ship2.png",width/3);

        Label shipName = new Label(player.playerShip.getName(),main.skin);
        playerHP = new ProgressBar(0, player.playerShip.getHealthMax(),0.1f,false,main.skin);
        playerHPLabel = new Label(player.playerShip.getHealth()+"/"+player.playerShip.getHealthMax(), main.skin);

        playerHP.getStyle().background.setMinHeight(playerHP.getPrefHeight()*2); //Setting vertical size of progress slider (Class implementation is slightly weird)
        playerHP.getStyle().knobBefore.setMinHeight(playerHP.getPrefHeight());

        Label enemyName = new Label("Enemy "+enemy.getName(),main.skin);
        enemyHP = new ProgressBar(0,enemy.getHealthMax(),0.1f,false,main.skin);
        enemyHPLabel = new Label(enemy.getHealth()+"/"+enemy.getHealthMax(), main.skin);

        enemyHP.setValue(enemy.getDefence());

        Table playerHPTable = new Table();
        Table enemyHPTable = new Table();

        playerHPTable.add(playerHPLabel).padRight(width/36f);
        playerHPTable.add(playerHP).width(width/5);

        enemyHPTable.add(enemyHPLabel).padRight(width/36f);
        enemyHPTable.add(enemyHP).width(width/5);

        Label screenTitle = new Label("Combat Mode", main.skin,"title");
        screenTitle.setAlignment(Align.center);

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
        buttonListener(fleeButton);

        descriptionLabel = new Label("Please choose your attack option", main.skin);
        descriptionLabel.setWrap(true);
        descriptionLabel.setAlignment(Align.center);

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

        textBox = new TextButton("WELCOME TO THE BATTLE", main.skin);
        rootTable.row().width(width*0.8f);
        rootTable.add(screenTitle).colspan(2);
        rootTable.row();
        rootTable.add(shipName);
        rootTable.add(enemyName);
        rootTable.row().fillX();
        rootTable.add(myShip);
        rootTable.add(enemyShip);
        rootTable.row();
        rootTable.add(playerHPTable);
        rootTable.add(enemyHPTable);
        rootTable.row().height(height/54f);
        rootTable.add().colspan(2);
        rootTable.row();
        rootTable.add(textBox).colspan(2).fillX().height(height/9f).pad(2*height/27,0,2*height/27,0);
        tableContainer.setActor(rootTable);

        // Creates the overlay stage for choosing a move on players turn.
        attackStage = new Stage(new FitViewport(1920,1080));
        completeAttackTable = new Table();
        completeAttackTable.setFillParent(true);
        completeAttackTable.align(Align.bottom);
        completeAttackTable.row().expandX().padBottom(2*height/27f);
        completeAttackTable.add(descriptionTable).width(width/2);
        completeAttackTable.add(attackTable).width(width/2).bottom();
        background_wood.setVisible(false);
        completeAttackTable.setVisible(false);
        attackStage.addActor(background_wood);
        attackStage.addActor(completeAttackTable);


//        stage.addActor(myShip);
//        stage.addActor(enemyShip);
        stage.addActor(background);
        stage.addActor(tableContainer);

        // Setup Enemy Attacks
        enemyAttacks = new ArrayList<Attack>();
        enemyAttacks.add(Attack.attackMain);
        enemyAttacks.add(Attack.attackGrape);
        enemyAttacks.add(Attack.attackSwivel);

        currentAttack = null;

        Gdx.input.setInputProcessor(stage);
        toggleAttackStage();


        //Allow debugging of layout
//        descriptionTable.setDebug(true);
//        attackTable.setDebug(true);
        completeAttackTable.setDebug(true);
        rootTable.setDebug(true);
//        myShip.setDebug(true);
//        enemyShip.setDebug(true);
//        tableContainer.setDebug(true);
        System.out.println(width + "," + height + " AND " + Gdx.graphics.getWidth() + "," + Gdx.graphics.getHeight());

    }

	@Override
	public void render (float delta) {
//	    Gdx.gl.glClearColor((float) 0.26,(float) 0.52,(float) 0.95,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
        stage.draw();
        stage.act();
        attackStage.draw();
        attackStage.act();

//        Gdx.input.setInputProcessor(attackStage);

//        stage.setDebugAll(true);
//        batch.end();
    }

	@Override
	public void show(){
    }
	
	@Override
	public void dispose () {
        stage.dispose();
        attackStage.dispose();
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

    public void toggleAttackStage(){
        // This method should be called at the start and end of a player move (overlays attack menu on the screen)
        if (background_wood.isVisible()) {
            background_wood.setVisible(false);
            completeAttackTable.setVisible(false);
            Gdx.input.setInputProcessor(stage);
        }
        else{
            background_wood.setVisible(true);
            completeAttackTable.setVisible(true);
            Gdx.input.setInputProcessor(attackStage);
        }
    }

    // Combat Handler
    //  This function handles the ship combat
    public void combatHandler(BattleEvent status){
        if (!combatStack.empty()){
            currentAttack = combatStack.pop();
        }
        if (enemy.getHealth() <= 0) {
            System.out.println(enemy.getHealth());
            status = BattleEvent.ENEMY_DIES;
        }
        switch(status) {
            case NONE:
                return;
            case PLAYER_MOVE:
                toggleAttackStage();
                if (currentAttack.isSkipMoveStatus()){
                    currentAttack.setSkipMoveStatus(false);
                    dialog("Charging attack.");
                    combatStack.push(currentAttack);
                }
                else if (currentAttack.getName() == "FLEE"){
                    if (currentAttack.doAttack(player.playerShip, enemy) == 1){
                        dialog("Flee successful!");
                        combatHandler(BattleEvent.PLAYER_FLEES);
                    }
                    else{
                        dialog("Flee failed.");
                    }
                }
                else {
                    int damage = currentAttack.doAttack(player.playerShip, enemy); // Calls the attack function on the player and stores damage output
                    if (damage == 0){
                        dialog("Attack Missed");
                        System.out.println("ATTACK MISSED, damage dealt: " + damage + ", Player Ship Health: "+ player.playerShip.getHealth() + ", Enemy Ship Health: " + enemy.getHealth());
                    }
                    else{
                        dialog("You dealt " + damage + "with " + currentAttack.getName() + "!");
                        updateHP();
                        System.out.println("ATTACK SUCCESSFUL, damage dealt: " + damage + ", Player Ship Health: "+ player.playerShip.getHealth() + ", Enemy Ship Health: " + enemy.getHealth());

                    }
                    // This selection statement returns Special Charge Attacks to normal state
                    if (currentAttack.isSkipMove()) {
                        currentAttack.setSkipMoveStatus(true);
                    }
                }
                if (player.playerShip.getHealth() <= 0) {
                    combatHandler(BattleEvent.PLAYER_DIES);
                }
                if (enemy.getHealth() <= 0) {
                    combatHandler(BattleEvent.ENEMY_DIES);
                }
                combatHandler(BattleEvent.ENEMY_MOVE);
                break;
            case ENEMY_MOVE:
                System.out.println("Running enemy move");
                Attack enemyAttack = enemyAttacks.get(ThreadLocalRandom.current().nextInt(0,3));
                int damage = enemyAttack.doAttack(enemy, player.playerShip);
                System.out.println("ENEMY ATTACK SUCCESSFUL, damage dealt: " + damage + ", Player Ship Health: "+ player.playerShip.getHealth() + ", Enemy Ship Health: " + enemy.getHealth());
                dialog("Enemy "+enemy.getName()+ "dealt " + damage + " with " + enemyAttack.getName()+ "!");
                updateHP();
                if (combatStack.isEmpty()){
                    toggleAttackStage();
                }
                else{
                    combatHandler(BattleEvent.PLAYER_MOVE);
                }
                break;
            case PLAYER_DIES:
                break;
            case ENEMY_DIES:
                dialog("Congratulations, you have defeated Enemy " + enemy.getName());
                player.addGold(10);
                player.addPoints(10);
                break;
            case PLAYER_FLEES:
                break;

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
                descriptionLabel.setText("Please choose your attack option");
            };
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button.setText("Attacking");
                //Insert Delay?
                combatStack.push(button.attack);
                combatHandler(BattleEvent.PLAYER_MOVE);
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
            }
        });
    }

    //TODO Add animation to HP bar
    public void updateHP(){
        enemyHPLabel.setText(enemy.getHealth()+"/"+enemy.getHealthMax());
        enemyHP.setValue(enemy.getHealth());
        playerHPLabel.setText(player.playerShip.getHealth()+"/"+player.playerShip.getHealthMax());
        playerHP.setValue(player.playerShip.getHealth());
    }
    //TODO Add animation to dialog box
    public void dialog(String message){
        textBox.setText(message);
    }
}
