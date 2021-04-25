package com.mygdx.game.screens

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.Jetrix
import com.mygdx.game.Textures
import com.mygdx.game.components.MovementComponent
import com.mygdx.game.components.PositionComponent
import com.mygdx.game.components.ShooterComponent
import com.mygdx.game.components.TextureComponent
import com.mygdx.game.systems.MovementSystem
import com.mygdx.game.systems.ProjectileSystem
import com.mygdx.game.systems.RenderSystem
import com.mygdx.game.systems.ShootSystem

class PlayScreen(game: Jetrix) : GameScreen(game) {

    private val background = Entity()
    private val player = Entity()

    override fun show() {
        background.add(PositionComponent(0f, 0f))
        background.add(TextureComponent(Textures.ocean, isBackground = true))

        player.add(PositionComponent(0f, 0f))
        player.add(TextureComponent(Textures.jet))
        player.add(MovementComponent())
        player.add(ShooterComponent(2f, Vector2(0f, 32f + 7f)))

        game.engine.addEntity(background)
        game.engine.addEntity(player)

        game.engine.addSystem(MovementSystem(game.camera))
        game.engine.addSystem(RenderSystem(game.spriteBatch, game.camera))
        game.engine.addSystem(ShootSystem())
        game.engine.addSystem(ProjectileSystem())
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