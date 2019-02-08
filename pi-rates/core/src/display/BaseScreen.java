package display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import game_manager.GameManager;

public abstract class BaseScreen implements Screen {

    protected GameManager game;

    protected Stage stage;
    protected Stage pauseStage;
    protected boolean gamePaused;


    protected final int viewwidth = 1920;
    protected final int viewheight = 1080;
    private Texture pauseBackgroundTexture;
    private Image pauseBackgroundImage;

    private Slider masterSlider;
    private Slider soundSlider;
    private Slider musicSlider;
    private Label masterLabel;
    private Label soundLabel;
    private Label musicLabel;

    private Music music;

    public BaseScreen(GameManager pirateGame){
        this.game = pirateGame;
        this.stage = new Stage(new FitViewport(this.viewwidth, this.viewheight));
        this.pauseStage = new Stage(new FitViewport(this.viewwidth, this.viewheight));
        this.gamePaused = false;


        // Sets up PauseStage
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        pauseBackgroundTexture = new Texture("shopBackground.png");
        pauseBackgroundImage = new Image(pauseBackgroundTexture);
        pauseBackgroundImage.getColor().set(1f,1f,1f,0.75f);

        masterSlider = new Slider(0f, 1f, 0.01f, false, skin);
        soundSlider = new Slider(0f, 1f, 0.01f, false, skin);
        musicSlider = new Slider(0f, 1f, 0.01f, false, skin);

        masterSlider.setValue(game.getMasterValue());
        soundSlider.setValue(game.getSoundValue());
        musicSlider.setValue(game.getMusicValue());

        masterLabel = new Label((int)(game.getMasterValue() * 100) + " / " + 100, skin);
        soundLabel = new Label((int)(game.getSoundValue() * 100) + " / " + 100, skin);
        musicLabel = new Label((int)(game.getMusicValue() * 100) + " / " + 100, skin);

        Table table = new Table();
        table.setFillParent(true);
        table.add(new Label("PAUSED", skin)).colspan(3);
        table.row().uniform();
        table.add(new Label("Master Volume", skin));
        table.add(masterSlider);
        table.add(masterLabel);
        table.row().uniform();
        table.add(new Label("Sound Effects Volume", skin));
        table.add(soundSlider);
        table.add(soundLabel);
        table.row().uniform();
        table.add(new Label("Music Volume", skin));
        table.add(musicSlider);
        table.add(musicLabel);
        table.row().uniform();
        pauseBackgroundImage.setSize(table.getPrefWidth() * 1.25f, table.getPrefHeight() * 1.5f);
        pauseBackgroundImage.setPosition(viewwidth/2, viewheight/2, Align.center);
        pauseStage.addActor(pauseBackgroundImage);
        pauseStage.addActor(table);
    }

    public abstract void update(float delta);

    public void render (float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();
        this.stage.act();
        if (!gamePaused){
            Gdx.input.setInputProcessor(stage);
            this.stage.act(delta);
            update(delta);
        }
        else{
//            System.out.println("Stage Draw");
            Gdx.input.setInputProcessor(pauseStage);
            pauseStage.draw();
            pauseStage.act();

            game.setMasterVolume(masterSlider.getValue());
            game.setMusicVolume(musicSlider.getValue());
            game.setSoundVolume(soundSlider.getValue());

            masterLabel.setText((int)(game.getMasterValue() * 100) + " / " + 100);
            soundLabel.setText((int)(game.getSoundValue() * 100) + " / " + 100);
            musicLabel.setText((int)(game.getMusicValue() * 100) + " / " + 100);

            music.setVolume(game.getMusicVolume());
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("Toggle");
            toggleGamePaused();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            this.game.setScreen(new CombatScreen(game, false));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            this.game.setScreen(new DepartmentScreen(game));
        }

    }



    public void changeScreen(BaseScreen screen) {
        game.setScreen(screen);
        dispose();
    }

    public Sound makeSound(String filename) { return Gdx.audio.newSound(Gdx.files.internal(filename)); }

    public void playSound(Sound sound) { sound.setVolume(sound.play(), game.getSoundVolume()); }

    public Music makeMusic(String filename) { return Gdx.audio.newMusic(Gdx.files.internal(filename)); }

    public void playMusic(Music music, boolean loop) {
        music.setVolume(game.getMusicVolume());
        music.setLooping(loop);
        music.play();
    }

    public void playMusic(Music music) { playMusic(music, false); }

    public void setMusic(Music music) {this.music = music;}

    public Music getMusic() {return music;}

    @Override
    public void dispose () {
        this.stage.dispose();
        this.pauseStage.dispose();
        this.music.stop();
        this.music.dispose();
    }

    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height);
        this.pauseStage.getViewport().update(width, height);
    }

    @Override
    public void show(){ }

    @Override
    public void pause() { }

    @Override
    public void hide(){ }

    @Override
    public void resume() {}

    public GameManager getGame() {
        return game;
    }

    public void setGame(GameManager game) {
        this.game = game;
    }

    public void toggleGamePaused(){
        gamePaused = !gamePaused;
    }


}
