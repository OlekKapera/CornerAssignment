package com.android.example.cornerapp.view;

import android.graphics.Canvas;
import android.support.annotation.Px;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by kapera on 22-Apr-18.
 */
public class CustomHorizontalBarChartRender extends HorizontalBarChartRenderer {

    @Px
    private int xOffset;

    public CustomHorizontalBarChartRender(int xOffset, BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
        this.xOffset = xOffset;
    }

    @Override
    public void drawValue(Canvas c, IValueFormatter formatter, float value, Entry entry, int dataSetIndex, float x, float y, int color) {
        super.drawValue(c, formatter, value, entry, dataSetIndex, (x + xOffset), y+xOffset, color);
    }
}
