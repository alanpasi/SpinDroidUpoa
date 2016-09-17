package com.example.alanpasi.spindroidupoa;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Performance extends AppCompatActivity {

    private BarGraphSeries<DataPoint> mSeriesPerformance;
    private LineGraphSeries<DataPoint> mSeriesPerformanceAverage;
    private DataPoint dataPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        GraphView graph = (GraphView) findViewById(R.id.graph_performance);

        Intent intent = getIntent();
        ArrayList<String> performance_list = intent.getStringArrayListExtra("graphData");

        int dias = performance_list.size();
        double performanceSum = 0.0;
        DataPoint[] performance = new DataPoint[dias];
        for (int x = 0; x < dias; x++) {
            double y = Double.parseDouble(performance_list.get(x));
            dataPoint = new DataPoint(x+1, y);
            performance[x] = dataPoint;
            performanceSum += y;
        }

        double result = performanceSum/dias;
        DataPoint[] performanceAverage = new DataPoint[2];
        DataPoint dataPoint = new DataPoint(0, result);
        performanceAverage[0] = dataPoint;
        dataPoint = new DataPoint(dias + 1, result);
        performanceAverage[1] = dataPoint;

        mSeriesPerformanceAverage = new LineGraphSeries<>(performanceAverage);
        mSeriesPerformanceAverage.setColor(Color.RED);
        mSeriesPerformanceAverage.setDrawDataPoints(true);
        mSeriesPerformanceAverage.setDataPointsRadius(10);
        mSeriesPerformanceAverage.setThickness(4);
        graph.addSeries(mSeriesPerformanceAverage);

        mSeriesPerformance = new BarGraphSeries<>(performance);
        mSeriesPerformance.setDrawValuesOnTop(true);
        mSeriesPerformance.setValuesOnTopSize(25);
        mSeriesPerformance.setValuesOnTopColor(Color.BLACK);
        mSeriesPerformance.setSpacing(5);
        graph.addSeries(mSeriesPerformance);

        graph.setTitle("Índice de Desempenho" + " - " + dias + " dias");
        graph.setTitleTextSize(50);
        graph.getGridLabelRenderer().setPadding(20);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(dias + 1);
        graph.getViewport().setXAxisBoundsManual(true);
    }
}
