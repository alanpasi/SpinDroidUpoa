package com.example.alanpasi.spindroidupoa;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by alanpasi on 28/08/16.
 */

public class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.ViewHolder> {

    private static final String TAG = RidesAdapter.class.getSimpleName();


    Context context;
    List<Ride> rideList;

    public RidesAdapter(Context context, List<Ride> rideList) {
        this.context = context;
        this.rideList = rideList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder -> parent ->" + parent);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder -> position ->" + position);

        String result = null;
        try {
            result = getStringWeekDate(rideList.get(position).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.dataWeek.setText(result);
        holder.data.setText(rideList.get(position).getDate());
        holder.distance.setText(rideList.get(position).getDistance() + " km");
        holder.payment.setText("R$ " + rideList.get(position).getPayment());
        holder.quantity.setText(rideList.get(position).getQuantity() + " viagens");
        holder.timeHour.setText(rideList.get(position).getTimeHour() + " horas");
        holder.timeMinute.setText(rideList.get(position).getTimeMinute() + "minutos");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onBindViewHolder -> onClick -> Position ->" + position);

            }
        });

        final String finalResult = result;
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Log.d(TAG, "holder.itemView.setOnLongClickListener -> onLongClick -> Data ->" + rideList.get(position).getDate());

                String dtStart = rideList.get(position).getDate();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = format.parse(dtStart);
                    Log.d(TAG, "Data ->" + date);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {
                    String result = getStringWeekDate(rideList.get(position).getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Apagar Cartão de " + finalResult + " dia " + rideList.get(position).getDate() + "?");
                builder.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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
        return rideList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dataWeek, data, distance, payment, quantity, timeHour, timeMinute;

        public ViewHolder(View itemView) {
            super(itemView);

            Log.d(TAG, "ViewHolder -> itemView ->" + itemView);

            dataWeek = (TextView) itemView.findViewById(R.id.rvdatelabel);
            data = (TextView) itemView.findViewById(R.id.rvdate);
            distance = (TextView) itemView.findViewById(R.id.rvdistance);
            payment = (TextView) itemView.findViewById(R.id.rvpayment);
            quantity = (TextView) itemView.findViewById(R.id.rvquantity);
            timeHour = (TextView) itemView.findViewById(R.id.rvtimehour);
            timeMinute = (TextView) itemView.findViewById(R.id.rvtimeminute);
        }
    }

    private String getStringWeekDate(String dtStart) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = format.parse(dtStart);
        Log.d(TAG, "Data ->" + date);

        SimpleDateFormat dateformatWeek = new SimpleDateFormat("EEEE");
        return dateformatWeek.format(date);
    }
}
