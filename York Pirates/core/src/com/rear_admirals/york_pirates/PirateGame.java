package com.rear_admirals.york_pirates;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PirateGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	int x = 0;
	int y = 0;

	public class MyInputProcessor implements InputProcessor {

		@Override
		public boolean keyDown (int keycode) {

			if (keycode == Input.Keys.UP) {
				y = y + 1;
				update();
				return true;
			}
			else if (keycode == Input.Keys.DOWN) {
				y = y - 1;
				update();
				return true;
			}
			else if (keycode == Input.Keys.LEFT) {
				x = x - 1;
				update();
				return true;
			}
			else if (keycode == Input.Keys.RIGHT) {
				x = x + 1;
				update();
				return true;
			}
			else {
				return false;
			}

		}

		@Override
		public boolean keyUp (int keycode) {
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			return false;
		}


	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, y);
		batch.end();
	}

	public void update () {
		render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

}
