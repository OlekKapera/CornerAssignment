package com.android.example.cornerapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kapera on 21-Apr-18.
 */
public class Data {

    private List<Punch> punches = new ArrayList<>();
    private List<Integer> punchesPercent = new ArrayList<>();

    private double avgSpeed = 0;
    private double avgPower = 0;

    private int leftJab;
    private int leftHook;
    private int leftUppercut;
    private int rightCross;
    private int rightHook;
    private int rightUppercut;

    public Data() {
    }

    public Data(List<Punch> punches) {
        this.punches = punches;
    }

    /**
     * Initialize all class params accordingly to gained data
     */
    public void init(double target, double seconds) {
        calcPunchesPercents(target, seconds);
        calcPunches();
        calcAvg();
    }

    /**
     * Calculate how many punches in percents have been made considering targeted amount
     *
     * @param target  target punches per session
     * @param seconds session length
     */
    private void calcPunchesPercents(double target, double seconds) {
        double punchPerSet = (target / seconds) * 15.0;
        int punchCount = 0;
        int interval = 1;

        for (int i = 0; i < punches.size(); i++) {
            if (punches.get(i).getTimestamp() > (15000 * interval)) {
                punchesPercent.add((int) Math.round((punchCount / punchPerSet * 100) - 100));
                punchCount = 0;
                interval++;
            }
            punchCount++;
        }
    }

    /**
     * Calculate average speed and power
     */
    public void calcAvg() {
        double speed = 0;
        double power = 0;
        for (Punch p : punches) {
            speed += p.getSpeed();
            power += p.getPower();
        }

        speed /= punches.size();
        power /= punches.size();

        avgSpeed = speed;
        avgPower = power;
    }

    /**
     * Calculate how many different types of punches each hand has made
     */
    private void calcPunches() {
        for (int i = 0; i < punches.size(); i++) {
            switch (punches.get(i).getType()) {
                case 0:
                    leftJab++;
                    break;
                case 1:
                    leftHook++;
                    break;
                case 2:
                    leftUppercut++;
                    break;
                case 3:
                    rightCross++;
                    break;
                case 4:
                    rightHook++;
                    break;
                case 5:
                    rightUppercut++;
                    break;
            }
        }
    }

    public void addPunch(Punch punch) {
        punches.add(punch);
    }

    public Punch getPunch(int index) {
        return punches.get(index);
    }

    public List<Punch> getPunches() {
        return punches;
    }

    public void setPunches(List<Punch> punches) {
        this.punches = punches;
    }

    public List<Integer> getPunchesPercent() {
        return punchesPercent;
    }

    public void setPunchesPercent(List<Integer> punchesPercent) {
        this.punchesPercent = punchesPercent;
    }

    public int getLeftJab() {
        return leftJab;
    }

    public void setLeftJab(int leftJab) {
        this.leftJab = leftJab;
    }

    public int getLeftHook() {
        return leftHook;
    }

    public void setLeftHook(int leftHook) {
        this.leftHook = leftHook;
    }

    public int getLeftUppercut() {
        return leftUppercut;
    }

    public void setLeftUppercut(int leftUppercut) {
        this.leftUppercut = leftUppercut;
    }

    public int getRightCross() {
        return rightCross;
    }

    public void setRightCross(int rightCross) {
        this.rightCross = rightCross;
    }

    public int getRightHook() {
        return rightHook;
    }

    public void setRightHook(int rightHook) {
        this.rightHook = rightHook;
    }

    public int getRightUppercut() {
        return rightUppercut;
    }

    public void setRightUppercut(int rightUppercut) {
        this.rightUppercut = rightUppercut;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public double getAvgPower() {
        return avgPower;
    }

    public void setAvgPower(double avgPower) {
        this.avgPower = avgPower;
    }
}
