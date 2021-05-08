package com.mygdx.game

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Logger
import com.mygdx.game.screens.PlayScreen

class Jetrix : Game() {

    lateinit var spriteBatch: SpriteBatch
    lateinit var camera: OrthographicCamera

    lateinit var engine: Engine

    companion object {
        val logger = Logger("Jetrix")
    }

    override fun create() {
        spriteBatch = SpriteBatch()
        camera = OrthographicCamera(Config.width.toFloat(), Config.height.toFloat())

        engine = Engine()

        setScreen(PlayScreen(this))
    }

}