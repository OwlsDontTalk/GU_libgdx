package com.dune.game.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
    private static final Assets ourInstance = new Assets();

    public static Assets getInstance() {
        return ourInstance;
    }

    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    public TextureAtlas getAtlas() {
        return textureAtlas;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    private Assets() {
        assetManager = new AssetManager();
    }

    public void loadAssets() {
        assetManager.load("game.pack", TextureAtlas.class);
        assetManager.finishLoading();
        textureAtlas = assetManager.get("game.pack");
    }

    public void clear() {
        assetManager.clear();
    }
}