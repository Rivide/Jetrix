package com.mygdx.game.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Logger
import com.mygdx.game.components.PositionComponent
import com.mygdx.game.components.TextureComponent

class RenderSystem(private val spriteBatch: SpriteBatch, private val camera: Camera) : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>

    private val positionCM = ComponentMapper.getFor(PositionComponent::class.java)
    private val textureCM = ComponentMapper.getFor(TextureComponent::class.java)

    private val logger = Logger("Jetrix")

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(
                Family.all(PositionComponent::class.java, TextureComponent::class.java).get()
        )
    }
    override fun update(deltaTime: Float) {
        spriteBatch.projectionMatrix = camera.combined

        spriteBatch.begin()

        entities.forEach {
            val positionComponent = positionCM.get(it)
            val textureComponent = textureCM.get(it)

            if (textureComponent.isBackground) {
                drawBackground(textureComponent.texture)
            } else {
                spriteBatch.draw(
                        textureComponent.texture,
                        positionComponent.position.x - textureComponent.texture.width / 2,
                        positionComponent.position.y - textureComponent.texture.height / 2
                )
            }
        }

        spriteBatch.end()
    }
    private fun drawBackground(texture: Texture) {
        for (x in 0 until camera.viewportWidth.toInt() step texture.width) {
            for (y in 0 until camera.viewportHeight.toInt() step texture.height) {
                spriteBatch.draw(
                        texture,
                        x.toFloat() - camera.viewportWidth / 2,
                        y.toFloat() - camera.viewportHeight / 2
                )
            }
        }
    }
}