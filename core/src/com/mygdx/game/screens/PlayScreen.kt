package com.mygdx.game.screens

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Jetrix
import com.mygdx.game.Textures
import com.mygdx.game.components.*
import com.mygdx.game.systems.*

class PlayScreen(game: Jetrix) : GameScreen(game) {

    private val background = Entity()
    private val player = Entity()

    override fun show() {
        background.add(PositionComponent(0f, 0f))
        background.add(TextureComponent(Textures.ocean, isBackground = true))

        player.add(PositionComponent(0f, 0f))
        player.add(ColliderComponent(64f, 64f))
        player.add(TextureComponent(Textures.jet))
        player.add(DraggableComponent())
        player.add(ShooterComponent(1.5f, Vector2(0f, 32f + 8f)))
        player.add(HealthComponent(10))
        player.add(RemovableComponent())

        val enemy = Entity()
        enemy.add(PositionComponent(96f, 256f))
        enemy.add(ColliderComponent(64f, 64f))
        enemy.add(TextureComponent(Textures.jet))
        enemy.add(HealthComponent(10))
        enemy.add(RemovableComponent())

        game.engine.addEntity(background)
        game.engine.addEntity(player)
        game.engine.addEntity(enemy)

        game.engine.addSystem(DragSystem(game.camera))
        game.engine.addSystem(MoveSystem())
        game.engine.addSystem(ShootSystem())
        game.engine.addSystem(ProjectileSystem())
        game.engine.addSystem(DeathSystem())
        game.engine.addSystem(RemoveSystem())
        game.engine.addSystem(RenderSystem(game.spriteBatch, game.camera))
    }

    override fun render(delta: Float) {
        game.engine.update(delta)
    }

    override fun resize(width: Int, height: Int) {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {

    }

}