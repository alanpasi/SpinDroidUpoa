package com.example.alanpasi.spindroidupoa;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.NumberFormat;
import java.util.ArrayList;

public class GraphGanhoCombustivel extends AppCompatActivity {

    private BarGraphSeries<DataPoint> mSeriesGanhoPorKm;
    private LineGraphSeries<DataPoint> mSeriesCombustivelPorKm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_ganho_combustivel);

        GraphView graphView = (GraphView) findViewById(R.id.graph_ganhocombustivel);

        Intent intent = this.getIntent();

        ArrayList<String> ganhokm_list = intent.getStringArrayListExtra("graphGanhoKm");
        ArrayList<String> combustivelkm_list = intent.getStringArrayListExtra("graphCombustivelKm");

        int dias = ganhokm_list.size();
        double y;
        double w;

        mSeriesGanhoPorKm = new BarGraphSeries<>();
        mSeriesCombustivelPorKm = new LineGraphSeries<>();
        for (int x = 0; x < dias; x++) {
            y = Double.parseDouble(ganhokm_list.get(x));
            w = Double.parseDouble(combustivelkm_list.get(x));
            mSeriesGanhoPorKm.appendData(new DataPoint(x + 1, y), true, dias);
            mSeriesCombustivelPorKm.appendData(new DataPoint(x + 1, w), true, dias);
        }

        graphView.getGridLabelRenderer().setHumanRounding(true);

        NumberFormat nf = NumberFormat.getInstance();
//        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumIntegerDigits(1);
        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));

        mSeriesGanhoPorKm.setDrawValuesOnTop(true);
        mSeriesGanhoPorKm.setValuesOnTopSize(20);
        mSeriesGanhoPorKm.setValuesOnTopColor(Color.BLACK);
        mSeriesGanhoPorKm.setSpacing(10);
        mSeriesGanhoPorKm.setColor(Color.BLUE);
        mSeriesGanhoPorKm.setTitle("Ganho (R$/km)");
        graphView.addSeries(mSeriesGanhoPorKm);

        mSeriesCombustivelPorKm.setDrawDataPoints(true);
        mSeriesCombustivelPorKm.setColor(Color.RED);
        mSeriesCombustivelPorKm.setDataPointsRadius(10);
        mSeriesCombustivelPorKm.setThickness(2);
        mSeriesCombustivelPorKm.setTitle("Combustível (R$/km)");
        graphView.addSeries(mSeriesCombustivelPorKm);

        graphView.setTitle("R$/km" + " - " + dias + " dias");
        graphView.setTitleTextSize(50);
        graphView.getGridLabelRenderer().setPadding(20);
        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMinX(10);
        graphView.getViewport().setXAxisBoundsManual(false);
        graphView.getViewport().setScrollable(false);
        graphView.getViewport().setScalableY(false);

    }
}
