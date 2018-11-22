package com.rear_admirals.york_pirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MoveableObject {
	protected int x;
	protected int y;
	protected int a;
	protected Texture tex;

	private double sind(double angle) {
		return Math.sin(Math.toRadians(angle));
	}

	private double cosd(double angle) {
		return Math.cos(Math.toRadians(angle));
	}

	public MoveableObject() {
		this.x = 0;
		this.y = 0;
		this.a = 0;
		this.tex = new Texture("default.png");
	}

	public MoveableObject(int x, int y, int angle, Texture texture) {
		this.x = x;
		this.y = y;
		this.a = angle;
		this.tex = texture;
	}

	public MoveableObject(int x, int y, Texture texture) {
		this.x = x;
		this.y = y;
		this.a = 0;
		this.tex = texture;
	}

	public MoveableObject(Texture texture) {
		this.x = 0;
		this.y = 0;
		this.a = 0;
		this.tex = texture;
	}

	public void draw(SpriteBatch batch) {
		batch.draw(tex, x, y, tex.getWidth()/2, tex.getHeight()/2, tex.getWidth(), tex.getHeight(), 1, 1, a, 0,0, tex.getWidth(), tex.getHeight(), false, false);
	}

	public void angularMove(float angle, int distance) {
		int xChange = (int) (Math.round(sind(angle) * distance));
		int yChange = (int) (Math.round(cosd(angle) * distance));
		x = x - xChange;
		y = y + yChange;
	}

	public void absoluteMove(int xChange, int yChange) {
		x = x + xChange;
		y = y + yChange;
	}

	public void rotate(int angleChange) {
		a = a + angleChange;
		if (a > 360) {a = a - 360;}
	}

	public void setX(int x) {this.x = x;}

	public void setY(int y) {this.y = y;}

	public void setAngle(int angle) {a = angle;}

}