package com.mygdx.game.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.math.Rectangle
import com.mygdx.game.components.*

class ProjectileSystem : IteratingSystem(
        Family.all(PositionComponent::class.java, ColliderComponent::class.java,
        ProjectileComponent::class.java, RemovableComponent::class.java).get()
) {
    private lateinit var targets: ImmutableArray<Entity>

    private val positionCM = ComponentMapper.getFor(PositionComponent::class.java)
    private val colliderCM = ComponentMapper.getFor(ColliderComponent::class.java)
    private val projectileCM = ComponentMapper.getFor(ProjectileComponent::class.java)
    private val healthCM = ComponentMapper.getFor(HealthComponent::class.java)
    private val removableCM = ComponentMapper.getFor(RemovableComponent::class.java)

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)

        targets = engine.getEntitiesFor(
                Family.all(PositionComponent::class.java, ColliderComponent::class.java,
                    HealthComponent::class.java)
                        .exclude(ProjectileComponent::class.java).get()
        )
    }

    override fun processEntity(projectile: Entity, deltaTime: Float) {
        targets.forEach {
            if (collide(it, projectile)) {
                healthCM.get(it).health -= projectileCM.get(projectile).damage

                removableCM.get(projectile).toBeRemoved = true
            }
        }
    }

    private fun collide(entity1: Entity, entity2: Entity): Boolean {
        val position1 = positionCM.get(entity1).position
        val dimensions1 = colliderCM.get(entity1).dimensions

        val position2 = positionCM.get(entity2).position
        val dimensions2 = colliderCM.get(entity2).dimensions

        return Rectangle(position1.x, position1.y, dimensions1.x, dimensions1.y)
                .overlaps(Rectangle(position2.x, position2.y, dimensions2.x, dimensions2.y))
    }
}