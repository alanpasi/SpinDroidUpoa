package com.example.alanpasi.spindroidupoa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

    DatabaseReference mRideRef;

    RecyclerView mRecyclerView;

    List<Ride> rideList = new ArrayList<>();

    RidesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Log.d(TAG, "mRecyclerView -> " + mRecyclerView);

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        if (id == R.id.action_report) {

            Log.d(TAG, "Chama Resumo");

            Intent intent = new Intent(this, Resume.class);
//            intent.putParcelableArrayListExtra("lista", rideList);
            startActivity(intent);

            return true;
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
                            day.getTimeMinute()));

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
