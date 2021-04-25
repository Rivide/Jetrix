package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Jet extends GameObject {
    public float speed;
    private float reload;
    private float reloadProgress;

    public Jet(float x, float y, float rotation, int health, int damage, float speed) {
        super(x, y, rotation, new Texture("jet.png"), health, damage);
        this.speed = speed;
        reload = .5f;
        reloadProgress = 0;
    }
    public Array<Bullet> fire(float dt) {
        reloadProgress += dt;
        Array<Bullet> bullets = new Array<Bullet>();

        if (reloadProgress >= reload) {
            Bullet b = new Bullet(
                    position.x, position.y,
                    (float) Math.cos(rotation * Math.PI / 180 + Math.PI / 2) * 100,
                    (float) Math.sin(rotation * Math.PI / 180 + Math.PI / 2) * 100,
                    rotation
            );
            b.addPosition(
                    (float) (Math.cos(rotation * Math.PI / 180 + Math.PI / 2) *
                            (texture.getWidth() / 2f + b.getTexture().getWidth() / 2f + 2) +
                            texture.getWidth() / 2f - b.getTexture().getWidth() / 2f),
                    (float) (Math.sin(rotation * Math.PI / 180 + Math.PI / 2) *
                            (texture.getHeight() / 2f + b.getTexture().getHeight() / 2f + 2) +
                            texture.getHeight() / 2f - b.getTexture().getHeight() / 2f)
            );
            bullets.addAll(b);
            reloadProgress = 0;
        }
        return bullets;
    }
    public String toString() {
        return "Jet " + position;
    }
}
