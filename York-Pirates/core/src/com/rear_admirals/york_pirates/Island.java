package com.rear_admirals.york_pirates;

import com.badlogic.gdx.graphics.Texture;

public class Island {
	private Texture texture;
	private College college;
	private int x;
	private int y;

	public Island (Texture texture, College college, int x, int y) {
		this.texture = texture;
		this.college = college;
		this.x = x;
		this.y = y;
	}

}
