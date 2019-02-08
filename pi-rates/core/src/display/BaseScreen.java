package display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game_manager.GameManager;

public abstract class BaseScreen implements Screen {

    protected GameManager game;

    protected Stage stage;
    protected Stage pauseStage;
    protected boolean gamePaused;


    public BaseScreen(GameManager pirateGame){
        this.game = pirateGame;
        this.stage = new Stage();
        this.pauseStage = new Stage();
        this.gamePaused = false;
    }

    public abstract void update(float delta);

    public void render (float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();
        if (!gamePaused){
            this.stage.act(delta);
            update(delta);
        }
        else{
            pauseStage();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.out.println("ESC pressed");
            toggleGamePaused();
            pauseStage();
        }

    }

    public void pauseStage(){
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Table table = new Table();
        table.setFillParent(true);
        table.add(new Label("PAUSED", skin));
        table.row();
        table.add(new Label("Master Volume", skin));
        table.add(new Slider(0f, 1f, 0.01f, false, skin));
        table.row();
        table.add(new Label("Sound Effects Volume", skin));
        table.add(new Slider(0f, 1f, 0.01f, false, skin));
        table.row();
        table.add(new Label("Music Volume", skin));
        table.add(new Slider(0f, 1f, 0.01f, false, skin));
        table.row();
    }



    @Override
    public void dispose () {
        this.stage.dispose();
    }

    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height);
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
