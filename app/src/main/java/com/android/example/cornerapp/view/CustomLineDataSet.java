package com.android.example.cornerapp.view;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kapera on 18-Apr-18.
 */
public class CustomLineDataSet extends LineDataSet {

    private static boolean topValue = false;
    private static boolean lowValue = false;

    public CustomLineDataSet(List<Entry> data, String label) {
        super(data, label);
    }

    /**
     * Set gradient background
     *
     * @param backgroundGradient gradient for chart's background
     */
    public void setBackgroundGradient(Drawable backgroundGradient) {
        setFillDrawable(backgroundGradient);
        setDrawFilled(true);
    }

    /**
     * Set gradient lines and its width
     *
     * @param mChart     chart used for adding current data set
     * @param lineColor1 first gradient color for line
     * @param lineColor2 second gradient color for line
     */
    public void setLineGradient(LineChart mChart, int lineColor1, int lineColor2) {
        Paint paint = mChart.getRenderer().getPaintRender();
        int height = mChart.getHeight();

        LinearGradient linGrad = new LinearGradient(0, 0, 0, height, lineColor1, lineColor2, Shader.TileMode.REPEAT);
        paint.setShader(linGrad);
    }

    /**
     * Format values labels
     * @param textSize size of label
     * @param topValueColor color of the biggest label value
     * @param lowValueColor color of the lowest label value
     */
    public void setValuesFormatter(float textSize, int topValueColor, int lowValueColor) {
        topValue = false;
        lowValue = false;

        setDrawCircles(false);
        setIconsOffset(new MPPointF(0, 5));
        setValueTextSize(textSize);

        List<Integer> valueColors = new ArrayList<>();
        valueColors.add(lowValueColor);
        valueColors.add(topValueColor);
        setValueTextColors(valueColors);

        // adjust labels to view only the highest a the lowest value with percent sign at the end
        setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                if (getYMax() == value && !topValue) {
                    topValue = true;
                    return (int) value + "%";
                } else if (getYMin() == value && !lowValue) {
                    lowValue = true;
                    return (int) value + "%";
                } else
                    return "";
            }
        });
    }
}
