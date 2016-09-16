package com.example.alanpasi.spindroidupoa;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Random;

import static android.view.View.Y;

public class Performance extends AppCompatActivity {

    private static final String TAG = Performance.class.getSimpleName();

    private BarGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        GraphView graph = (GraphView) findViewById(R.id.graph_performance);

        Intent intent = getIntent();

        ArrayList<String> performance_list = intent.getStringArrayListExtra("graphData");

        int tamanho = performance_list.size();
        double performanceSum = 0.0;
        DataPoint[] values = new DataPoint[tamanho];
        for (int i = 0; i < tamanho; i++) {
            double x = (double) i;
            double y = Double.parseDouble(performance_list.get(i));
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
            performanceSum += y;
        }

        double result = performanceSum/tamanho;
        DataPoint[] values2 = new DataPoint[2];
        DataPoint v2 = new DataPoint(0, result);
        values2[0] = v2;
        v2 = new DataPoint(tamanho-1, result);
        values2[1] = v2;

        mSeries2 = new LineGraphSeries<>(values2);
        graph.addSeries(mSeries2);
        mSeries2.setColor(Color.GREEN);
        mSeries2.setDrawDataPoints(true);
        mSeries2.setDataPointsRadius(10);
        mSeries2.setThickness(8);

        mSeries1 = new BarGraphSeries<>(values);
        graph.addSeries(mSeries1);



//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(5);
////        paint.setColor(Color.GREEN);
////        paint.setPathEffect(new DashPathEffect(new float[]{80, 50}, 0));
//        mSeries2.setCustomPaint(paint);

        graph.setTitle("Índice de Desempenho");
        graph.setTitleTextSize(50);
//        graph.getLegendRenderer().setVisible(true);
        mSeries1.setDrawValuesOnTop(true);
        mSeries1.setValuesOnTopSize(25);
        mSeries1.setValuesOnTopColor(Color.BLACK);
        mSeries1.setSpacing(5);

        graph.getGridLabelRenderer().setPadding(20);

        graph.getViewport().setMinX(0);
        graph.getViewport().setXAxisBoundsManual(true);
    }
}
