package com.dune.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends AbstractScreen {
    private SpriteBatch batch;
    private GameController gameController;
    private WorldRenderer worldRenderer;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
        this.gameController = new GameController();
    }

    @Override
    public void show() {
        this.gameController = new GameController();
        this.worldRenderer = new WorldRenderer(batch,gameController);
    }

    @Override
    public void render(float delta) {
        update(delta);
        worldRenderer.render();
    }

    public void update(float dt) {
        gameController.update(dt);
    }

    @Override
    public void dispose() {
     }
}
