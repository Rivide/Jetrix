package com.mygdx.game.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.mygdx.game.components.*
import com.mygdx.game.gdx_extensions.div
import com.mygdx.game.gdx_extensions.minus

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
            if (projectileCM.get(projectile).shooter != it && collide(it, projectile)) {
                healthCM.get(it).health -= projectileCM.get(projectile).damage

                removableCM.get(projectile).toBeRemoved = true
            }
        }
    }

    private fun collide(entity1: Entity, entity2: Entity): Boolean {
        val dimensions1 = colliderCM.get(entity1).dimensions
        val corner1 = positionCM.get(entity1).position - dimensions1 / 2f

        val dimensions2 = colliderCM.get(entity2).dimensions
        val corner2 = positionCM.get(entity2).position - dimensions2 / 2f

        return Rectangle(corner1.x, corner1.y, dimensions1.x, dimensions1.y)
                .overlaps(Rectangle(corner2.x, corner2.y, dimensions2.x, dimensions2.y))
    }
}