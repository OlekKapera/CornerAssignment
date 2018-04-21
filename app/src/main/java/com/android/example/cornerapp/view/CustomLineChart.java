package com.android.example.cornerapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.android.example.cornerapp.R;
import com.android.example.cornerapp.XAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;

import static com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM;

/**
 * Created by kapera on 21-Apr-18.
 */
public class CustomLineChart extends LineChart {

    private final boolean DEFAULT_BOOLEAN = false;
    private final float DEFAULT_GRANULARITY = 59.999f;
    private final int DEFAULT_GRIDCOLOR = getResources().getColor(R.color.chartVerticalAxis);
    private final int DEFAULT_TEXTCOLOR = getResources().getColor(R.color.chartLabelColor);
    private final float DEFAULT_GRIDWIDTH = 2f;
    private final float DEFAULT_YOFFSET = 20f;
    private final float DEFAULT_EXBOTTOMOFFSET = 1f;

    public CustomLineChart(Context context) {
        super(context);
        init(null);
    }

    public CustomLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    /**
     * Method for initializing chart view. When specific attributes are added set them, otherwise use
     * default parameters
     */
    private void init(AttributeSet attrs) {
        TypedArray a = getResources().obtainAttributes(attrs, R.styleable.CustomLineChart);

        Boolean isAxisLeft = a.getBoolean(R.styleable.CustomLineChart_isAxisLeft, DEFAULT_BOOLEAN);
        Boolean isAxisRight = a.getBoolean(R.styleable.CustomLineChart_isAxisRight, DEFAULT_BOOLEAN);
        Boolean isLegend = a.getBoolean(R.styleable.CustomLineChart_isLegend, DEFAULT_BOOLEAN);
        Boolean isDescription = a.getBoolean(R.styleable.CustomLineChart_isDescription, DEFAULT_BOOLEAN);
        Boolean isXAxisLine = a.getBoolean(R.styleable.CustomLineChart_isXAxisLine, DEFAULT_BOOLEAN);

        float granularity = a.getFloat(R.styleable.CustomLineChart_xGranularity, DEFAULT_GRANULARITY);
        int gridColor = a.getColor(R.styleable.CustomLineChart_xGridColor, DEFAULT_GRIDCOLOR);
        int textColor = a.getColor(R.styleable.CustomLineChart_xTextColor, DEFAULT_TEXTCOLOR);
        float gridWidth = a.getFloat(R.styleable.CustomLineChart_xGridWidth, DEFAULT_GRIDWIDTH);
        float yOffset = a.getFloat(R.styleable.CustomLineChart_xOffsetY, DEFAULT_YOFFSET);
        float exBottomOffset = a.getFloat(R.styleable.CustomLineChart_extraBottomOffset, DEFAULT_EXBOTTOMOFFSET);

        getAxisLeft().setEnabled(isAxisLeft);
        getAxisRight().setEnabled(isAxisRight);
        getLegend().setEnabled(isLegend);
        getDescription().setEnabled(isDescription);
        getXAxis().setDrawAxisLine(isXAxisLine);
        getXAxis().setPosition(BOTTOM);
        getXAxis().setGranularity(granularity);
        getXAxis().setGridColor(gridColor);
        getXAxis().setTextColor(textColor);
        getXAxis().setGridLineWidth(gridWidth);
        getXAxis().setYOffset(yOffset);
        setExtraBottomOffset(exBottomOffset);
        getXAxis().setValueFormatter(new XAxisValueFormatter());
        setTouchEnabled(false);

        //adjust y offset to fix cutting off x labels
        int offset = getResources().getDimensionPixelSize(R.dimen.chartYOffset);
        setRenderer(new CustomLineChartRender(offset, this, this.getAnimator(), this.getViewPortHandler()));

        a.recycle();
    }
}
