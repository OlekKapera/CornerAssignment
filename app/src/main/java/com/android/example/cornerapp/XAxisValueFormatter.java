package com.android.example.cornerapp;

import android.icu.util.TimeUnit;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by kapera on 21-Apr-18.
 */
public class XAxisValueFormatter implements IAxisValueFormatter {

    public XAxisValueFormatter() {
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return (int) (value / 60) + 1 + ":00";
    }
}
