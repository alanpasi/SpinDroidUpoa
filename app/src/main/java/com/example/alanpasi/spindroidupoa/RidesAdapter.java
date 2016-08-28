package com.example.alanpasi.spindroidupoa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alanpasi on 28/08/16.
 */

public class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.ViewHolder>{

    Context context;
    List<Ride> rideList;

    public RidesAdapter(Context context, List<Ride> rideList) {
        this.context = context;
        this.rideList = rideList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.data.setText(rideList.get(position).getDate());
        holder.distance.setText(rideList.get(position).getDistance());
        holder.payment.setText(rideList.get(position).getPayment());
        holder.quantity.setText(rideList.get(position).getQuantity());
        holder.timeHour.setText(rideList.get(position).getTimeHour());
        holder.timeMinute.setText(rideList.get(position).getTimeMinute());

    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView data, distance, payment, quantity, timeHour, timeMinute;

        public ViewHolder(View itemView) {

            super(itemView);

            data = (TextView) itemView.findViewById(R.id.rvdate);
            distance = (TextView) itemView.findViewById(R.id.rvdistance);
            payment = (TextView) itemView.findViewById(R.id.rvpayment);
            quantity = (TextView) itemView.findViewById(R.id.rvquantity);
            timeHour = (TextView) itemView.findViewById(R.id.rvtimehour);
            timeMinute = (TextView) itemView.findViewById(R.id.rvtimeminute);
        }
    }
}
