package com.mygdx.game.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.mygdx.game.components.MotionComponent
import com.mygdx.game.components.PositionComponent
import com.mygdx.game.gdx_extensions.plus
import com.mygdx.game.gdx_extensions.times

class MoveSystem : IteratingSystem(
        Family.all(PositionComponent::class.java, MotionComponent::class.java).get()
) {
    private val positionCM = ComponentMapper.getFor(PositionComponent::class.java)
    private val motionCM = ComponentMapper.getFor(MotionComponent::class.java)

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val motionComponent = motionCM.get(entity)

        motionComponent.velocity += motionComponent.acceleration * deltaTime
        positionCM.get(entity).position += motionComponent.velocity * deltaTime
    }
}