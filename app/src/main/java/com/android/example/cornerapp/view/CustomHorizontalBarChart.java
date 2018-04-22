package com.android.example.cornerapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.android.example.cornerapp.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;

/**
 * Created by kapera on 22-Apr-18.
 */
public class CustomHorizontalBarChart extends HorizontalBarChart {

    public CustomHorizontalBarChart(Context context) {
        super(context);
        init(null);
    }

    public CustomHorizontalBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomHorizontalBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    /**
     * Method for initializing chart view. When specific attributes are added set them, otherwise use
     * default parameters
     */
    private void init(AttributeSet attrs) {
        TypedArray a = getResources().obtainAttributes(attrs, R.styleable.CustomLineChart);

    }
}
