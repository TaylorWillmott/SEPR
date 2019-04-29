package com.rear_admirals.york_pirates.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class AnimatedActor extends BaseActor {
    private float elapsedTime;
    private Animation<TextureRegion> activeAnim;
    private String activeName;
    protected Map<String, Animation> animationStorage;
    private boolean pauseAnim;

    public  AnimatedActor() {
        super();
        elapsedTime = 0;
        activeAnim = null;
        activeName = null;
        animationStorage = new HashMap<String, Animation>();
        pauseAnim = false;
    }

    public void storeAnimation(String name, Animation anim) {
        animationStorage.put(name, anim);
        if (activeName == null) setActiveAnimation(name);
    }

    public void storeAnimation(String name, Texture tex) {
        TextureRegion reg = new TextureRegion(tex);
        TextureRegion[] frames = {reg};
        Animation anim = new Animation(1.0f, frames);
        storeAnimation(name, anim);
    }

    public void setActiveAnimation(String name) {
        if (!animationStorage.containsKey(name)) {
            System.out.println("No animation: " + name);
            return;
        }

        // no need to set animation if already running
//        if (activeName.equals(name)) return;

        activeName = name;
        activeAnim = animationStorage.get(name);
        elapsedTime = 0;

        if ( getWidth() == 0 || getHeight() == 0 ) {
            Texture tex = activeAnim.getKeyFrame(0).getTexture();
            setWidth( tex.getWidth() );
            setHeight( tex.getHeight() );
        }
    }

    public String getAnimationName() {
        return activeName;
    }

    public void act(float dt) {
        super.act(dt);
        if (!pauseAnim) elapsedTime += dt;
    }

    public void draw(Batch batch, float parentAlpha) {
        region.setRegion(activeAnim.getKeyFrame(elapsedTime));
        super.draw(batch, parentAlpha);
    }

    // Adjust elapsed time of the animation to the value corresponding to when a particular frame is displayed
    public void setAnimationFrame(int n) { elapsedTime = n * activeAnim.getFrameDuration(); }

    public void pauseAnimation() { pauseAnim = true; }
    public void startAnimation() { pauseAnim = false; }
}
