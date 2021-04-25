package com.mygdx.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture

class TextureComponent(val texture: Texture, val isBackground: Boolean = false) : Component