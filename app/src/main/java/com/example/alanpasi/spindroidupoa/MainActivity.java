package com.example.alanpasi.spindroidupoa;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseReference mRideRef;
    private RecyclerView mRecyclerView;
    private List<Ride> rideList = new ArrayList<>();
    private RidesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Log.d(TAG, "mRecyclerView onCreate -> " + mRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RidesAdapter(this, rideList);
        mRecyclerView.setAdapter(adapter);

        loadDb();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRide();
            }
        });
    }

    private void addRide() {
        Intent intent = new Intent(this, AddRide.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_resume) {

            int distanceSum = 0;
            int quantitySum = 0;
            int timeHourSum = 0;
            int timeMinuteSum = 0;
            double paymentSum = 0.0;
            for (int i = 0; i < rideList.size(); i++ ){
                distanceSum += Integer.parseInt(rideList.get(i).getDistance());
                quantitySum += Integer.parseInt(rideList.get(i).getQuantity());
                timeHourSum += Integer.parseInt(rideList.get(i).getTimeHour());
                timeMinuteSum += Integer.parseInt(rideList.get(i).getTimeMinute());
                paymentSum += Double.parseDouble(rideList.get(i).getPayment());
            }

            String distance = String.valueOf(distanceSum) + " km";
            String quantity = String.valueOf(quantitySum) + " viagens";
            double totalTime = timeHourSum + (timeMinuteSum / 60.0);
            String timeHour = getString(R.string.reaisFormat, totalTime) + " h";
            String payment = "R$ " + getString(R.string.reaisFormat, paymentSum);
            String quantityDays = String.valueOf(rideList.size()) + " dias";
            double reaisByDistance = paymentSum/distanceSum;
            String reaisByDistanceMid = getString(R.string.reaisFormat, reaisByDistance) + " R$/km";
            double reaisByDay = paymentSum/rideList.size();
            String reaisByDaysMid = getString(R.string.reaisFormat, reaisByDay) + " R$/dia";
            double reaisByHour = paymentSum/totalTime;
            String reaisByHourMid = getString(R.string.reaisFormat, reaisByHour) + " R$/h";
            double indice = reaisByDistance * reaisByHour;
            String indiceMid = getString(R.string.reaisFormat, indice);

            Intent intent = new Intent(getApplicationContext(), Resume.class);

            Bundle bundle = new Bundle();
            bundle.putString("distance", distance);
            bundle.putString("quantity", quantity);
            bundle.putString("timeHour", timeHour);
            bundle.putString("payment", payment);
            bundle.putString("quantityDays", quantityDays);
            bundle.putString("reaisByDistance", reaisByDistanceMid);
            bundle.putString("reaisByDay", reaisByDaysMid);
            bundle.putString("reaisByHour", reaisByHourMid);
            bundle.putString("indice", indiceMid);

            intent.putExtras(bundle);

            startActivity(intent);

            return true;
        }

        if (id == R.id.action_performance) {

            ArrayList<String> graphData = new ArrayList<>();

            Intent intent = new Intent(getApplicationContext(), Performance.class);

            double reaisByDistance;
            double totalTime;
            double reaisByHour;
            double indice;
            String data;

            for (int i = 0; i < rideList.size(); i++ ){
                reaisByDistance = Double.parseDouble(rideList.get(i).getPayment()) / Double.parseDouble(rideList.get(i).getDistance());
                totalTime = Double.parseDouble(rideList.get(i).getTimeHour()) + (Double.parseDouble(rideList.get(i).getTimeMinute()) / 60.0);
                reaisByHour = Double.parseDouble(rideList.get(i).getPayment()) / totalTime;
                indice = reaisByDistance * reaisByHour;
                data = String.valueOf(indice);
                graphData.add(data);
            }

            intent.putStringArrayListExtra("graphData", graphData);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadDb(){

        mRideRef = FirebaseDatabase.getInstance().getReference().child("Ride");
        Log.d(TAG, "ladDB -> " + mRideRef);

        mRideRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rideList.clear();
                for(DataSnapshot daySnapshot: dataSnapshot.getChildren()){
                    Ride day = daySnapshot.getValue(Ride.class);
                    rideList.add(new Ride(day.getDate(),
                            day.getDistance(),
                            day.getPayment(),
                            day.getQuantity(),
                            day.getTimeHour(),
                            day.getTimeMinute(),
                            day.getImageView()));

                    Log.d(TAG, "dataSnapshot.getChildren -> " + dataSnapshot.getChildren());

                }

                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
