package com.rear_admirals.york_pirates.Combat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rear_admirals.york_pirates.Ship;

public class CombatShip extends Actor {
    float posX;
    float posY;
    Texture texture;
    Ship ship;

    public CombatShip(Ship ship, String shipFile, float posX, float posY){
        this.ship = ship;
        this.posX = posX;
        this.posY = posY;
        this.texture = new Texture(shipFile);
//        this.texture = new Texture(ship.name+".png");

        setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
    }


    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, posX ,posY,150,150);
    }
}