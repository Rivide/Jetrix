package com.mygdx.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class ColliderComponent(width: Float, height: Float) : Component {
    val dimensions = Vector2(width, height)
}