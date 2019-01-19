package com.rear_admirals.york_pirates.Screen.Combat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rear_admirals.york_pirates.Ship;

public class CombatShip extends Actor {

    float ship_size;
    Texture texture;
    Ship ship;

    public CombatShip(Ship ship, String shipFile, float ship_size){
        this.ship = ship;
        this.texture = new Texture(shipFile);
        this.ship_size = ship_size;
        this.setBounds(getX(),getY(),ship_size,ship_size);
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