package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;

public abstract class GameObject {
    protected Texture texture;
    protected Vector3 position;
    protected float rotation;
    protected Polygon bounds;
    protected int health;
    protected int damage;

    public GameObject(float x, float y, float rotation, Texture texture, int health, int damage) {
        position = new Vector3(x, y, 0);
        this.rotation = rotation;
        this.texture = texture;
        this.health = health;
        this.damage = damage;
        bounds = new Polygon(new float[]{
                0, 0,
                0 + texture.getWidth(), 0,
                0 + texture.getWidth(), 0 + texture.getHeight(),
                0, 0 + texture.getHeight(),
        });
        bounds.setPosition(x, y);
        bounds.setOrigin(texture.getWidth() / 2f, texture.getHeight() /2f);
        bounds.setRotation(rotation);
    }
    public void draw(SpriteBatch sb) {
        sb.draw(texture,
                position.x,
                position.y,
                texture.getWidth() / 2f,
                texture.getHeight() / 2f,
                texture.getWidth(),
                texture.getHeight(),
                1,
                1,
                rotation,
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                false,
                false);

    }
    public void setPosition(Vector3 pos) {
        position.set(pos);
        bounds.setPosition(pos.x, pos.y);
    }
    public void addPosition(float x, float y) {
        position.add(new Vector3(x, y, 0));
        bounds.setPosition(position.x, position.y);
    }
    public Vector3 getPosition() {
        return position;
    }
    public Texture getTexture() {
        return texture;
    }
    public Polygon getBounds() {
        return bounds;
    }
    public boolean checkCollision(GameObject obj) {
        if (Intersector.overlapConvexPolygons(this.bounds, obj.getBounds())) {
            health -= obj.getDamage();
            System.out.println("HOO");
            return true;
        }
        return false;
    }
    public int getDamage() {
        return damage;
    }
    public boolean isDestroyed() {
        return health <= 0;
    }
    public void drawBounds(ShapeRenderer sr) {
        sr.polygon(bounds.getTransformedVertices());
    }
}
