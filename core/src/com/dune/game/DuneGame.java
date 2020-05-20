package com.dune.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class DuneGame extends ApplicationAdapter {
    private static class Tank {
        private Vector2 position;
        private Texture texture;
        private float angle;
        private float speed;

        public Tank(float x, float y) {
            this.position = new Vector2(x, y);
            this.texture = new Texture("tank.png");
            this.speed = 200.0f;
        }

        public void update(float dt) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                angle += 180.0f * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                angle -= 180.0f * dt;
            }


            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if(!((position.x + speed * MathUtils.cosDeg(angle) * dt) > 1260) && !((position.x + speed * MathUtils.cosDeg(angle) * dt) < 20 )) {
                    position.x += speed * MathUtils.cosDeg(angle) * dt;
                }


                if( ! ( ( position.y + speed * MathUtils.sinDeg(angle) * dt ) > 700 ) && ! ((position.y + speed * MathUtils.sinDeg(angle) * dt ) < 20)  ){
                    position.y += speed * MathUtils.sinDeg(angle) * dt;
                }

            }
        }

        public void render(SpriteBatch batch) {
            batch.draw(texture, position.x - 40, position.y - 40, 40, 40, 80, 80, 1, 1, angle, 0, 0, 80, 80, false, false);
        }

        public void dispose() {
            texture.dispose();
        }

        public Vector2 getPosition() {
            return this.position;
        }
    }

    private static class Circle{
        private Vector2 position;
        private Texture texture;

        public Circle(float x, float y){
            this.position = new Vector2(x,y);
            this.texture = new Texture("circle.png");
        }

        public void render(SpriteBatch batch) {
            batch.draw(texture,
                    position.x - 25,
                    position.y - 25);
        }

        public void update(Tank tank){
            if(Math.abs(tank.getPosition().x - this.position.x) < 25 && Math.abs(tank.getPosition().y - this.position.y) < 25 ){
                disappear();
            }
        }

        private void disappear(){
            Random random = new Random();
            Vector2 newPosition = new Vector2(random.nextInt(1280), random.nextInt(720));
            this.position = newPosition;
        }
    }

    private SpriteBatch batch;
    private Tank tank;
    private Circle circle;

    @Override
    public void create() {
        batch = new SpriteBatch();
        tank = new Tank(200, 200);
        circle = new Circle(500, 500);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(0, 0.4f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        tank.render(batch);
        circle.render(batch);
        batch.end();
    }

    public void update(float dt) {
        tank.update(dt);
        circle.update(tank);
    }

    @Override
    public void dispose() {
        batch.dispose();
        tank.dispose();
    }

    //Доделать
    //Добавить ограничение на выезд за нижнюю и левую границы экрана
}