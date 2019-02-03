package display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import game_manager.GameManager;

public abstract class BaseScreen implements Screen {

    protected GameManager game;

    protected Stage stage;

    public BaseScreen(GameManager pirateGame){
        this.game = pirateGame;
        this.stage = new Stage();
        }

    public abstract void update(float delta);

    public void render (float delta) {
        this.stage.act(delta);
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();
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
    public void resume() { }

}
