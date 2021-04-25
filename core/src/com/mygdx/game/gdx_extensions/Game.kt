package com.mygdx.game.gdx_extensions

import com.badlogic.gdx.Game
import com.badlogic.gdx.utils.ObjectMap
import com.mygdx.game.Jetrix
import com.mygdx.game.screens.GameScreen

class Game : Game() {
    val screens = ObjectMap<Class<GameScreen>, GameScreen>()

    fun attachScreen(screen: Class<GameScreen>) {
        screens.put(screen, screen.getConstructor(this.javaClass).newInstance(this))
    }

    override fun create() {
        TODO("Not yet implemented")
    }
    /*inline fun <reified T: GameScreen> setScreen() {
        setScreen(screens[T::javaClass])
    }*/
}