package com.android.example.cornerapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.android.example.cornerapp.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

/**
 * Created by kapera on 22-Apr-18.
 */
public class CustomHorizontalBarChart extends HorizontalBarChart {

    private final boolean DEFAULT_BOOLEAN = false;
    private final boolean DEFAULT_INVERSION = true;
    private final int DEFAULT_COLOR = getResources().getColor(R.color.chartLabelColor);
    private final int DEFAULT_AXISCOLOR = getResources().getColor(android.R.color.white);
    private final int DEFAULT_TEXTSIZE = 15;
    private final float DEFAULT_AXISWIDTH = 1f;
    private final int DEFAULT_LABELCOUNT = 3;
    private final String[] DEFAULT_LABELS = {"U", "H", "J"};

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
        TypedArray a = getResources().obtainAttributes(attrs, R.styleable.CustomHorizontalBarChart);

        Boolean isAxisLeftInverted = a.getBoolean(R.styleable.CustomHorizontalBarChart_isAxisLeftInverted, DEFAULT_INVERSION);
        Boolean isGridLines = a.getBoolean(R.styleable.CustomHorizontalBarChart_isGridLines, DEFAULT_BOOLEAN);
        Boolean isAxisLeft = a.getBoolean(R.styleable.CustomHorizontalBarChart_isAxisLeft, DEFAULT_BOOLEAN);
        Boolean isAxisRight = a.getBoolean(R.styleable.CustomHorizontalBarChart_isAxisRight, DEFAULT_BOOLEAN);
        Boolean isLegend = a.getBoolean(R.styleable.CustomHorizontalBarChart_isLegend, DEFAULT_BOOLEAN);
        Boolean isDescription = a.getBoolean(R.styleable.CustomHorizontalBarChart_isDescription, DEFAULT_BOOLEAN);

        int xTextColor = a.getColor(R.styleable.CustomHorizontalBarChart_xTextColor, DEFAULT_COLOR);
        int xAxisColor = a.getColor(R.styleable.CustomHorizontalBarChart_xLineColor, DEFAULT_AXISCOLOR);
        int xTextSize = a.getInt(R.styleable.CustomHorizontalBarChart_xTextSize, DEFAULT_TEXTSIZE);
        float xLineWidth = a.getFloat(R.styleable.CustomHorizontalBarChart_xLineWidth, DEFAULT_AXISWIDTH);
        int labelCount = a.getInt(R.styleable.CustomHorizontalBarChart_labelCount, DEFAULT_LABELCOUNT);

        getAxisLeft().setInverted(isAxisLeftInverted);
        getAxisLeft().setEnabled(isAxisLeft);
        getAxisRight().setEnabled(isAxisRight);
        getLegend().setEnabled(isLegend);
        getDescription().setEnabled(isDescription);
        getXAxis().setDrawGridLines(isGridLines);
        getXAxis().setTextColor(xTextColor);
        getXAxis().setAxisLineColor(xAxisColor);
        getXAxis().setTextSize(xTextSize);
        getXAxis().setAxisLineWidth(xLineWidth);
        getXAxis().setLabelCount(labelCount);
        setTouchEnabled(false);

        getXAxis().setValueFormatter(new IndexAxisValueFormatter(DEFAULT_LABELS));

        a.recycle();
    }
}
