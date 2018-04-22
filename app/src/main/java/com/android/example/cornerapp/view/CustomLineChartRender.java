package com.android.example.cornerapp.view;

import android.graphics.Canvas;
import android.support.annotation.Px;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by kapera on 21-Apr-18.
 */
public class CustomLineChartRender extends LineChartRenderer {

    @Px
    private int yOffset;

    public CustomLineChartRender(int yOffset, LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
        this.yOffset = yOffset;
    }

    /**
     * Draw value with specific yOffset. If value is negative we have to consider also label's height
     */
    @Override
    public void drawValue(Canvas c, IValueFormatter formatter, float value, Entry entry, int dataSetIndex, float x, float y, int color) {
        if (value < 0)
            super.drawValue(c, formatter, value, entry, dataSetIndex, x, y + 2*this.yOffset, color);
        else
            super.drawValue(c, formatter, value, entry, dataSetIndex, x, y - this.yOffset, color);
    }
}
