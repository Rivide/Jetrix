package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.Instruction;
import com.mygdx.game._Jetrix;
import com.mygdx.game.sprites.Bullet;
import com.mygdx.game.sprites.EnemyJet;
import com.mygdx.game.sprites.GameObject;
import com.mygdx.game.sprites.Jet;

import java.lang.reflect.Method;
import java.util.HashMap;

import sun.security.ssl.Debug;

public class PlayState extends State {

    private OrthographicCamera cam;
    private ShapeRenderer sr;

    private Jet jet;
    private float jetMouseOffsetY;

    private Texture bg;
    private float bgOffset = 0;

    private Array<Bullet> bullets;
    private Array<EnemyJet> enemies;
    private ArrayMap<EnemyJet, Method> spawnEnemies;
    private Array<Instruction> instructions;

    private boolean debug = false;

    public PlayState() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, _Jetrix.WIDTH, _Jetrix.HEIGHT);
        cam.position.x = _Jetrix.WIDTH / 2f;
        cam.position.y = _Jetrix.HEIGHT / 2f;
        sr = new ShapeRenderer();
        sr.setProjectionMatrix(cam.combined);

        jet = new Jet(_Jetrix.WIDTH / 2f - 32, 64, 0, 100, 20, 1);
        jetMouseOffsetY = 48;

        bg = new Texture("ocean.png");

        bullets = new Array<Bullet>();
        enemies = new Array<EnemyJet>();
        spawnEnemies = new ArrayMap<EnemyJet, Method>();
        try {
            spawnEnemies.put(
                    new EnemyJet(0, _Jetrix.HEIGHT, 225, 20,
                            new Instruction("moveTo", 0, 10, 0,
                                    0f, _Jetrix.HEIGHT - 64f)
                    ), PlayState.class.getMethod("and", Method.class, Method.class)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        instructions = new Array<Instruction>();
        /*instructions.addAll(
                new Instruction("spawnEnemy", 0, -1, )
        );*/
    }
    public void spawnEnemy(EnemyJet enemy) {

    }
    @Override
    public void update(float dt) {
        if (Gdx.input.isTouched()) {
            Vector3 touchDelta = new Vector3();
            touchDelta.set(Gdx.input.getDeltaX(), Gdx.input.getDeltaY(), 0);
            cam.unproject(touchDelta);
            touchDelta.sub(cam.unproject(Vector3.Zero.cpy()));
            jet.addPosition(touchDelta.x, touchDelta.y);
        }
        for (EnemyJet e : enemies) {
            e.update(dt);
        }
        for (Bullet b : bullets) {
            b.update(dt);
        }
        if (Gdx.input.isTouched()) {
            bullets.addAll(jet.fire(dt));
        }
        for (EnemyJet e : enemies) {
            bullets.addAll(e.fire(dt));
        }
        Array<GameObject> gameObjects = new Array<GameObject>();
        gameObjects.clear();
        gameObjects.add(jet);
        gameObjects.addAll(bullets);
        gameObjects.addAll(enemies);

        Array<Bullet> destroyedBullets = new Array<Bullet>();
        for (Bullet b : bullets) {
            Array<GameObject> otherObjects = new Array<GameObject>();
            otherObjects.addAll(gameObjects);
            otherObjects.removeValue(b, true);
            for (GameObject otherObj : otherObjects) {
                if (b.checkCollision(otherObj)) {
                    if (b.isDestroyed()) {
                        destroyedBullets.add(b);
                    }
                }
            }
        }

        Array<EnemyJet> destroyedEnemies = new Array<EnemyJet>();
        for (EnemyJet e : enemies) {
            Array<GameObject> otherObjects = new Array<GameObject>();
            otherObjects.addAll(gameObjects);
            otherObjects.removeValue(e, true);
            for (GameObject otherObj : otherObjects) {
                if (e.checkCollision(otherObj)) {
                    if (e.isDestroyed()) {
                        destroyedEnemies.add(e);
                    }
                }
            }
        }
        bullets.removeAll(destroyedBullets, true);
        enemies.removeAll(destroyedEnemies, true);

        bgOffset = (bgOffset - jet.speed) % -64;

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sr.setProjectionMatrix(cam.combined);
        sb.begin();
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (int x = 0; x < _Jetrix.WIDTH; x += bg.getWidth()) {
            for (int y = 0; y < _Jetrix.HEIGHT + Math.ceil(-bgOffset / 64) * 64; y += bg.getHeight()) {
                sb.draw(bg, x, y + bgOffset);
            }
        }
        for (Bullet b : bullets) {
            b.draw(sb);
            if (debug) {
                b.drawBounds(sr);
            }
        }
        for (EnemyJet e : enemies) {
            e.draw(sb);
            if (debug) {
                e.drawBounds(sr);
            }
        }
        jet.draw(sb);
        if (debug) {
            jet.drawBounds(sr);
        }
        sb.end();
        sr.end();
    }
}
