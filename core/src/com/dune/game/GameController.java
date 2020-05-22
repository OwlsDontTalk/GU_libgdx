package com.dune.game;

public class GameController {
    private Tank tank;

    public Tank getTank() {
        return tank;
    }

    public GameController() {
         tank =  new Tank(200, 200);;
    }

    public void update(float dt){
        tank.update(dt);
    }
}
