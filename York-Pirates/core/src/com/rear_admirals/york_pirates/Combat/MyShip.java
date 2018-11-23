package com.rear_admirals.york_pirates.Combat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyShip extends Actor {
    public boolean started = false;
    float posX;
    float posY;
    Texture texture;

    public MyShip(String shipFile, float posX, float posY){
        this.posX = posX;
        this.posY = posY;
        this.texture = new Texture(shipFile);

        setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
    }


    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, posX ,posY,150,150);
    }
}