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

	public Island (String texture, College college, int x, int y) {
		this(new Texture(texture), college, x, y);
	}

	public Island (College college, int x, int y) {
		this(new Texture("island.png"), college, x, y);
	}

	public Island (Texture texture, int x, int y) {
		this(texture, null, x, y);
	}

	public Island (String texture, int x, int y) {
		this(new Texture(texture), x, y);
	}

	public Island (int x, int y) {
		this((College) null, x, y);
	}

	public Island() {
		this(0,0);
	}
}
