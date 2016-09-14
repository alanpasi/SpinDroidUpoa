package com.example.alanpasi.spindroidupoa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Resume extends AppCompatActivity {

    TextView distance;
    TextView quantity;
    TextView timeHour;
    TextView payment;
    TextView quantityDays;
    TextView reaisByDistance;
    TextView reaisByDay;
    TextView reaisByHour;
    TextView indice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        distance = (TextView) findViewById(R.id.distance);
        quantity = (TextView) findViewById(R.id.quantity);
        timeHour = (TextView) findViewById(R.id.timeHour);
        payment = (TextView) findViewById(R.id.payment);
        quantityDays = (TextView) findViewById(R.id.quantityDays);
        reaisByDistance = (TextView) findViewById(R.id.reaisByDistance);
        reaisByDay = (TextView) findViewById(R.id.reaisByDay);
        reaisByHour = (TextView) findViewById(R.id.reaisByHour);
        indice = (TextView) findViewById(R.id.indice);

        Bundle bundle = getIntent().getExtras();
        String distanceSum = bundle.getString("distance");
        String quantitySum = bundle.getString("quantity");
        String timeHourSum = bundle.getString("timeHour");
        String paymentSum = bundle.getString("payment");
        String quatityDaysSum = bundle.getString("quantityDays");
        String reaisByDistanceMid = bundle.getString("reaisByDistance");
        String reaisByDayMid = bundle.getString("reaisByDay");
        String reaisByHourMid = bundle.getString("reaisByHour");
        String indiceMid = bundle.getString("indice");

        distance.setText(distanceSum);
        quantity.setText(quantitySum);
        timeHour.setText(timeHourSum);
        payment.setText(paymentSum);
        quantityDays.setText(quatityDaysSum);
        reaisByDistance.setText(reaisByDistanceMid);
        reaisByDay.setText(reaisByDayMid);
        reaisByHour.setText(reaisByHourMid);
        indice.setText(indiceMid);


        Toast.makeText(this, reaisByDistanceMid, Toast.LENGTH_SHORT).show();

    }
}
