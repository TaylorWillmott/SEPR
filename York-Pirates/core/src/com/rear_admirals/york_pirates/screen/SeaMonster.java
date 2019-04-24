package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rear_admirals.york_pirates.base.PhysicsActor;

public class SeaMonster extends PhysicsActor {
    //Enemy variables.
    public int moveSpeed = 150;
    public TextureAtlas monsterTextureAtlas;
    public TextureRegion monsterTexture;

    //Setup new enemy.
    public SeaMonster(float x, float y){
        this.setSpeed(moveSpeed);
        this.monsterTextureAtlas = new TextureAtlas(Gdx.files.internal("largeshark.atlas"));
        this.monsterTexture = monsterTextureAtlas.findRegion("shark1");
        this.setPosition(x,y);

        this.setWidth(this.monsterTexture.getRegionWidth());
        this.setHeight(this.monsterTexture.getRegionHeight());
        this.setOriginCentre();
        this.setMaxSpeed(250);
        this.setDeceleration(250);
        this.setEllipseBoundary();
    }

    public void monsterMovement(float dt, boolean collision){

    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.setColor(1,1,1,alpha);
        batch.draw(monsterTexture,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,1,getRotation());
    }
}
