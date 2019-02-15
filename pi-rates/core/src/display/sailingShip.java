package display;

import base.PhysicsActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import location.College;

import static location.College.Derwent;

public class sailingShip extends PhysicsActor {
    private Texture sailingTexture = new Texture(Gdx.files.internal("ship (1).png"));
    private College college;
    private boolean isBoss = false;

    // For testing purposes only. Use of this constructor in-game WILL cause errors.
    @Deprecated
    public sailingShip(){
        this.college = Derwent;
    }

    public sailingShip(College college) {
        this.college = college;
        setupShip();
    }

    public sailingShip(College college, String texturePath) {
        this.college = college;
        this.sailingTexture = new Texture(Gdx.files.internal(texturePath));
        setupShip();
    }

    public sailingShip(College college, boolean isBoss) {
        this.college = college;
        this.sailingTexture = new Texture(Gdx.files.internal("ship (1).png"));
        this.isBoss = isBoss;
        setupShip();
    }

    public void setupShip(){
        this.setWidth(this.sailingTexture.getWidth());
        this.setHeight(this.sailingTexture.getHeight());
        this.setOriginCentre();
        this.setMaxSpeed(200);
        this.setDeceleration(20);
        this.setEllipseBoundary();
    }

    public void playerMove(float dt) {
        this.setAccelerationXY(0,0);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.rotateBy(90 * dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            this.rotateBy(-90 * dt );
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            this.setAnchor(false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            this.setAnchor(true);
        }
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.setColor(1,1,1,alpha);
        batch.draw(new TextureRegion(sailingTexture),getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,1,getRotation());
    }

    // Getters and Setters
    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public Texture getSailingTexture() { return this.sailingTexture; }

    public boolean getIsBoss() { return this.isBoss; }
}
