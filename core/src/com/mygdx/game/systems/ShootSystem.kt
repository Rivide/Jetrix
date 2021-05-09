package com.mygdx.game.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.Jetrix
import com.mygdx.game.components.*
import com.mygdx.game.gdx_extensions.plus

class ShootSystem : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>
    private lateinit var gameEngine: Engine

    private val positionCM = ComponentMapper.getFor(PositionComponent::class.java)
    private val shooterCM = ComponentMapper.getFor(ShooterComponent::class.java)

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(
                Family.all(PositionComponent::class.java, ShooterComponent::class.java).get()
        )
        gameEngine = engine
    }

    override fun update(deltaTime: Float) {
        entities.forEach {
            val shooterComponent = shooterCM.get(it)

            if (shooterComponent.cooldown > 0) {
                shooterComponent.cooldown = (shooterComponent.cooldown - deltaTime)
                        .coerceAtLeast(0f)

                return@forEach
            }

            if (!shooterComponent.active) {
                return@forEach
            }

            val position = positionCM.get(it).position + shooterComponent.launchOffset
            Gdx.app.log("ShootSystem", position.toString())
            val projectile = Entity()

            projectile.add(PositionComponent(position.x, position.y))
            projectile.add(MotionComponent(Vector2(0f, 70f)))
            projectile.add(ColliderComponent(12f, 16f))
            projectile.add(TextureComponent(Texture("bullet.png")))
            projectile.add(ProjectileComponent(2))
            projectile.add(RemovableComponent())

            gameEngine.addEntity(projectile)

            shooterComponent.cooldown = 1 / shooterComponent.fireRate
        }
    }
}