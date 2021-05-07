package com.mygdx.game.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.mygdx.game.components.RemovableComponent

class RemoveSystem : IteratingSystem(
        Family.all(RemovableComponent::class.java).get()
) {

    private val removableCM = ComponentMapper.getFor(RemovableComponent::class.java)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (removableCM.get(entity).toBeRemoved) {
            engine.removeEntity(entity)
        }
    }

}