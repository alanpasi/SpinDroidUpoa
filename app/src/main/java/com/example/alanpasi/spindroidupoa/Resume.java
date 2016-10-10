package com.example.alanpasi.spindroidupoa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Resume extends AppCompatActivity {

    private TextView dateNow;
    private TextView distance;
    private TextView quantity;
    private TextView timeHour;
    private TextView payment;
    private TextView quantityDays;
    private TextView quantityByDay;
    private TextView reaisByDistance;
    private TextView reaisByDay;
    private TextView reaisByHour;
    private TextView distannceByDay;
    private TextView hoursByDay;
    private TextView indice;
    private TextView gas;
    private TextView gasPercent;
    private TextView gasByDistance;
    private TextView gasByDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd/MMMM/yyy HH:mm", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());

        dateNow = (TextView) findViewById(R.id.dateNow);
        distance = (TextView) findViewById(R.id.distance);
        quantity = (TextView) findViewById(R.id.quantity);
        timeHour = (TextView) findViewById(R.id.timeHour);
        payment = (TextView) findViewById(R.id.payment);
        quantityDays = (TextView) findViewById(R.id.quantityDays);

        quantityByDay = (TextView) findViewById(R.id.quantityByDay);
        reaisByDay = (TextView) findViewById(R.id.reaisByDay);
        distannceByDay = (TextView) findViewById(R.id.distanceByDay);
        hoursByDay = (TextView) findViewById(R.id.hoursByDay);
        reaisByDistance = (TextView) findViewById(R.id.reaisByDistance);
        reaisByHour = (TextView) findViewById(R.id.reaisByHour);

        gas = (TextView) findViewById(R.id.gas);
        gasPercent = (TextView) findViewById(R.id.gaspercent);
        gasByDay = (TextView) findViewById(R.id.gasbyday);
        gasByDistance = (TextView) findViewById(R.id.gasbydistance);

        indice = (TextView) findViewById(R.id.indice);

        dateNow.setText(formattedDate);
        Bundle bundle = getIntent().getExtras();
        distance.setText(bundle.getString("distance"));
        quantity.setText(bundle.getString("quantity"));
        timeHour.setText(bundle.getString("timeHour"));
        payment.setText(bundle.getString("payment"));
        quantityDays.setText(bundle.getString("quantityDays"));

        quantityByDay.setText(bundle.getString("quantityByDay"));
        reaisByDistance.setText(bundle.getString("reaisByDistance"));
        reaisByDay.setText(bundle.getString("reaisByDay"));
        reaisByHour.setText(bundle.getString("reaisByHour"));
        distannceByDay.setText(bundle.getString("distanceByDay"));
        hoursByDay.setText(bundle.getString("hoursByDay"));

        gas.setText(bundle.getString("gas"));
        gasPercent.setText(bundle.getString("gasPercent"));
        gasByDay.setText(bundle.getString("gasByDay"));
        gasByDistance.setText(bundle.getString("gasByDistance"));

        indice.setText(bundle.getString("indice"));
    }
}
