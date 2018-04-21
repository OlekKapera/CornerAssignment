package com.android.example.cornerapp;

/**
 * Created by kapera on 21-Apr-18.
 */
public class Punch {

    private long timestamp;
    private int type;
    private double speed;
    private double power;

    public Punch(long timestamp, int type, double speed, double power) {
        this.timestamp = timestamp;
        this.type = type;
        this.speed = speed;
        this.power = power;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }
}
