package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.rear_admirals.york_pirates.PirateGame;
import com.rear_admirals.york_pirates.Player;
import com.rear_admirals.york_pirates.base.BaseScreen;
import com.rear_admirals.york_pirates.minigame.MiniGameScreen;
import com.rear_admirals.york_pirates.screen.combat.CombatScreen;
import com.rear_admirals.york_pirates.Ship;
import com.rear_admirals.york_pirates.screen.combat.attacks.Attack;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.List;

import static com.rear_admirals.york_pirates.PirateGame.Chemistry;
import static com.rear_admirals.york_pirates.ShipType.*;
import static com.rear_admirals.york_pirates.College.*;

public class MainMenu extends BaseScreen {
    private Stage stage;

    private Texture menuBackground = new Texture("menuBackground.png");

    public MainMenu(final PirateGame pirateGame){
        super(pirateGame);

        // Layout Properties
        Container<Table> tableContainer = new Container<Table>();
        tableContainer.setFillParent(true);
        tableContainer.setPosition(0,0);
        tableContainer.align(Align.center);
        Table table = new Table();
        stage = new Stage(new FitViewport(1920,1080));

        float screen_width = stage.getWidth();
        float screen_height = stage.getHeight();

        // Debugging
        Gdx.app.debug("Screen Dimensions", screen_width + ", " + screen_height);

        //TODO Remove this when redoing menu buttons as it is now redundant.
        Label title = new Label("", pirateGame.getSkin(), "title");
        title.setAlignment(Align.center);

        Image background = new Image(menuBackground);
        stage.addActor(background);
        background.setSize(viewwidth, viewheight);

        final TextButton new_game = new TextButton("New Game", pirateGame.getSkin()); // Starts sailing mode.
        final TextButton load_game = new TextButton("Load Game", pirateGame.getSkin());


        load_game.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                // SHOW SAVES
                try {
                    loadFile(pirateGame.getSave_file());
                    pirateGame.setScreen(new SailingScreen(pirateGame, false));
                    dispose();
                }catch (Exception e){
                    Gdx.app.log("load", "No save file found");
                    load_game.setText("No save found.");
                }


            }
        });
        new_game.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
//                Stage new_game_stage = new Stage();
//                Table new_game_table = new Table();
//                new_game_table.setFillParent(true);
//                new_game_table.add(new TextField("Enter New World Name:", pirateGame.getSkin()));
//                new_game_table.add();
//                new_game_stage.addActor(new_game_table);
                pirateGame.setScreen(new SailingScreen(pirateGame, true));
                dispose();
            }
        });


        TextButton combat_mode = new TextButton("Go to Combat Mode", pirateGame.getSkin());
        TextButton college_mode = new TextButton("Go to College screen", pirateGame.getSkin());
        TextButton department_mode = new TextButton("Go to Department screen", pirateGame.getSkin());
        TextButton miniGame_mode = new TextButton("Start Mini Game",pirateGame.getSkin());

        // Allows button to be clickable, and sets process for when clicked.
        combat_mode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pirateGame.setScreen(new CombatScreen(pirateGame, new Ship(Brig, Derwent)));
                dispose();
            }
        });

//        new_game.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y){
//                pirateGame.setScreen(new SailingScreen(pirateGame, true));
//                dispose();
//            }
//        });

        college_mode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                pirateGame.setScreen(new CollegeScreen(pirateGame, Derwent));
                dispose();
            }
        });

        department_mode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                pirateGame.setScreen(new DepartmentScreen(pirateGame, Chemistry));
                dispose();
            }
        });
        miniGame_mode.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                pirateGame.setScreen(new MiniGameScreen(pirateGame));
                dispose();
            }
        });

        tableContainer.setActor(table);

        table.add(title).padBottom(viewwidth/20).width(viewwidth/2);
        table.row(); // Ends the current row
        table.add(new_game).uniform().padBottom(viewheight/40).size(viewwidth/2,viewheight/10);
        table.row();
        table.add(load_game).uniform().padBottom(viewheight/40).size(viewwidth/2,viewheight/10);
        table.row();
        table.add(new Label("These are for demo purposes, to show implementation of combat and colleges.", pirateGame.getSkin()));
        table.row();
        table.add(combat_mode).uniform().padBottom(viewheight/40).fill();
        table.row();
        table.add(college_mode).uniform().fill().padBottom(viewheight/40);
        table.row();
        table.add(department_mode).uniform().fill().padBottom(viewheight/40);
        table.row();
        table.add(miniGame_mode).uniform().fill().padBottom(viewheight/40);

        stage.addActor(tableContainer);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float delta) { }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act();

    }

    private void loadFile(Preferences file){

        pirateGame.setPlayer(new Player());
        ArrayList<Attack> equippedAttacks;
        ArrayList<Attack> ownedAttacks;


        pirateGame.getPlayer().setPlayerShip(new Ship(file.getFloat("atkMultiplier"), file.getInteger("defence"), file.getFloat("accMultiplier"), Brig, Derwent, file.getString("name"), false));
        pirateGame.getPlayer().getPlayerShip().setHullHealth(file.getInteger("hull health"));
        pirateGame.getPlayer().getPlayerShip().setSailsHealth(file.getInteger("sail health"));
        pirateGame.getPlayer().setGold(file.getInteger("gold"));
        pirateGame.getPlayer().setPoints(file.getInteger("points"));

        try {
            System.out.println("about to serialize");
            equippedAttacks = SerializationUtils.deserialize(Base64.getDecoder().decode(file.getString("equipped attacks")));

            pirateGame.getPlayer().setEquippedAttacks(equippedAttacks);
        }catch (SerializationException a){}
        try {
            System.out.println(file.getString("owned attacks"));
            ownedAttacks = SerializationUtils.deserialize(Base64.getDecoder().decode(file.getString("owned attacks")));

            pirateGame.getPlayer().setEquippedAttacks(ownedAttacks);
        }catch (SerializationException a){}

        Derwent = SerializationUtils.deserialize(Base64.getDecoder().decode(file.getString("derwent")));
        Vanbrugh = SerializationUtils.deserialize(Base64.getDecoder().decode(file.getString("vanbrugh")));
        James = SerializationUtils.deserialize(Base64.getDecoder().decode(file.getString("james")));
        Alcuin = SerializationUtils.deserialize(Base64.getDecoder().decode(file.getString("alcuin")));
        Wentworth = SerializationUtils.deserialize(Base64.getDecoder().decode(file.getString("wentworth")));


        pirateGame.setSailingShipX(file.getFloat("shipX"));
        pirateGame.setSailingShipY(file.getFloat("shipY"));
        pirateGame.setSailingShipRotation(file.getFloat("shipRotation"));

    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}


