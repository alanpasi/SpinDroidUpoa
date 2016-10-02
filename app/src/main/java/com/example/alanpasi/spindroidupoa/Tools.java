package com.example.alanpasi.spindroidupoa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by alanpasi on 02/10/16.
 */

public class Tools {

    public static boolean getResume(Context context, List<Ride> rideList) {

        DecimalFormat formatter = new DecimalFormat("#,###.##");
        DecimalFormat noDecimalFormatter = new DecimalFormat("#,###");

        int distanceSum = 0;
        int quantitySum = 0;
        int timeHourSum = 0;
        int timeMinuteSum = 0;
        double paymentSum = 0;
        double gasSum = 0;
        for (int i = 0; i < rideList.size(); i++ ){
            distanceSum += Integer.parseInt(rideList.get(i).getDistance());
            quantitySum += Integer.parseInt(rideList.get(i).getQuantity());
            timeHourSum += Integer.parseInt(rideList.get(i).getTimeHour());
            timeMinuteSum += Integer.parseInt(rideList.get(i).getTimeMinute());
            paymentSum += Double.parseDouble(rideList.get(i).getPayment());
            gasSum += (Double.parseDouble(rideList.get(i).getDistance()) /
                    Double.parseDouble(rideList.get(i).getGasConsumption())) *
                    Double.parseDouble(rideList.get(i).getGasPrice());
        }

        String distance = noDecimalFormatter.format(distanceSum) + " km";
        String quantity = String.valueOf(quantitySum) + " viagens";
        double totalTime = timeHourSum + (timeMinuteSum / 60.0);
        String timeHour = formatter.format(totalTime) + " h";
        String payment = "R$ " + formatter.format(paymentSum);
        String quantityDays = String.valueOf(rideList.size()) + " dias";

        String gas = "R$ " + formatter.format(gasSum);
        String gasPercent = formatter.format((gasSum / paymentSum) * 100.0) + "%";
        String gasByDay = formatter.format(gasSum / rideList.size()) + " R$/dia";
        String gasByDistance = formatter.format(gasSum / distanceSum) + " R$/km";

        double reaisByDistance = paymentSum/distanceSum;
        String reaisByDistanceMid = formatter.format(reaisByDistance) + " R$/km";
        double reaisByDay = paymentSum/rideList.size();
        String reaisByDaysMid = formatter.format(reaisByDay) + " R$/dia";
        double reaisByHour = paymentSum/totalTime;
        String reaisByHourMid = formatter.format(reaisByHour) + " R$/h";

        double indice = reaisByDistance * reaisByHour;
        String indiceMid = formatter.format(indice);

        Intent intent = new Intent(context, Resume.class);

        Bundle bundle = new Bundle();
        bundle.putString("distance", distance);
        bundle.putString("quantity", quantity);
        bundle.putString("timeHour", timeHour);
        bundle.putString("payment", payment);
        bundle.putString("quantityDays", quantityDays);
        bundle.putString("gas", gas);
        bundle.putString("gasPercent", gasPercent);
        bundle.putString("gasByDay", gasByDay);
        bundle.putString("gasByDistance", gasByDistance);
        bundle.putString("reaisByDistance", reaisByDistanceMid);
        bundle.putString("reaisByDay", reaisByDaysMid);
        bundle.putString("reaisByHour", reaisByHourMid);
        bundle.putString("indice", indiceMid);

        intent.putExtras(bundle);

        context.startActivity(intent);

        return true;
    }

    public static void showPerformanceGraph(Context context, List<Ride> rideList) {

        Toast.makeText(context, "showPerformanceGraph", Toast.LENGTH_SHORT).show();

        ArrayList<String> graphData = new ArrayList<>();

        Intent intent = new Intent(context, Performance.class);

        double reaisByDistance;
        double reaisByDistanceSum = 0.0;
        double totalTime;
        double reaisByHour;
        double reaisByHourSum = 0.0;
        double indice;
        double indiceAverage;

        String data;

        for (int i = 0; i < rideList.size(); i++ ){
            reaisByDistance = Double.parseDouble(rideList.get(i).getPayment()) / Double.parseDouble(rideList.get(i).getDistance());
            totalTime = Double.parseDouble(rideList.get(i).getTimeHour()) + (Double.parseDouble(rideList.get(i).getTimeMinute()) / 60.0);
            reaisByHour = Double.parseDouble(rideList.get(i).getPayment()) / totalTime;
            reaisByDistanceSum += reaisByDistance;
            reaisByHourSum += reaisByHour;
            indice = reaisByDistance * reaisByHour;
            data = String.valueOf(indice);
            graphData.add(data);
        }

        indiceAverage = (reaisByDistanceSum / (double) rideList.size()) * (reaisByHourSum / (double) rideList.size());

        intent.putStringArrayListExtra("graphData", graphData);
        intent.putExtra("graphDataAverage", indiceAverage);

        context.startActivity(intent);
    }
}
