package com.mygdx.game.gdx_extensions

import com.badlogic.gdx.math.Vector2

operator fun Vector2.plus(vector: Vector2): Vector2 {
    return Vector2(x + vector.x, y + vector.y)
}

operator fun Vector2.minus(vector: Vector2): Vector2 {
    return plus(-vector)
}

operator fun Vector2.times(scalar: Float): Vector2 {
    return Vector2(x * scalar, y * scalar)
}

operator fun Vector2.div(scalar: Float): Vector2 {
    return times(1 / scalar)
}

operator fun Vector2.unaryMinus(): Vector2 {
    return times(-1f)
}