package com.example.alanpasi.spindroidupoa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Resume extends AppCompatActivity {

    TextView distance;
    TextView quantity;
    TextView timeHour;
    TextView timeMinute;
    TextView payment;
    TextView quantityDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        distance = (TextView) findViewById(R.id.distance);
        quantity = (TextView) findViewById(R.id.quantity);
        timeHour = (TextView) findViewById(R.id.timeHour);
        timeMinute = (TextView) findViewById(R.id.timeMinute);
        payment = (TextView) findViewById(R.id.payment);
        quantityDays = (TextView) findViewById(R.id.quantityDays);

        Bundle bundle = getIntent().getExtras();
        String distanceSum = bundle.getString("distance");
        String quantitySum = bundle.getString("quantity");
        String timeHourSum = bundle.getString("timeHour");
        String timeMinuteSum = bundle.getString("timeMinute");
        String paymentSum = bundle.getString("payment");
        String quatityDaysSum = bundle.getString("quantityDays");

        distance.setText(distanceSum);
        quantity.setText(quantitySum);
        timeHour.setText(timeHourSum);
        timeMinute.setText(timeMinuteSum);
        payment.setText(paymentSum);
        quantityDays.setText(quatityDaysSum);

    }
}
