package com.android.example.cornerapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kapera on 21-Apr-18.
 */
public class Data {

    private List<Punch> punches = new ArrayList<>();
    private List<Integer> punchesPercent = new ArrayList<>();

    public Data() {
    }

    public Data(List<Punch> punches) {
        this.punches = punches;
    }

    public void addPunch(Punch punch) {
        punches.add(punch);
    }

    public Punch getPunch(int index){
        return punches.get(index);
    }

    public void calcPunchesPercents(double target, double seconds){
        double punchPerSet = (target / seconds) * 15.0;
        int punchCount = 0;
        int interval = 1;

        for(int i =0 ; i < punches.size();i++){
            if(punches.get(i).getTimestamp() > (15000*interval)){
                punchesPercent.add((int) Math.round((punchCount / punchPerSet * 100)-100));
                punchCount = 0;
                interval++;
            }
            punchCount++;
        }
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
}
