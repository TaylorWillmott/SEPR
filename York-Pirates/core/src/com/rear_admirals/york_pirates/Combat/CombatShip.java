package com.rear_admirals.york_pirates.Combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rear_admirals.york_pirates.Ship;

import java.awt.*;

public class CombatShip extends Actor {

    float ship_size;
    Texture texture;
    Ship ship;
    float x;
    float y;

    public CombatShip(Ship ship, String shipFile, float ship_size){
        this.ship = ship;
        this.texture = new Texture(shipFile);
        this.ship_size = ship_size;
//        this.setBounds(x,y,ship_size,ship_size);
    }


    @Override
    public void draw(Batch batch, float alpha){
        batch.setColor(1,1,1,alpha);
        batch.draw(texture,getX(),getY(),ship_size,ship_size);
    }

//    @Override
//    public void act(float delta) {
//        super.act(delta);
//    }

}