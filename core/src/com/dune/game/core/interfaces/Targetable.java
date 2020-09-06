package com.dune.game.core.interfaces;

import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.units.types.TargetType;

public interface Targetable {
    Vector2 getPosition();
    TargetType getTargetType();
    boolean isActive();
}
