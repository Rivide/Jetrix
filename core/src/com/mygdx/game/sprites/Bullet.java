package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;

public class Bullet extends GameObject {

    private Vector3 velocity;

    public Bullet(float x, float y, float velocityX, float velocityY, float rotation) {
        super(x, y, rotation, new Texture("bullet.png"), 10, 20);
        velocity = new Vector3(velocityX, velocityY, 0);
    }
    public void update(float dt) {
        velocity.scl(dt);
        addPosition(velocity.x, velocity.y);
        velocity.scl(1 / dt);
    }

    public String toString() {
        return "Bullet " + position;
    }
}
