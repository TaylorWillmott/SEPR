package com.rear_admirals.york_pirates;

public class MovableObject {
	int x;
	int y;
	int angle;

	public void angularMove(float angle, int distance) {
		int xChange = (int) (Math.round(Math.cos(angle) * distance));
		int yChange = (int) (Math.round(Math.sin(angle) * distance));
		x = x + xChange;
		y = y + yChange;
	}

	public void absoluteMove(int xChange, int yChange) {
		x = x + xChange;
		y = y + yChange;
	}

	public void rotate(int angleChange) {
		angle = angle + angleChange;
	}

}