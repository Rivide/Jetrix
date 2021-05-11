package com.mygdx.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity

class ProjectileComponent(val damage: Int, val shooter: Entity) : Component {
}