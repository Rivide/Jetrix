package com.mygdx.game.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.mygdx.game.components.HealthComponent
import com.mygdx.game.components.RemovableComponent

class DeathSystem : IteratingSystem(
        Family.all(HealthComponent::class.java, RemovableComponent::class.java).get()
) {
    private val healthCM = ComponentMapper.getFor(HealthComponent::class.java)
    private val removableCM = ComponentMapper.getFor(RemovableComponent::class.java)

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (healthCM.get(entity).health == 0) {
            removableCM.get(entity).toBeRemoved = true
        }
    }
}