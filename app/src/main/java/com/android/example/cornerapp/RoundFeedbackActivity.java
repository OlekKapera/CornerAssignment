package com.android.example.cornerapp;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.example.cornerapp.view.CustomLineDataSet;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;
import java.util.List;

public class RoundFeedbackActivity extends AppCompatActivity {

    private LineChart mLineChart;
    private CustomLineDataSet mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_feedback);

        mLineChart = (LineChart) findViewById(R.id.intensity_lineChart);

        List<Entry> list = new ArrayList<>();
        list.add(new Entry(2, 5));
        list.add(new Entry(3, 6));
        list.add(new Entry(4, 2));
        list.add(new Entry(5, -3));
        list.add(new Entry(6, -5));

        Drawable backgroundGradient = ContextCompat.getDrawable(this, R.drawable.gradient_intensity);
        mDataSet = new CustomLineDataSet(list, "Intensity");
        mDataSet.setBackgroundGradient(backgroundGradient);
        mDataSet.setValuesFormatter(10, getResources().getColor(R.color.chartValueGreen), getResources().getColor(R.color.chartValueRed));

        LineData lineData = new LineData(mDataSet);

        mLineChart.setData(lineData);
        mLineChart.getAxisLeft().setEnabled(false);
        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.getLegend().setEnabled(false);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.getXAxis().setPosition(com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM);
        mLineChart.getXAxis().setGranularity(1f);
        mLineChart.getXAxis().setGridColor(getResources().getColor(R.color.chartVerticalAxis));
        mLineChart.setBackgroundColor(getResources().getColor(R.color.bgColor));
        mLineChart.invalidate();
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                if (mDataSet != null)
                    mDataSet.setLineGradient(mLineChart, getResources().getColor(R.color.chartHighGreen),
                            getResources().getColor(R.color.chartLowRed), 5);
            }
        });
    }
}
