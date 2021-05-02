package com.mygdx.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class MotionComponent(var velocity: Vector2 = Vector2.Zero.cpy(),
                      var acceleration: Vector2 = Vector2.Zero.cpy()) : Component {
}