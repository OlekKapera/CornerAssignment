package com.android.example.cornerapp;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.example.cornerapp.util.CSVFile;
import com.android.example.cornerapp.view.CustomHorizontalBarChartRender;
import com.android.example.cornerapp.view.CustomLineChart;
import com.android.example.cornerapp.view.CustomLineDataSet;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

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
    private CustomLineDataSet mDataSet;

    private TextView mPunches;
    private TextView mSpeed;
    private TextView mPower;

    private HorizontalBarChart mLeftHandChart;

    private Data mData;
    private List<Entry> mIntensityContent;
    private int mPunchTarget = 100;
    private int mPunchSeconds = 180;

    private SimpleDateFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_feedback);

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
        mLeftHandChart = (HorizontalBarChart) findViewById(R.id.barChart_leftHand);

        //parse CSV file into data object
        InputStream inputStream = getResources().openRawResource(R.raw.round2);
        mData = parseData(inputStream);
        mData.init(mPunchTarget, mPunchSeconds);

        setupIntensityChart();
        setupTrainingInfo();
        setupLeftHandChart();
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.findViewById(R.id.lineChart_intensity).post(new Runnable() {
            @Override
            public void run() {
                if (mDataSet != null)
                    mDataSet.setLineGradient(mLineChart, getResources().getColor(R.color.chartHighGreen),
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

        formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        for (int i = 0; i < csvParsed.size(); i++) {
            //ignore first four columns because this is table header
            if (i < 4)
                continue;

            try {
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

    private List<Entry> parseIntensityContent() {
        List<Entry> values = new ArrayList<>();
        for (int i = 0; i < mData.getPunchesPercent().size(); i++) {
            values.add(new Entry(15 * (i + 1), mData.getPunchesPercent().get(i)));
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
        mDataSet = new CustomLineDataSet(mIntensityContent, "Intensity");
        mDataSet.setBackgroundGradient(backgroundGradient);
        mDataSet.setValuesFormatter(13, getResources().getColor(R.color.chartValueGreen), getResources().getColor(R.color.chartValueRed));
        mDataSet.setLineWidth(5f);

        LineData lineData = new LineData(mDataSet);

        mLineChart.setData(lineData);
        mLineChart.setBackgroundColor(getResources().getColor(R.color.bgColor));

        mLineChart.invalidate();
    }

    /**
     * Fill textViews with specific data
     */
    private void setupTrainingInfo(){
        int punchesSum = mData.getPunches().size();

        mPunches.setText(String.valueOf(punchesSum));
        mSpeed.setText(String.format("%.2f",mData.getAvgSpeed()));
        mPower.setText(String.format("%.2f",mData.getAvgPower()));
    }

    /**
     * Fill chart with data and adjust its appearance
     */
    private void setupLeftHandChart() {
        //adjust data for chart
        List<BarEntry> data = new ArrayList<>();
        data.add(new BarEntry(0, mData.getLeftJab()));
        data.add(new BarEntry(1, mData.getLeftHook()));
        data.add(new BarEntry(2, mData.getLeftUppercut()));

        String[] yLabels = {"U","H","J"};

        BarDataSet dataSet = new BarDataSet(data, "Type Left");
        dataSet.setColor(getResources().getColor(R.color.chartValueGreen));
        dataSet.setValueTextColor(getResources().getColor(R.color.chartValueGreen));

        mLeftHandChart.getAxisLeft().setInverted(true);
        mLeftHandChart.getAxisLeft().setEnabled(false);
        mLeftHandChart.getAxisRight().setEnabled(false);
        mLeftHandChart.getXAxis().setDrawGridLines(false);
        mLeftHandChart.getXAxis().setTextColor(getResources().getColor(R.color.chartLabelColor));
        mLeftHandChart.getXAxis().setTextSize(15);
        mLeftHandChart.getXAxis().setAxisLineColor(getResources().getColor(android.R.color.white));
        mLeftHandChart.getXAxis().setAxisLineWidth(1);
        mLeftHandChart.getXAxis().setLabelCount(3);
        mLeftHandChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(yLabels));
        mLeftHandChart.setRenderer(new CustomHorizontalBarChartRender(200,mLeftHandChart, mLeftHandChart.getAnimator(), mLeftHandChart.getViewPortHandler()));

        mLeftHandChart.getDescription().setEnabled(false);
        mLeftHandChart.getLegend().setEnabled(false);

        BarData barData = new BarData(dataSet);

        mLeftHandChart.setData(barData);
        mLeftHandChart.invalidate();
    }
}
