package com.rear_admirals.york_pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rear_admirals.york_pirates.base.GameUtils;
import com.rear_admirals.york_pirates.base.PhysicsActor;


import java.util.concurrent.ThreadLocalRandom;

class SeaMonster extends PhysicsActor {
    //Enemy variables.
    public int moveSpeed = 2;
    private TextureAtlas monsterTextureAtlas;
    private TextureRegion monsterTexture;

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

        Animation sharkAnim = GameUtils.parseSpriteSheet("largeshark.png", 1, 3, new int[] {0, 1, 2}, 0.1f, Animation.PlayMode.LOOP);
        this.storeAnimation("move", sharkAnim);
        this.setActiveAnimation("move");

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

}
