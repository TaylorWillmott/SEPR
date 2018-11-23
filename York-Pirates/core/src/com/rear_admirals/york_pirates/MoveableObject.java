package com.rear_admirals.york_pirates;

import com.badlogic.gdx.graphics.Texture;

public class MoveableObject extends GameObject {

	public MoveableObject() { super(); }

	public MoveableObject(int x, int y, int angle, Texture texture) { super(x,y,angle,texture); }

	public MoveableObject(int x, int y, Texture texture) { super(x,y,texture); }

	public MoveableObject(Texture texture) { super(texture); }

	private double sind(double angle) { return Math.sin(Math.toRadians(angle)); }

	private double cosd(double angle) { return Math.cos(Math.toRadians(angle)); }

	public void angularMove(float angle, int distance) {
		int xChange = (int) (Math.round(sind(angle) * distance));
		int yChange = (int) (Math.round(cosd(angle) * distance));
		pos.x = pos.x - xChange;
		pos.y = pos.y + yChange;
	}

	public void absoluteMove(int xChange, int yChange) {
		pos.x = pos.x + xChange;
		pos.y = pos.y + yChange;
	}

	public void rotate(int angleChange) {
		pos.z = pos.z + angleChange;
		if (pos.z > 360) {pos.z = pos.z - 360;}
	}

}