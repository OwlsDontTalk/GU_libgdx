package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.units.Owner;

public class Base {
    private GameController gc;
    private TextureRegion texture;
    private Vector2 position;

    public Vector2 getPosition() {
        Vector2 tmp = new Vector2(position.x, position.y);
        return tmp;
    }


    public Base(GameController gc){
        this.gc = gc;
        this.texture = Assets.getInstance().getAtlas().findRegion("base2");
        this.position = new Vector2(100,100);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 64, position.y - 64, 128, 128);
    }
}
