package com.mygdx.game.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.components.DraggableComponent
import com.mygdx.game.components.PositionComponent

class DragSystem(private val camera: Camera) : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>

    private val positionCM = ComponentMapper.getFor(PositionComponent::class.java)

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(
                Family.all(DraggableComponent::class.java, PositionComponent::class.java).get()
        )
    }
    override fun update(deltaTime: Float) {
        if (!Gdx.input.isTouched) return

        entities.forEach {
            val touchDelta = Vector3()
            touchDelta.set(Gdx.input.deltaX.toFloat(), Gdx.input.deltaY.toFloat(), 0f)

            camera.unproject(touchDelta)
            touchDelta.sub(camera.unproject(Vector3.Zero.cpy()))

            positionCM.get(it).position.add(touchDelta.x, touchDelta.y)
        }
    }
}