package com.rear_admirals.york_pirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rear_admirals.york_pirates.Ship;
import com.rear_admirals.york_pirates.base.GameUtils;
import com.rear_admirals.york_pirates.base.PhysicsActor;


import java.util.concurrent.ThreadLocalRandom;

public class SeaMonster extends PhysicsActor {
    //Enemy variables.
    public int moveSpeed = 2;
    public TextureAtlas monsterTextureAtlas;
    public TextureRegion monsterTexture;

    private float time;

    //Setup new enemy.
    public SeaMonster(float x, float y){
        this.setSpeed(moveSpeed);

        String atlas;
        String texture;

        Animation sharkAnim = GameUtils.parseSpriteSheet("largeshark.png", 1, 3, new int[] {0, 1, 2}, 0.1f, Animation.PlayMode.LOOP);
        Animation dragonAnim = GameUtils.parseSpriteSheet("dragon.png", 4, 4, new int[] {0, 4, 8, 12}, 0.3f, Animation.PlayMode.LOOP);
        Animation seaSoldierAnim = GameUtils.parseSpriteSheet("seaSoldier.png", 4, 4, new int[] {0, 4, 8, 12}, 0.2f, Animation.PlayMode.LOOP);
        Animation leviathanAnim = GameUtils.parseSpriteSheet("leviathan.png", 4, 4, new int[] {0, 4, 8, 12}, 0.2f, Animation.PlayMode.LOOP);

        // Randomly choose an appearance for the monster
        switch(ThreadLocalRandom.current().nextInt(4)) {
            case 0:
                atlas = "largeshark.atlas";
                texture = "shark1";

                this.monsterTextureAtlas = new TextureAtlas(Gdx.files.internal(atlas));
                this.monsterTexture = monsterTextureAtlas.findRegion(texture);

                this.setWidth(this.monsterTexture.getRegionWidth()*0.5f);
                this.setHeight(this.monsterTexture.getRegionHeight()*0.5f);


                this.storeAnimation("move", sharkAnim);

                break;
            case 1:
                atlas = "dragon.atlas";
                texture = "s1";

                this.monsterTextureAtlas = new TextureAtlas(Gdx.files.internal(atlas));
                this.monsterTexture = monsterTextureAtlas.findRegion(texture);

                this.setWidth(this.monsterTexture.getRegionWidth()*1.5f);
                this.setHeight(this.monsterTexture.getRegionHeight()*1.5f);

                this.storeAnimation("move", dragonAnim);

                break;
            case 2:
                atlas = "seaSoldier.atlas";
                texture = "s1";

                this.monsterTextureAtlas = new TextureAtlas(Gdx.files.internal(atlas));
                this.monsterTexture = monsterTextureAtlas.findRegion(texture);

                this.storeAnimation("move", seaSoldierAnim);

                this.setWidth(this.monsterTexture.getRegionWidth()*1.5f);
                this.setHeight(this.monsterTexture.getRegionHeight()*1.5f);


                break;
            case 3:
                atlas = "leviathan.atlas";
                texture = "s1";

                this.monsterTextureAtlas = new TextureAtlas(Gdx.files.internal(atlas));
                this.monsterTexture = monsterTextureAtlas.findRegion(texture);

                this.storeAnimation("move", leviathanAnim);

                this.setWidth(this.monsterTexture.getRegionWidth()*1.5f);
                this.setHeight(this.monsterTexture.getRegionHeight()*1.5f);


                break;
        }

        this.setActiveAnimation("move");

        this.setPosition(x,y);
        this.setWidth(this.monsterTexture.getRegionWidth()*1.5f);
        this.setHeight(this.monsterTexture.getRegionHeight()*1.5f);
        this.setOriginCentre();
        this.setMaxSpeed(200);
        this.setEllipseBoundary();

        this.time = 10;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void monsterMovement(Ship playerShip){
        float dx = playerShip.getX() - this.getX();
        float dy = playerShip.getY() - this.getY();
        float norm = (float) Math.sqrt(dx*dx + dy*dy);

        this.setRotation((float)(Math.atan2(dy, dx)*180.0d / Math.PI)); // Monster always point towards player
        this.moveBy(dx *= (this.moveSpeed/norm), dy *= (this.moveSpeed/norm)); // Monster move towards player
    }

//    @Override
//    public void draw(Batch batch, float alpha){
//        batch.setColor(1,1,1,alpha);
//        batch.draw(monsterTexture,getX(),getY(),getOriginX(),getOriginY(),getWidth()/1.5f,getHeight()/1.5f,1,1,getRotation() + 90);
//    }
}
