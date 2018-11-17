package com.rear_admirals.york_pirates;

import com.badlogic.gdx.graphics.Texture;

public class Island {
	private Texture texture;
	private int x;
	private int y;

	public void Island (String texture, int x, int y) {
		this.texture = new Texture(texture);
		this.x = x;
		this.y = y;
	}

}
