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
    public int moveSpeed = 100;
    public TextureAtlas monsterTextureAtlas;
    public TextureRegion monsterTexture;

    private float time;

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
        this.setMaxSpeed(200);
        this.setDeceleration(250);
        this.setEllipseBoundary();

        this.time = 10;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

//    public void monsterMovement(float dt, boolean collision){
//        this.addAccelerationAS(this.getRotation(), 1000);
//    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.setColor(1,1,1,alpha);
        batch.draw(monsterTexture,getX(),getY(),getOriginX(),getOriginY(),getWidth()/1.5f,getHeight()/1.5f,1,1,getRotation() + 90);
    }
}
