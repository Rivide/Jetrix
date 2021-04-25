package com.mygdx.game.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.mygdx.game.components.PositionComponent
import com.mygdx.game.components.ProjectileComponent

class ProjectileSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>

    private val positionCM = ComponentMapper.getFor(PositionComponent::class.java)
    private val projectileCM = ComponentMapper.getFor(ProjectileComponent::class.java)

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(
                Family.all(PositionComponent::class.java, ProjectileComponent::class.java).get()
        )
    }

    override fun update(deltaTime: Float) {
        entities.forEach {
            positionCM.get(it).position.add(0f, projectileCM.get(it).speed * deltaTime)
        }
    }
}