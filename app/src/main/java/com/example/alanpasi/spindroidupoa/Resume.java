package com.example.alanpasi.spindroidupoa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Resume extends AppCompatActivity {

    TextView mDayQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        mDayQuantity = (TextView) findViewById(R.id.dayQuantity);


    }
}
