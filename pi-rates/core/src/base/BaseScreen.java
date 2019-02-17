package base;

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
import display.CombatScreen;
import display.DepartmentScreen;
import display.MinigameScreen;
import display.SailingScreen;
import game_manager.GameManager;

import static game_manager.GameManager.ComputerScience;
import static location.College.Goodricke;

public abstract class BaseScreen implements Screen {

    protected GameManager game;

    protected Stage mainStage;
    protected Stage uiStage;
    protected Stage pauseStage;
    protected boolean gamePaused;


    protected final int viewwidth = 1920;
    protected final int viewheight = 1080;
    protected final Skin skin;
    private Texture pauseBackgroundTexture;
    private Image pauseBackgroundImage;

    private boolean fullscreen = false;

    private Slider masterSlider;
    private Slider soundSlider;
    private Slider musicSlider;
    private Label masterLabel;
    private Label soundLabel;
    private Label musicLabel;

    private Music music;

    public BaseScreen(GameManager pirateGame){
        this.game = pirateGame;
        this.mainStage = new Stage(new FitViewport(this.viewwidth, this.viewheight));
        this.uiStage = new Stage(new FitViewport(this.viewwidth, this.viewheight));
        this.pauseStage = new Stage(new FitViewport(this.viewwidth, this.viewheight));
        this.gamePaused = false;


        // Sets up PauseStage
        skin = new Skin(Gdx.files.internal("uiskin.json"));
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
        this.uiStage.act(delta);
        this.mainStage.act(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.mainStage.draw();
        this.uiStage.draw();

        if (!gamePaused){
            Gdx.input.setInputProcessor(mainStage);
            update(delta);
        }
        else{
            pauseProcess();
        }

        this.inputForScreen();
    }

    public void inputForScreen() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.debug("Pause Menu", "Toggled");
            toggleGamePaused();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            changeScreen(new CombatScreen(game, false, Goodricke));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            changeScreen(new CombatScreen(game, true, Goodricke));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            changeScreen(new DepartmentScreen(game, ComputerScience));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            changeScreen(new MinigameScreen(game));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            changeScreen(new SailingScreen(game, true));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            if (fullscreen) {
                Gdx.graphics.setWindowedMode((int)(Gdx.graphics.getDisplayMode().width*0.8), (int)(Gdx.graphics.getDisplayMode().height*0.8));
            } else {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
            fullscreen = !fullscreen;
        }
    }

    public void pauseProcess(){
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

    public void changeScreen(BaseScreen screen) {
        dispose();
        game.setScreen(screen);
    }

    public Sound makeSound(String filename) { return Gdx.audio.newSound(Gdx.files.internal(filename)); }

    public void playSound(Sound sound) { sound.setVolume(sound.play(), game.getSoundVolume()); }

    public Music makeMusic(String filename) { return Gdx.audio.newMusic(Gdx.files.internal(filename)); }

    public void playMusic(Music music, boolean loop) {
        music.setVolume(game.getMusicVolume());
        music.setLooping(loop);
        music.play();
    }

    public void playMusic(Music music) { playMusic(music, true); }

    public void setMusic(Music music) {this.music = music;}

    public Music getMusic() {return music;}

    /**
     * Sets up the music and beings playing it immediately.
     * @param filename
     * @param loop
     */
    public void musicSetup(String filename, Boolean loop) {
        setMusic(makeMusic(filename));
        playMusic(getMusic(), loop);
    }

    /**
     * Sets up the music and beings playing it immediately. Loop defaults to true.
     * @param filename
     */
    public void musicSetup(String filename) { musicSetup(filename, true); }

    @Override
    public void dispose () {
        Gdx.app.debug("BaseScreen DEBUG", "About to dispose uiStage");
        this.uiStage.dispose();
        this.mainStage.dispose();
        this.pauseStage.dispose();
        this.music.stop();
        this.music.dispose();
    }

    public void resize(int width, int height) {
        this.pauseStage.getViewport().update(width, height, true);
        this.mainStage.getViewport().update(width, height, true);
    }

    @Override
    public void show(){ }

    @Override
    public void pause() { }

    @Override
    public void hide(){
    }

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
