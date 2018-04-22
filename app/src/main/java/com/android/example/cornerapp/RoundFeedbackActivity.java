package com.android.example.cornerapp;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.WindowInsets;
import android.widget.TextView;

import com.android.example.cornerapp.util.CSVFile;
import com.android.example.cornerapp.view.CustomHorizontalBarDataSet;
import com.android.example.cornerapp.view.CustomHorizontalBarChart;
import com.android.example.cornerapp.view.CustomHorizontalBarChartRender;
import com.android.example.cornerapp.view.CustomLineChart;
import com.android.example.cornerapp.view.CustomLineDataSet;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.listener.OnDrawListener;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class RoundFeedbackActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private LineChart mLineChart;
    private CustomLineDataSet mLineDataSet;
    private CustomHorizontalBarDataSet mBarDataSetLeft;
    private CustomHorizontalBarDataSet mBarDataSetRight;

    private TextView mPunches;
    private TextView mSpeed;
    private TextView mPower;

    private CustomHorizontalBarChart mLeftHandChart;
    private CustomHorizontalBarChart mRightHandChart;

    private Data mData;
    private List<Entry> mIntensityContent;
    private final int mPunchTarget = 100;
    private final int mPunchSeconds = 180;
    private final double mSessionLenght = 15.0;

    private SimpleDateFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_feedback);

        //set toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            // set title
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // show "up" button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mLineChart = (CustomLineChart) findViewById(R.id.lineChart_intensity);
        mPunches = (TextView) findViewById(R.id.text_punchesCount);
        mSpeed = (TextView) findViewById(R.id.text_speedValue);
        mPower = (TextView) findViewById(R.id.text_powerValue);
        mLeftHandChart = (CustomHorizontalBarChart) findViewById(R.id.barChart_leftHand);
        mRightHandChart = (CustomHorizontalBarChart) findViewById(R.id.barChart_rightHand);

        //parse CSV file into data object
        InputStream inputStream = getResources().openRawResource(R.raw.round2);
        mData = parseData(inputStream);
        mData.init(mPunchTarget, mPunchSeconds, mSessionLenght);

        setupIntensityChart();
        setupTrainingInfo();
        setupHandChart();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // set gradient after line chart has been initialized
        this.findViewById(R.id.lineChart_intensity).post(new Runnable() {
            @Override
            public void run() {
                if (mLineDataSet != null)
                    mLineDataSet.setLineGradient(mLineChart, getResources().getColor(R.color.chartHighGreen),
                            getResources().getColor(R.color.chartLowRed));
                mLineChart.invalidate();
            }
        });
    }

    /**
     * Parse column separated values from csv file into data object
     *
     * @param inputStream csv file
     * @return data object
     */
    private Data parseData(InputStream inputStream) {
        Data data = new Data();
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> csvParsed = csvFile.read();

        // time format for parsing time information
        formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        for (int i = 0; i < csvParsed.size(); i++) {
            //ignore first four columns because this is table header
            if (i < 4)
                continue;

            try {
                // get timestamp string value
                Date date = formatter.parse(csvParsed.get(i)[0]);
                long d = date.getTime();

                data.addPunch(new Punch(date.getTime(), Integer.parseInt(csvParsed.get(i)[1]),
                        Double.parseDouble(csvParsed.get(i)[2]), Double.parseDouble(csvParsed.get(i)[3])));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * @return list of entries for intensity line chart with specific intervals
     */
    private List<Entry> parseIntensityContent() {
        List<Entry> values = new ArrayList<>();
        for (int i = 0; i < mData.getmPunchesPercent().size(); i++) {
            values.add(new Entry(15 * (i + 1), mData.getmPunchesPercent().get(i)));
        }
        return values;
    }

    /**
     * Fill chart with data and adjust its appearance
     */
    private void setupIntensityChart() {
        //parse data object into intensity chart x and y values
        mIntensityContent = parseIntensityContent();

        Drawable backgroundGradient = ContextCompat.getDrawable(this, R.drawable.gradient_intensity);
        mLineDataSet = new CustomLineDataSet(mIntensityContent, "Intensity");
        mLineDataSet.setBackgroundGradient(backgroundGradient);
        mLineDataSet.setLineWidth(5f);

        LineData lineData = new LineData(mLineDataSet);

        mLineChart.setData(lineData);
        mLineChart.setBackgroundColor(getResources().getColor(R.color.bgColor));

        mLineDataSet.setValuesFormatter(13, getResources().getColor(R.color.chartValueGreen), getResources().getColor(R.color.chartValueRed));

        // format values when focus was changed
        mLineChart.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                mLineDataSet.setValuesFormatter(13, getResources().getColor(R.color.chartValueGreen), getResources().getColor(R.color.chartValueRed));
            }
        });

        mLineChart.invalidate();
    }

    /**
     * Fill textViews with specific data
     */
    private void setupTrainingInfo() {
        int punchesSum = mData.getmPunches().size();

        mPunches.setText(String.valueOf(punchesSum));
        mSpeed.setText(String.format("%.2f", mData.getmAvgSpeed()));
        mPower.setText(String.format("%.2f", mData.getmAvgPower()));
    }

    /**
     * Fill chart with data and adjust its appearance
     */
    private void setupHandChart() {
        //adjust data for left chart
        List<BarEntry> dataLeft = new ArrayList<>();
        dataLeft.add(new BarEntry(0, mData.getmLeftUppercut()));
        dataLeft.add(new BarEntry(1, mData.getmLeftHook()));
        dataLeft.add(new BarEntry(2, mData.getmLeftJab()));

        //adjust data for right chart
        List<BarEntry> dataRight = new ArrayList<>();
        dataRight.add(new BarEntry(0, -1));
        dataRight.add(new BarEntry(0, mData.getmRightUppercut()));
        dataRight.add(new BarEntry(1, mData.getmRightHook()));
        dataRight.add(new BarEntry(2, mData.getmRightCross()));

        mBarDataSetLeft = new CustomHorizontalBarDataSet(dataLeft, "Type Left", getResources().getColor(R.color.chartValueGreen), 15);
        mBarDataSetRight = new CustomHorizontalBarDataSet(dataRight, "Type Right", getResources().getColor(R.color.chartValueBlue), 15);

        // move values a bars themselves into correct position
        mLeftHandChart.setRenderer(new CustomHorizontalBarChartRender(mData.getmTopPunch() * -1, mLeftHandChart, mLeftHandChart.getAnimator(), mLeftHandChart.getViewPortHandler()));
        mRightHandChart.setRenderer(new CustomHorizontalBarChartRender(mData.getmTopPunch(), mRightHandChart, mRightHandChart.getAnimator(), mRightHandChart.getViewPortHandler()));
        mRightHandChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(new String[]{"U", "H", "C"}));
        mRightHandChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        BarData barDataLeft = new BarData(mBarDataSetLeft);
        barDataLeft.setBarWidth(.1f);

        BarData barDataRight = new BarData(mBarDataSetRight);
        barDataRight.setBarWidth(.1f);

        mLeftHandChart.setData(barDataLeft);
        mRightHandChart.setData(barDataRight);

        if(mData.ismIsLeftBigger())
            mRightHandChart.setVisibleYRangeMinimum(mData.getmTopPunch(), YAxis.AxisDependency.LEFT);
        else
            mLeftHandChart.setVisibleYRangeMinimum(mData.getmTopPunch(), YAxis.AxisDependency.RIGHT);

        mLeftHandChart.invalidate();
        mRightHandChart.invalidate();
    }
}
