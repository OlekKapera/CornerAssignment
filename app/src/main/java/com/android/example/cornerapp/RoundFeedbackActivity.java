package com.android.example.cornerapp;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.android.example.cornerapp.util.CSVFile;
import com.android.example.cornerapp.view.CustomLineChart;
import com.android.example.cornerapp.view.CustomLineDataSet;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;

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
        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        //parse CSV file into data object
        InputStream inputStream = getResources().openRawResource(R.raw.round1);
        mData = parseData(inputStream);
        mData.calcPunchesPercents(mPunchTarget, mPunchSeconds);

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

        for (int i = 0; i < csvParsed.size(); i++) {
            //ignore first four columns because this is table header
            if (i < 4)
                continue;

            try {
                Date date = formatter.parse("01-01-1970 " + csvParsed.get(i)[0]);
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
}
