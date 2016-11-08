package com.example.alanpasi.spindroidupoa;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class AddRide extends FragmentActivity {

    private static final String TAG = AddRide.class.getSimpleName();

    private TextView mRideDate;
    private EditText mDistance;
    private EditText mPayment;
    private EditText mQuantity;
    private EditText mTotalHours;
    private EditText mTotalMinutes;
    private EditText mConsumoCombustivel;
    private EditText mPrecoCombustivel;

    private DatabaseReference mRideRef;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mRideDate = (TextView) findViewById(R.id.rideDate);
        mDistance = (EditText) findViewById(R.id.distance);
        mPayment = (EditText) findViewById(R.id.payment);
        mQuantity = (EditText) findViewById(R.id.quantity);
        mTotalHours = (EditText) findViewById(R.id.totalHours);
        mTotalMinutes = (EditText) findViewById(R.id.totalMinutes);
        mConsumoCombustivel = (EditText) findViewById(R.id.consumoCombustivel);
        mPrecoCombustivel = (EditText) findViewById(R.id.precoCombustivel);

        PickerDialogs pickerDialogs = new PickerDialogs();
        pickerDialogs.show(getSupportFragmentManager(), "date_picker");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                mRideRef = FirebaseDatabase.getInstance().getReference().child("Ride");

                String dateInput = mRideDate.getText().toString();

                Log.d(TAG, "mRideDate -> " + dateInput);

                mRideRef.orderByChild("date").equalTo(dateInput)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChildren()) {

                                    Log.d(TAG, "onDataChange");

                                    Snackbar snackbar = Snackbar.make(view, "Data já existe!!!", Snackbar.LENGTH_LONG);
                                    snackbar.show();

                                }
                                else {

                                    saveRide(view);

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AddRide Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private void saveRide(View view) {

        DecimalFormat formatter = new DecimalFormat("#,###.##");

        String rideDateValue = mRideDate.getText().toString().trim();
        String distanceValue = mDistance.getText().toString();
        String paymentValue = mPayment.getText().toString();
        String quantityValue = mQuantity.getText().toString();
        String totalHoursValue = mTotalHours.getText().toString();
        String totalMinutesValue = mTotalMinutes.getText().toString();
        String consumoCombustivel = mConsumoCombustivel.getText().toString();
        String precoCombustivel = mPrecoCombustivel.getText().toString();

        double pagamento = Double.parseDouble(paymentValue);
        String paymentFormated = formatter.format(pagamento);
        paymentFormated = paymentFormated.replace(',', '.');

        double consumo = Double.parseDouble(consumoCombustivel);
        String consumoFormated = formatter.format(consumo);
        consumoFormated = consumoFormated.replace(',', '.');

        double preco = Double.parseDouble(precoCombustivel);
        String precoFormated = formatter.format(preco);
        precoFormated = precoFormated.replace(',', '.');

        Log.d(TAG, "paymentFormated -> " + paymentFormated);

        if(!TextUtils.isEmpty(rideDateValue)
                && !TextUtils.isEmpty(distanceValue)
                && !TextUtils.isEmpty(paymentValue)
                && !TextUtils.isEmpty(quantityValue)
                && !TextUtils.isEmpty(totalHoursValue)
                && !TextUtils.isEmpty(totalMinutesValue)
                && !TextUtils.isEmpty(consumoCombustivel)
                && !TextUtils.isEmpty(precoCombustivel)){

            DatabaseReference newPost = mRideRef.push();
            newPost.child("date").setValue(rideDateValue);
            newPost.child("distance").setValue(distanceValue);
            newPost.child("payment").setValue(paymentFormated);
            newPost.child("quantity").setValue(quantityValue);
            newPost.child("timeHour").setValue(totalHoursValue);
            newPost.child("timeMinute").setValue(totalMinutesValue);
            newPost.child("gasConsumption").setValue(consumoFormated);
            newPost.child("gasPrice").setValue(precoFormated);

            Snackbar snackbar = Snackbar.make(view, "Incluído", Snackbar.LENGTH_LONG);
            snackbar.show();

            Log.d(TAG, "rideDatevalue -> " + rideDateValue);

            finish();

        }
        else {
            Toast.makeText(this, "Favor preencher todos campos ou\n pressionar botão de retorno.", Toast.LENGTH_LONG).show();
        }
    }
}
