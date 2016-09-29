package com.example.alanpasi.spindroidupoa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Resume extends AppCompatActivity {

    private TextView distance;
    private TextView quantity;
    private TextView timeHour;
    private TextView payment;
    private TextView quantityDays;
    private TextView reaisByDistance;
    private TextView reaisByDay;
    private TextView reaisByHour;
    private TextView indice;
    private TextView gas;
    private TextView gasPercent;
    private TextView gasByDistance;
    private TextView gasByDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        distance = (TextView) findViewById(R.id.distance);
        quantity = (TextView) findViewById(R.id.quantity);
        timeHour = (TextView) findViewById(R.id.timeHour);
        payment = (TextView) findViewById(R.id.payment);
        quantityDays = (TextView) findViewById(R.id.quantityDays);
        gas = (TextView) findViewById(R.id.gas);
        gasPercent = (TextView) findViewById(R.id.gaspercent);
        gasByDay = (TextView) findViewById(R.id.gasbyday);
        gasByDistance = (TextView) findViewById(R.id.gasbydistance);
        reaisByDistance = (TextView) findViewById(R.id.reaisByDistance);
        reaisByDay = (TextView) findViewById(R.id.reaisByDay);
        reaisByHour = (TextView) findViewById(R.id.reaisByHour);
        indice = (TextView) findViewById(R.id.indice);

        Bundle bundle = getIntent().getExtras();
        distance.setText(bundle.getString("distance"));
        quantity.setText(bundle.getString("quantity"));
        timeHour.setText(bundle.getString("timeHour"));
        payment.setText(bundle.getString("payment"));
        quantityDays.setText(bundle.getString("quantityDays"));
        gas.setText(bundle.getString("gas"));
        gasPercent.setText(bundle.getString("gasPercent"));
        gasByDay.setText(bundle.getString("gasByDay"));
        gasByDistance.setText(bundle.getString("gasByDistance"));
        reaisByDistance.setText(bundle.getString("reaisByDistance"));
        reaisByDay.setText(bundle.getString("reaisByDay"));
        reaisByHour.setText(bundle.getString("reaisByHour"));
        indice.setText(bundle.getString("indice"));
    }
}
