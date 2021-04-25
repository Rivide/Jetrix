package com.mygdx.game.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.Jetrix
import com.mygdx.game.components.PositionComponent
import com.mygdx.game.components.ProjectileComponent
import com.mygdx.game.components.ShooterComponent
import com.mygdx.game.components.TextureComponent

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

        Jetrix.logger.debug("shootsystem update")
        if (!Gdx.input.isTouched) return

        entities.forEach {
            val shooterComponent = shooterCM.get(it)

            if (shooterComponent.cooldown > 0) {
                shooterComponent.cooldown = (shooterComponent.cooldown - deltaTime)
                        .coerceAtLeast(0f)

                Jetrix.logger.debug("shootsystem cooldown >0")

                return@forEach
            }
            Jetrix.logger.debug("shootsystem cooldown 0")
            val position = positionCM.get(it).position.cpy().add(shooterComponent.launchOffset)

            val projectile = Entity()

            projectile.add(PositionComponent(position.x, position.y))
            projectile.add(TextureComponent(Texture("bullet.png")))
            projectile.add(ProjectileComponent(20f))

            gameEngine.addEntity(projectile)

            shooterComponent.cooldown = 1 / shooterComponent.fireRate
        }
    }
}