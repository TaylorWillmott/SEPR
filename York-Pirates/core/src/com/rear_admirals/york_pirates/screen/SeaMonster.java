package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rear_admirals.york_pirates.base.PhysicsActor;
import java.util.concurrent.ThreadLocalRandom;

public class SeaMonster extends PhysicsActor {
    //Enemy variables.
    public int moveSpeed = 150;
    public TextureAtlas monsterTextureAtlas;
    public TextureRegion monsterTexture;

    //Setup new enemy.
    public SeaMonster(float x, float y){
        this.setSpeed(moveSpeed);

        String atlas = "largeshark.atlas";
        String texture = "shark1";
        /*
        switch(ThreadLocalRandom.current().nextInt(3)) {
            case 0:
	            atlas = "largeshark.atlas";
            	texture = "shark1";
            	break;
            case 1:
	            atlas = "largeshark.atlas";
	            texture = "shark1";
	            break;
            case 2:
	            atlas = "largeshark.atlas";
	            texture = "shark1";
	            break;
	        default:
	            atlas = "largeshark.atlas";
	            texture = "shark1";
	    */
        this.monsterTextureAtlas = new TextureAtlas(Gdx.files.internal(atlas));
        this.monsterTexture = monsterTextureAtlas.findRegion(texture);

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
