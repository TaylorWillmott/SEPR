package com.rear_admirals.york_pirates.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LabelTimer extends Label {
    // This is a new class for Assessment 4. It is used for generating temporary labels.
    public float getTimer() {
        return timer;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    private float timer;

    // Default constructor
    public LabelTimer(CharSequence text, Skin skin, float startTimer){
        super(text, skin);
        this.timer = startTimer;

    }

    // Constructor allowing colouring of the text
    public LabelTimer(CharSequence text, Skin skin, float startTimer, Color color){
        super(text, skin);
        this.timer = startTimer;
        this.setColor(color);
    }
}
