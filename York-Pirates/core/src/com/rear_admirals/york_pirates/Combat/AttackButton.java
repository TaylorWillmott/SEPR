package com.rear_admirals.york_pirates.Combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rear_admirals.york_pirates.Attacks.Attack;
import javafx.scene.text.Text;


public class AttackButton extends TextButton {
    String name;
    Skin skin;
    String desc;

    public AttackButton(Attack attack, Skin skin){
        super(attack.getName(), skin);
        this.name = attack.getName();
        this.skin = skin;
        this.desc = attack.getDesc();
//        setHeight(20f);

    }
//
//    void changeToDesc() {
//        label.setText(attack.getDesc());
//    }
//
//    void changeToName() {
//        label.setText(attack.getName());
//    }

    @Override
    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }


//    public AttackButton setAttack
}
