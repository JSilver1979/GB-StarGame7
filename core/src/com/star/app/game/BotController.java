package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.star.app.game.helpers.ObjectPool;

public class BotController extends ObjectPool<Bot> {
    private GameController gc;

    @Override
    protected Bot newObject() {return new Bot(gc);}

    public BotController(GameController gc) {
        this.gc = gc;
    }
    public void setup(float x, float y, float chanceToBot) {
        if (MathUtils.random() <= chanceToBot) {
            getActiveElement().activate(x, y);
        }
    }
    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            Bot bot = activeList.get(i);
            bot.render(batch);
        }
    }
}
