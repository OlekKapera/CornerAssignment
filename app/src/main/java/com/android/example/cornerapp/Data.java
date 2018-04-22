package com.android.example.cornerapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kapera on 21-Apr-18.
 */
public class Data {

    private List<Punch> mPunches = new ArrayList<>();
    private List<Integer> mPunchesPercent = new ArrayList<>();

    private double mAvgSpeed = 0;
    private double mAvgPower = 0;

    private int mLeftJab;
    private int mLeftHook;
    private int mLeftUppercut;
    private int mRightCross;
    private int mRightHook;
    private int mRightUppercut;

    private int mTopPunch;
    private boolean mIsLeftBigger;
    private double mInterval;

    public Data() {
    }

    public Data(List<Punch> punches) {
        this.mPunches = punches;
    }

    /**
     * Initialize all class params accordingly to gained data
     */
    public void init(double target, double seconds, double interval) {
        this.mInterval = interval;

        calcPunchesPercents(target, seconds, interval);
        calcPunches();
        calcAvg();
    }

    /**
     * Calculate how many mPunches in percents have been made considering targeted amount
     *
     * @param target  target mPunches per session
     * @param seconds session length
     */
    private void calcPunchesPercents(double target, double seconds, double interval) {
        double punchPerSet = (target / seconds) * interval;
        int punchCount = 0;
        int intervalsCount = 1;

        for (int i = 0; i < mPunches.size(); i++) {
            if (mPunches.get(i).getTimestamp() > (interval * 1000 * intervalsCount)) {
                mPunchesPercent.add((int) Math.round((punchCount / punchPerSet * 100) - 100));
                punchCount = 0;
                intervalsCount++;
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
        for (Punch p : mPunches) {
            speed += p.getSpeed();
            power += p.getPower();
        }

        speed /= mPunches.size();
        power /= mPunches.size();

        mAvgSpeed = speed;
        mAvgPower = power;
    }

    /**
     * Calculate how many different types of mPunches each hand has made and which type of punch was used the most
     */
    private void calcPunches() {
        for (int i = 0; i < mPunches.size(); i++) {
            switch (mPunches.get(i).getType()) {
                case 0:
                    mLeftJab++;
                    break;
                case 1:
                    mLeftHook++;
                    break;
                case 2:
                    mLeftUppercut++;
                    break;
                case 3:
                    mRightCross++;
                    break;
                case 4:
                    mRightHook++;
                    break;
                case 5:
                    mRightUppercut++;
                    break;
            }
        }

        if (mLeftJab > mTopPunch) {
            mTopPunch = mLeftJab;
            mIsLeftBigger = true;
        }
        if (mLeftHook > mTopPunch) {
            mTopPunch = mLeftHook;
            mIsLeftBigger = true;
        }
        if (mLeftUppercut > mTopPunch) {
            mTopPunch = mLeftUppercut;
            mIsLeftBigger = true;
        }
        if (mRightCross > mTopPunch) {
            mTopPunch = mRightCross;
            mIsLeftBigger = false;
        }
        if (mRightHook > mTopPunch) {
            mTopPunch = mRightHook;
            mIsLeftBigger = false;
        }
        if (mRightUppercut > mTopPunch) {
            mTopPunch = mRightUppercut;
            mIsLeftBigger = false;
        }
    }

    public void addPunch(Punch punch) {
        mPunches.add(punch);
    }

    public Punch getPunch(int index) {
        return mPunches.get(index);
    }

    public List<Punch> getmPunches() {
        return mPunches;
    }

    public void setmPunches(List<Punch> mPunches) {
        this.mPunches = mPunches;
    }

    public List<Integer> getmPunchesPercent() {
        return mPunchesPercent;
    }

    public void setmPunchesPercent(List<Integer> mPunchesPercent) {
        this.mPunchesPercent = mPunchesPercent;
    }

    public int getmLeftJab() {
        return mLeftJab;
    }

    public void setmLeftJab(int mLeftJab) {
        this.mLeftJab = mLeftJab;
    }

    public int getmLeftHook() {
        return mLeftHook;
    }

    public void setmLeftHook(int mLeftHook) {
        this.mLeftHook = mLeftHook;
    }

    public int getmLeftUppercut() {
        return mLeftUppercut;
    }

    public void setmLeftUppercut(int mLeftUppercut) {
        this.mLeftUppercut = mLeftUppercut;
    }

    public int getmRightCross() {
        return mRightCross;
    }

    public void setmRightCross(int mRightCross) {
        this.mRightCross = mRightCross;
    }

    public int getmRightHook() {
        return mRightHook;
    }

    public void setmRightHook(int mRightHook) {
        this.mRightHook = mRightHook;
    }

    public int getmRightUppercut() {
        return mRightUppercut;
    }

    public void setmRightUppercut(int mRightUppercut) {
        this.mRightUppercut = mRightUppercut;
    }

    public double getmAvgSpeed() {
        return mAvgSpeed;
    }

    public void setmAvgSpeed(double mAvgSpeed) {
        this.mAvgSpeed = mAvgSpeed;
    }

    public double getmAvgPower() {
        return mAvgPower;
    }

    public void setmAvgPower(double mAvgPower) {
        this.mAvgPower = mAvgPower;
    }

    public int getmTopPunch() {
        return mTopPunch;
    }

    public void setmTopPunch(int mTopPunch) {
        this.mTopPunch = mTopPunch;
    }

    public boolean ismIsLeftBigger() {
        return mIsLeftBigger;
    }

    public void setmIsLeftBigger(boolean mIsLeftBigger) {
        this.mIsLeftBigger = mIsLeftBigger;
    }

    public double getmInterval() {
        return mInterval;
    }

    public void setmInterval(double mInterval) {
        this.mInterval = mInterval;
    }
}
