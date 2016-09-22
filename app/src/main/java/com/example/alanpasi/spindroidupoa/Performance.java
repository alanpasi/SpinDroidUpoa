package com.example.alanpasi.spindroidupoa;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Performance extends AppCompatActivity {

    private static final String TAG = Performance.class.getSimpleName();

    private BarGraphSeries<DataPoint> mSeriesPerformance;
    private LineGraphSeries<DataPoint> mSeriesPerformanceAverage;
    double finalIndiceAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        GraphView graph = (GraphView) findViewById(R.id.graph_performance);

        Intent intent = this.getIntent();
        ArrayList<String> performance_list = intent.getStringArrayListExtra("graphData");

        finalIndiceAverage = (double) getIntent().getExtras().get("graphDataAverage");

        Log.d(TAG, "finalIndiceAverage ->" + finalIndiceAverage);

        int dias = performance_list.size();
        double y;

        mSeriesPerformance = new BarGraphSeries<>();
        for (int x = 0; x < dias; x++) {
            y = Double.parseDouble(performance_list.get(x));
            mSeriesPerformance.appendData(new DataPoint(x +1, y), true, dias);
            Log.d(TAG, "Performance Sum ->" + y);
        }

        mSeriesPerformanceAverage = new LineGraphSeries<>();
        mSeriesPerformanceAverage.appendData(new DataPoint(0, finalIndiceAverage),true,2);
        mSeriesPerformanceAverage.appendData(new DataPoint(dias + 1, finalIndiceAverage),true,2);
        mSeriesPerformanceAverage.setColor(Color.RED);
        mSeriesPerformanceAverage.setDrawDataPoints(true);
        mSeriesPerformanceAverage.setDataPointsRadius(10);
        mSeriesPerformanceAverage.setThickness(4);
        graph.addSeries(mSeriesPerformanceAverage);

        mSeriesPerformance.setDrawValuesOnTop(true);
        mSeriesPerformance.setValuesOnTopSize(25);
        mSeriesPerformance.setValuesOnTopColor(Color.BLACK);
        mSeriesPerformance.setSpacing(5);
        graph.addSeries(mSeriesPerformance);

        graph.setTitle("Índice de Desempenho" + " - " + dias + " dias");
        graph.setTitleTextSize(50);
        graph.getGridLabelRenderer().setPadding(20);
        graph.getViewport().setMinX(0);
//        graph.getViewport().setMaxX(10);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalableY(false);
    }
}
