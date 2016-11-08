package com.example.alanpasi.spindroidupoa;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by alanpasi on 02/10/16.
 */

public class ToolsDb {

    public static boolean loadDb(final List<Ride> rideList,
                              final RecyclerView mRecyclerView,
                              final RidesAdapter adapter) {

        DatabaseReference mRideRef = FirebaseDatabase.getInstance().getReference().child("Ride");

        mRideRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rideList.clear();
                for(DataSnapshot daySnapshot: dataSnapshot.getChildren()){
                    Ride day = daySnapshot.getValue(Ride.class);
                    rideList.add(new Ride(day.getDate(),
                            day.getDistance(),
                            day.getGasConsumption(),
                            day.getGasPrice(),
                            day.getPayment(),
                            day.getQuantity(),
                            day.getTimeHour(),
                            day.getTimeMinute(),
                            day.getImageView() ,
                            day.getSaldo()));
                }
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return true;
    }
}
