package com.example.alanpasi.spindroidupoa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import static android.view.View.Y;

public class Performance extends AppCompatActivity {

    private static final String TAG = Performance.class.getSimpleName();

    LineGraphSeries<DataPoint> series;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        GraphView graphView = (GraphView) findViewById(R.id.action_performance);
        series = new LineGraphSeries<>();

        Intent intent = getIntent();

        ArrayList<String> performance_list = intent.getStringArrayListExtra("graphData");

        int tamanho = performance_list.size();

        Toast.makeText(this, String.valueOf(tamanho), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, performance_list.toString(), Toast.LENGTH_SHORT).show();

        int i = 0;
        for (String x : performance_list) {
            Log.d(TAG, "performance_list x -> " + x);

            series.appendData(new DataPoint(i, i), true, 16);
            i += 1;

        }
//        graphView.addSeries(series);

    }
}