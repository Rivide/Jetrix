package com.mygdx.game.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.mygdx.game.components.PlayerComponent
import com.mygdx.game.components.ShooterComponent

class InputSystem : IteratingSystem(Family.all(PlayerComponent::class.java).get()) {

    private val shooterCM = ComponentMapper.getFor(ShooterComponent::class.java)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        shooterCM.get(entity).active = Gdx.input.isTouched
    }
}