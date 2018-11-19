package com.rear_admirals.york_pirates;

public class MovableObject {
	int x;
	int y;
	int a;
		return Math.sin(Math.toRadians(angle));
	}

	private double cosd(double angle) {
		return Math.cos(Math.toRadians(angle));
	}


	public void angularMove(float angle, int distance) {
		int xChange = (int) (Math.round(Math.cos(angle) * distance));
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
		angle = angle + angleChange;
		a = a + angleChange;
		if (a > 360) {a = a - 360;}
	}

}