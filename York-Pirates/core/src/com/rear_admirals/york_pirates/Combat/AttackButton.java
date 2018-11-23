package com.rear_admirals.york_pirates.Combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import javafx.scene.text.Text;


public class AttackButton extends TextButton {
    String name;
    float posX;
    float posY;
    Skin skin;

    public AttackButton(String name, float posX, float posY, Skin skin){
        super(name, skin);
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.skin = skin;

        setWidth(125f);
        setHeight(20f);
        setPosition(posX, posY);
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setText("Attacking");
            }
        });
    }


}
