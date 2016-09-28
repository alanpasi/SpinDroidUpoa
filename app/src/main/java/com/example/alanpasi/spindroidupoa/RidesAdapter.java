package com.example.alanpasi.spindroidupoa;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.alanpasi.spindroidupoa.R.id.progressBar;
import static com.example.alanpasi.spindroidupoa.R.id.rvcombustivel;
import static com.example.alanpasi.spindroidupoa.R.id.rvindice;

/**
 * Created by alanpasi on 28/08/16.
 */

class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.ViewHolder> {

    private static final String TAG = RidesAdapter.class.getSimpleName();

    private Context context;
    private List<Ride> rideList;

    private DatabaseReference mRideRef;

    RidesAdapter(Context context, List<Ride> rideList) {
        this.context = context;
        this.rideList = rideList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder -> parent ->" + parent);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final int itemPosition = holder.getAdapterPosition();

        Log.d(TAG, "onBindViewHolder -> position ->" + position);

        String result = null;
        String weekOfYear = null;
        try {
            result = getStringWeekDate(rideList.get(position).getDate());
            weekOfYear = getStringWeekOfYear(rideList.get(position).getDate());
            Log.d(TAG, "Week of year ->" + weekOfYear + " = " + result);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.dateofWeek.setText(result);
        holder.date.setText(rideList.get(position).getDate());
        holder.weekofyear.setText(weekOfYear);

        double reaisByDistance = Double.parseDouble(rideList.get(position).getPayment())/Double.parseDouble(rideList.get(position).getDistance());
        Log.d(TAG, "reaisByDistance ->" + reaisByDistance);
        String reaisByDistanceMid = context.getString(R.string.reaisFormat, reaisByDistance) + " R$/km";
        holder.reaisbydistance.setText(reaisByDistanceMid);

        double decimalHours = Double.parseDouble(rideList.get(position).getTimeHour()) + (Double.parseDouble(rideList.get(position).getTimeMinute()) / 60d);
        double reaisByHour = Double.parseDouble(rideList.get(position).getPayment()) / decimalHours;
        String reaisByHourMid = context.getString(R.string.reaisFormat, reaisByHour) + " R$/h";
        holder.reaisbyhour.setText(reaisByHourMid);

        holder.distance.setText(rideList.get(position).getDistance() + " km");

        double paymentcalc = Double.parseDouble(rideList.get(position).getPayment());
        String paymentString = "R$ " + context.getString(R.string.reaisFormat, paymentcalc);
        holder.payment.setText(paymentString);

        holder.quantity.setText(rideList.get(position).getQuantity() + " viagens");
        holder.timeHour.setText(rideList.get(position).getTimeHour() + "h");
        holder.timeMinute.setText(rideList.get(position).getTimeMinute() + "min");

        double combustivelcalc = (Double.parseDouble(rideList.get(position).getDistance()) /
                Double.parseDouble(rideList.get(position).getGasConsumption())) *
                        Double.parseDouble(rideList.get(position).getGasPrice());
        String combustivelString = "Gasolina R$ " + context.getString(R.string.reaisFormat, combustivelcalc) + " (";
        holder.combustivel.setText(combustivelString);

        double percentualcalc = (combustivelcalc / Double.parseDouble(rideList.get(position).getPayment())) * 100.0;
        String percentualString = context.getString(R.string.reaisFormat, percentualcalc) + "%)";
        holder.percentual.setText(percentualString);

        double indice = reaisByDistance * reaisByHour;
        String indiceFinal = context.getString(R.string.reaisFormat, indice);
        holder.indice.setText(indiceFinal);

        double distanceSum = 0;
        double timeHourSum = 0;
        double timeMinuteSum = 0;
        double paymentSum = 0.0;
        for (int i = 0; i < rideList.size(); i++ ){
            distanceSum += Double.parseDouble(rideList.get(i).getDistance());
            timeHourSum += Double.parseDouble(rideList.get(i).getTimeHour());
            timeMinuteSum += Double.parseDouble(rideList.get(i).getTimeMinute());
            paymentSum += Double.parseDouble(rideList.get(i).getPayment());
        }
        double totalTime = timeHourSum + (timeMinuteSum / 60.0d);
        double reaisByDistanceGlobal = paymentSum/distanceSum;
        double reaisByHourGlobal = paymentSum/totalTime;
        double indiceAverage = reaisByDistanceGlobal * reaisByHourGlobal;

        Log.d(TAG, "onBindViewHolder -> indiceAverage ->" + indiceAverage);

        if (indice >= indiceAverage) {
            holder.indiceimageview.setImageResource(R.drawable.ic_action_good);
            holder.indiceimageview.setColorFilter(Color.GREEN);
        } else {
            holder.indiceimageview.setImageResource(R.drawable.ic_action_bad);
            holder.indiceimageview.setColorFilter(Color.RED);
        }

        final Ride infoData = rideList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onBindViewHolder -> onClick -> Position ->" + itemPosition);

            }
        });

        final String finalResult = result;

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {

                Log.d(TAG, "holder.itemView.setOnLongClickListener -> onLongClick -> Data ->" + rideList.get(itemPosition).getDate());

                String dtStart = rideList.get(itemPosition).getDate();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.UK );
                try {
                    Date date = format.parse(dtStart);
                    Log.d(TAG, "Data ->" + date);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Apagar Cartão de " + finalResult + " dia " + rideList.get(itemPosition).getDate() + "?");
                builder.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        removeCardItem(view, infoData);

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        if (rideList.size() != 0) {
            return rideList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateofWeek, weekofyear, reaisbydistance, reaisbyhour ,date,
                distance, payment, quantity, timeHour, timeMinute, indice, combustivel, percentual;

        ImageView indiceimageview;

        public ViewHolder(View itemView) {
            super(itemView);

            Log.d(TAG, "ViewHolder -> itemView ->" + itemView);

            dateofWeek = (TextView) itemView.findViewById(R.id.rvdatelabel);
            date = (TextView) itemView.findViewById(R.id.rvdate);
            weekofyear = (TextView) itemView.findViewById(R.id.rvweekofyear);
            reaisbydistance = (TextView) itemView.findViewById(R.id.rvreaisbydistance);
            reaisbyhour = (TextView) itemView.findViewById(R.id.rvreaisbyhour);
            distance = (TextView) itemView.findViewById(R.id.rvdistance);
            payment = (TextView) itemView.findViewById(R.id.rvpayment);
            quantity = (TextView) itemView.findViewById(R.id.rvquantity);
            timeHour = (TextView) itemView.findViewById(R.id.rvtimehour);
            timeMinute = (TextView) itemView.findViewById(R.id.rvtimeminute);
            indice = (TextView) itemView.findViewById(rvindice);
            combustivel = (TextView) itemView.findViewById(rvcombustivel);
            percentual = (TextView) itemView.findViewById(R.id.rvcombustivelpercentual);
            indiceimageview = (ImageView) itemView.findViewById(R.id.rvimageview);
        }
    }

    private String getStringWeekDate(String dtStart) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Date date = format.parse(dtStart);
//        Log.d(TAG, "Data ->" + date);

        SimpleDateFormat dateFormatWeek = new SimpleDateFormat("EEEE");
        return dateFormatWeek.format(date);
    }

    private String getStringWeekOfYear(String dtStart) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy");
        Date date = format.parse(dtStart);
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        format.setCalendar(calendar);

//        Date date = format.parse(dtStart);

        SimpleDateFormat dateFormatWeekOfYear = new SimpleDateFormat("w");
        dateFormatWeekOfYear.setCalendar(calendar);



        return dateFormatWeekOfYear.format(date);
    }

    private void removeCardItem(final View view, Ride infoData) {

        final int CurrPosition = rideList.indexOf(infoData);

        mRideRef = FirebaseDatabase.getInstance().getReference().child("Ride");

        mRideRef.orderByChild("date").equalTo(rideList.get(CurrPosition).getDate())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                            firstChild.getRef().removeValue();

                            rideList.remove(CurrPosition);
                            notifyItemRemoved(CurrPosition);

                            Snackbar snackbar = Snackbar.make(view, "Excluído", Snackbar.LENGTH_LONG);
                            snackbar.show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
