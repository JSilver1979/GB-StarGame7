package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.utils.Assets;

public class Bot extends Ship {
    private Circle alarmZone;

    public Circle getAlarmZone() {
        return alarmZone;
    }


    public Bot (GameController gc) {
        super(gc, 100, 50);

        this.texture = Assets.getInstance().getAtlas().findRegion("ship");
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

    public void update (float dt) {
        alarmZone.setPosition(position);
        fireTimer += dt;
        position.mulAdd(velocity, dt);
        hitArea.setPosition(position);

        super.checkBorders();
        if (gc.getHero().getHitArea().overlaps(alarmZone)) {
            angle -= (gc.getHero().getVelocity().angleRad(velocity));
        }
    }
}
