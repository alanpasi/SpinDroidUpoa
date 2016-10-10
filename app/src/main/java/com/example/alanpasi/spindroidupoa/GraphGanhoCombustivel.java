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
    private LineGraphSeries<DataPoint> mSeriesPercent;
    private LineGraphSeries<DataPoint> mSeriesPercentAverage;
    private double earnGasAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_ganho_combustivel);

        GraphView graphView = (GraphView) findViewById(R.id.graph_ganhocombustivel);

        Intent intent = this.getIntent();

        ArrayList<String> ganhokm_list = intent.getStringArrayListExtra("graphGanhoKm");
        ArrayList<String> combustivelkm_list = intent.getStringArrayListExtra("graphCombustivelKm");
        earnGasAverage = (double) getIntent().getExtras().get("graphEarnGasAverage");

        int days = ganhokm_list.size();
        double y;
        double w;
        double z;

        mSeriesPercent = new LineGraphSeries<>();
        mSeriesGanhoPorKm = new BarGraphSeries<>();
        mSeriesCombustivelPorKm = new LineGraphSeries<>();

        for (int x = 0; x < days; x++) {
            y = Double.parseDouble(ganhokm_list.get(x));
            w = Double.parseDouble(combustivelkm_list.get(x));
            z = (w / y) * 100.0;
            mSeriesGanhoPorKm.appendData(new DataPoint(x + 1, y), true, days);
            mSeriesCombustivelPorKm.appendData(new DataPoint(x + 1, w), true, days);
            mSeriesPercent.appendData(new DataPoint(x + 1, z), true, days);
        }

        graphView.getGridLabelRenderer().setHumanRounding(true);

        NumberFormat nf = NumberFormat.getInstance();
//        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumIntegerDigits(1);
        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));

        graphView.getSecondScale().addSeries(mSeriesPercent);
        graphView.getSecondScale().setMinY(0);
        graphView.getSecondScale().setMaxY(100);
        mSeriesPercent.setTitle("Combustível/Ganho (%)");
        mSeriesPercent.setColor(Color.RED);
        mSeriesPercent.setDrawDataPoints(true);
        mSeriesPercent.setDataPointsRadius(5);
        graphView.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.RED);
        mSeriesPercentAverage = new LineGraphSeries<>();
        mSeriesPercentAverage.appendData(new DataPoint(0, earnGasAverage),true,2);
        mSeriesPercentAverage.appendData(new DataPoint(days + 1, earnGasAverage),true,2);
        mSeriesPercentAverage.setTitle("Média Combustível/Ganho (%)");
        mSeriesPercentAverage.setColor(Color.RED);
        mSeriesPercentAverage.setDrawDataPoints(true);
        mSeriesPercentAverage.setDataPointsRadius(10);
        mSeriesPercentAverage.setThickness(2);
        graphView.getSecondScale().addSeries(mSeriesPercentAverage);

        mSeriesGanhoPorKm.setDrawValuesOnTop(true);
        mSeriesGanhoPorKm.setValuesOnTopSize(20);
        mSeriesGanhoPorKm.setValuesOnTopColor(Color.BLACK);
        mSeriesGanhoPorKm.setSpacing(10);
//        mSeriesGanhoPorKm.setColor(Color.BLUE);
        mSeriesGanhoPorKm.setTitle("Ganho (R$/km)");
        graphView.addSeries(mSeriesGanhoPorKm);

        mSeriesCombustivelPorKm.setDrawDataPoints(true);
        mSeriesCombustivelPorKm.setColor(Color.YELLOW);
        mSeriesCombustivelPorKm.setDataPointsRadius(8);
        mSeriesCombustivelPorKm.setThickness(2);
        mSeriesCombustivelPorKm.setTitle("Combustível (R$/km)");
        graphView.addSeries(mSeriesCombustivelPorKm);

        graphView.setTitle("R$/km" + " - " + days + " dias");
        graphView.setTitleTextSize(30);
        graphView.getGridLabelRenderer().setPadding(20);
        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
//        graphView.getViewport().setMinX(10);
//        graphView.getViewport().setMinX(15);
//        graphView.getViewport().setMinY(10);
//        graphView.getViewport().setMaxY(10);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScalableY(false);

    }
}
