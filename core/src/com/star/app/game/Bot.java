package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.utils.Assets;

public class Bot extends Ship implements Poolable{
    private Circle alarmZone;
    private boolean active;

    public Circle getAlarmZone() {
        return alarmZone;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public Bot (GameController gc) {
        super(gc, 100, 50);
        this.active = false;
        this.texture = Assets.getInstance().getAtlas().findRegion("bot");
        this.position = new Vector2();
        this.hitArea = new Circle(position, 28);
        this.alarmZone = new Circle(position, 400);
        this.velocity.set(MathUtils.random(-3.0f, 3.0f) * enginePower,
                MathUtils.random(-3.0f, 3.0f) * enginePower);

    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32,
                64, 64, 1, 1, angle);
    }

    public void deactivate() {
        active = false;
    }

    public void update (float dt) {
        alarmZone.setPosition(position);
        fireTimer += dt;
        position.mulAdd(velocity, dt);
        hitArea.setPosition(position);

        super.checkBorders();
        if (gc.getHero().getHitArea().overlaps(alarmZone)) {
            angle -= (velocity.angleRad(gc.getHero().getVelocity()));
        }
    }

    public void activate(float x, float y) {
        position.set(x,y);
        active = true;
        hp = hpMax;
        hitArea.setPosition(x,y);
        alarmZone.setPosition(x,y);
    }
    @Override
    public void takeDamage(int amount) {
        hp -= amount;
        if(hp <= 0) {
            deactivate();
        }
    }
}
