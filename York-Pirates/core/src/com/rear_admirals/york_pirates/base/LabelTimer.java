package com.rear_admirals.york_pirates.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LabelTimer extends Label {
    public float getTimer() {
        return timer;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    private float timer;

    public LabelTimer(CharSequence text, Skin skin){
        super(text, skin);
        this.timer = 1;
        this.setColor(Color.RED);
    }
}
