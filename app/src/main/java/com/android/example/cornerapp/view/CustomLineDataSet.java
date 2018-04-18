package com.android.example.cornerapp.view;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.List;

/**
 * Created by kapera on 18-Apr-18.
 */
public class CustomLineDataSet extends LineDataSet {

    public CustomLineDataSet(List<Entry> data, String label) {
        super(data, label);
    }

    /**
     * Set gradient background
     *
     * @param backgroundGradient gradient for chart's background
     */
    public void setBackgroundGradient(Drawable backgroundGradient){
        setFillDrawable(backgroundGradient);
        setDrawFilled(true);
    }

    /**
     * Set gradient lines and its width
     *
     * @param mChart chart used for adding current data set
     * @param lineColor1 first gradient color for line
     * @param lineColor2 second gradient color for line
     * @param lineWidth width of the line
     */
    public void setLineGradient(LineChart mChart, int lineColor1, int lineColor2, float lineWidth){
        Paint paint = mChart.getRenderer().getPaintRender();
        int height = mChart.getHeight();

        LinearGradient linGrad = new LinearGradient(0, 0, 0, height, lineColor1, lineColor2, Shader.TileMode.REPEAT);
        paint.setShader(linGrad);

        setLineWidth(lineWidth);
    }
}
