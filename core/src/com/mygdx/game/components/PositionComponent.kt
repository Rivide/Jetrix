package com.mygdx.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class PositionComponent(x: Float, y: Float) : Component {
    var position = Vector2(x, y)
}