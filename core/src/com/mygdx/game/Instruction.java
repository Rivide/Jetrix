package com.mygdx.game;

import com.badlogic.gdx.utils.Array;

import java.lang.reflect.Method;

public class Instruction {
    private String methodName;
    private float startTime;
    private float endTime;
    private float repeatTime;
    private Object[] params;
    private float intervalAge;
    public Instruction(String methodName, float startTime, float endTime, float repeatTime,
                       Object... params) {
        this.methodName = methodName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeatTime = repeatTime;
        this.params = params;
        intervalAge = 0;
    }
    //public Instruction(String methodName, Object caller, )

    public String getMethodName() {
        return methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public float getStartTime() {
        return startTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public boolean nextInterval(float dt) {
        intervalAge += dt;
        if (intervalAge >= repeatTime) {
            if (repeatTime == 0) {
                intervalAge = 0;
            }
            else {
                intervalAge %= repeatTime;
            }
            return true;
        }
        return false;
    }
}
