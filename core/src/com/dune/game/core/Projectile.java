package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    private Vector2 position;
    private Vector2 velocity = new Vector2();
    private TextureRegion texture;
    private boolean projectileFlying = false;
    private float angle;

    public Projectile(TextureAtlas atlas) {
        this.texture = atlas.findRegion("bullet");
        this.position = new Vector2(100.0f, 100.0f);
    }

    public boolean isProjectileFlying() {return projectileFlying;}

    public void checkBounds() {
        if (position.x < 40) {
            projectileFlying = false;
        }
        if (position.y < 40) {
            projectileFlying = false;
        }
        if (position.x > 1240) {
            projectileFlying = false;
        }
        if (position.y > 680) {
            projectileFlying = false;
        }
    }

    public void shoot(Vector2 startPosition, float angle){
        if(!projectileFlying){
            projectileFlying = true;
            this.angle = angle;
            position.set(startPosition);
            velocity.set(500.0f * MathUtils.cosDeg(angle), 500.0f * MathUtils.sinDeg(angle));
        }
    }

    public void update(float dt){
        checkBounds();
        position.mulAdd(velocity, dt);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x - 8, position.y - 8, 8, 8, 16, 16, 1, 1, angle);
    }
}
