package com.rear_admirals.york_pirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class GameObject {

	protected Vector3 pos;
	protected Texture tex;

	public GameObject(int x, int y, int angle, Texture texture) {
		pos = new Vector3(x,y,angle);
		this.tex = texture;
	}

	public GameObject() { this(0, 0, 0, new Texture("default.png")); }

	public GameObject(int x, int y, Texture texture) { this(x, y, 0, texture); }

	public GameObject(Texture texture) { this(0, 0, 0, texture); }

	public void draw(SpriteBatch batch) {
		batch.draw(tex, pos.x, pos.y, tex.getWidth() >> 1, tex.getHeight() >> 1, tex.getWidth(), tex.getHeight(), 1, 1, pos.z, 0,0, tex.getWidth(), tex.getHeight(), false, false);
	}

	public Vector3 getPos() { return pos; }

	public void setX(int x) {pos.x = x;}

	public void setY(int y) {pos.y = y;}

	public void setAngle(int angle) {pos.z = angle;}

}
