package com.mygdx.game.components

import com.badlogic.ashley.core.Component
import kotlin.math.max

class HealthComponent(health: Int) : Component {
    var health = health
        set(value) {
            field = max(value, 0)
        }
}