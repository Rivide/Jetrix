package com.mygdx.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class ShooterComponent(val fireRate: Float, val launchOffset: Vector2) : Component {
    var cooldown = 0f
    var active = true
}