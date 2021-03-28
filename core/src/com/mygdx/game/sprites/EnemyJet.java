package com.mygdx.game.sprites;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Instruction;

public class EnemyJet extends Jet {
    private Array<Instruction> ai;
    private float age;
    public EnemyJet(float x, float y, float rotation, float speed, Instruction... instructions) {
        super(x, y, rotation, 100, 20, speed);
        ai = new Array<Instruction>();
        for (Instruction inst : instructions) {
            addInstruction(inst);
        }

    }
    public void addInstruction(Instruction inst) {
        ai.add(inst);
    }
    public void followInstruction(Instruction inst, float dt) {
        Object[] params = inst.getParams();
        Class[] classes = new Class[params.length + 1];
        Object[] fullParams = new Object[params.length + 1];
        for (int i = 0; i < params.length; i++) {
            classes[i] = params[i].getClass();
            fullParams[i] = params[i];
        }
        classes[classes.length - 1] = ((Float) dt).getClass();
        fullParams[fullParams.length - 1] = dt;
        try {
            EnemyJet.class.getMethod(inst.getMethodName(), classes).invoke(this, fullParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveTo(Float x, Float y, Float dt) {
        Vector3 dir = new Vector3(x, y, 0);
        Vector3 dist = new Vector3();
        dist.set(dir.sub(position));
        dir.nor();
        dir.scl(Math.min(dt * speed, dist.len()));
        addPosition(dir.x, dir.y);
    }
    public void update(float dt) {
        age += dt;
        for (Instruction inst : ai) {
            if (age >= inst.getStartTime() && age <= inst.getEndTime()) {
                if (inst.nextInterval(dt)) {
                    followInstruction(inst, dt);
                }
            }
        }
    }
    public String toString() {
        return "Enemy Jet " + position;
    }
}
