package com.android.example.cornerapp.view;

import android.support.annotation.Nullable;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

/**
 * Created by kapera on 22-Apr-18.
 */
public class CustomHorizontalBarDataSet extends BarDataSet {

    public CustomHorizontalBarDataSet(List<BarEntry> yVals, String label, @Nullable int color, @Nullable int size) {
        super(yVals, label);

        // set default parameters
        if (color != 0) {
            setColor(color);
            setValueTextColor(color);
        }

        if (size != 0)
            setValueTextSize(size);

        //set formatter for casting values into integers
        setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.valueOf((int) value);
            }
        });
    }
}
