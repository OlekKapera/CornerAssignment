package com.android.example.cornerapp;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
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

    private LineChart lineChart;
    private CustomLineDataSet dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_feedback);

        lineChart = (LineChart) findViewById(R.id.intensity_lineChart);

        List<Entry> list = new ArrayList<>();
        list.add(new Entry(2, 5));
        list.add(new Entry(3, 6));
        list.add(new Entry(4, 2));
        list.add(new Entry(5, -3));
        list.add(new Entry(6, -5));

        Drawable backgroundGradient = ContextCompat.getDrawable(this, R.drawable.gradient_intensity);
        dataSet = new CustomLineDataSet(list, "Intensity");
        dataSet.setBackgroundGradient(backgroundGradient);

        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);
        lineChart.setBackgroundColor(Color.parseColor("#ffffffff"));
        lineChart.invalidate();
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                if (dataSet != null)
                    dataSet.setLineGradient(lineChart, Color.parseColor("#3cff00"),
                            Color.parseColor("#ff0400"), 50);
            }
        });
    }
}
