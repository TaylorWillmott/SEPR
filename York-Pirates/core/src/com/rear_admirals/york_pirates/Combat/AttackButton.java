package com.rear_admirals.york_pirates.Combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rear_admirals.york_pirates.Attacks.Attack;
import javafx.scene.text.Text;


public class AttackButton extends TextButton {
    String name;
    Skin skin;
    String desc;

    public AttackButton(String name, Skin skin, String desc){
        super(name, skin);
        this.name = name;
        this.skin = skin;
        this.desc = desc;

        setWidth(125f);
        setHeight(20f);
        addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                getLabel().setFontScale(0.5f, 0.5f);
                setText(getDesc());

            };
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                getLabel().setFontScale(1, 1);
                setText(getName());
            };
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setText("Attacking");
            }
        });
    }
    public AttackButton(Attack attack, Skin skin){
        super(attack.getName(), skin);
        this.name = attack.getName();
        this.skin = skin;
        this.desc = attack.getDesc();
        setWidth(125f);
        setHeight(20f);
        addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                getLabel().setFontScale(0.5f, 0.5f);
                setText(getDesc());
            };
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                getLabel().setFontScale(1, 1);
                setText(getName());
            };
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setText("Attacking");
//                attack.doAttack();
            }
        });

    }

    @Override
    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

//    public AttackButton setAttack
}
