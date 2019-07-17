package com.tcg.asteroids.util;

public class Timer {

    public enum TimerType {
        RUN_ONCE, RUN_CONTINUOUS
    }

    private TimerEvent event;
    private float time;
    private float stateTime;
    private int timesRun;

    private TimerType type;

    public Timer(float time, TimerType type) {
        this.event = null;
        this.time = time;
        this.stateTime = 0;
        this.timesRun = 0;
        this.type = type;
    }

    public void setTimerEvent(TimerEvent event) {
        this.event = event;
    }

    public void update(float dt) {
        if(event == null) {
            throw new IllegalStateException("No event set");
        }
        stateTime += dt;
        if(stateTime >= time) {
            if(type == TimerType.RUN_CONTINUOUS || timesRun == 0) {
                event.accept(this);
                stateTime = 0;
                timesRun++;
            }
        }
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void reset() {
        this.timesRun = 0;
        this.stateTime = 0;
    }

}
