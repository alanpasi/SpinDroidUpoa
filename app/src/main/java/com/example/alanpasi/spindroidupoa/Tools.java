package com.example.alanpasi.spindroidupoa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by alanpasi on 02/10/16.
 */

public class Tools {

    public static boolean getResume(Context context, List<Ride> rideList) {

        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        DecimalFormat noDecimalFormatter = new DecimalFormat("#,###");
        DecimalFormat oneDecimalFormatter = new DecimalFormat("#.0");

        int distanceSum = 0;
        int quantitySum = 0;
        int timeHourSum = 0;
        int timeMinuteSum = 0;
        int days = rideList.size();
        double paymentSum = 0;
        double gasSum = 0;

        for (int i = 0; i < days; i++ ){
            distanceSum += Integer.parseInt(rideList.get(i).getDistance());
            quantitySum += Integer.parseInt(rideList.get(i).getQuantity());
            timeHourSum += Integer.parseInt(rideList.get(i).getTimeHour());
            timeMinuteSum += Integer.parseInt(rideList.get(i).getTimeMinute());
            paymentSum += Double.parseDouble(rideList.get(i).getPayment());
            gasSum += (Double.parseDouble(rideList.get(i).getDistance()) /
                    Double.parseDouble(rideList.get(i).getGasConsumption())) *
                    Double.parseDouble(rideList.get(i).getGasPrice());
        }

//      Dados - Resumo
        String distance = noDecimalFormatter.format(distanceSum) + " km";
        String quantity = String.valueOf(quantitySum) + " viagens";
        double totalTime = timeHourSum + (timeMinuteSum / 60.0);
        String timeHour = formatter.format(totalTime) + " h";
        String payment = "R$ " + formatter.format(paymentSum);
        String quantityDays = String.valueOf(days) + " dias";
//      Combustível - Resumo
        String gas = "R$ " + formatter.format(gasSum);
        String gasPercent = formatter.format((gasSum / paymentSum) * 100.0) + "%";
        String gasByDay = formatter.format(gasSum / days) + " R$/dia";
        String gasByDistance = formatter.format(gasSum / distanceSum) + " R$/km";
//      Médias - Resumo
        double quantityByDay = quantitySum / days;
        String quantityByDayAverage = oneDecimalFormatter.format(quantityByDay) + " viagens/dia";
        double reaisByDistance = paymentSum/ (double) distanceSum;
        String reaisByDistanceMid = formatter.format(reaisByDistance) + " R$/km";
        double reaisByDay = paymentSum/ days;
        String reaisByDaysMid = formatter.format(reaisByDay) + " R$/dia";
        double reaisByHour = paymentSum/totalTime;
        String reaisByHourMid = formatter.format(reaisByHour) + " R$/h";
        double distanceByDay = distanceSum / (double) days;
        String distanceByDayMid = formatter.format(distanceByDay) + " km/dia";
        double hoursByDay = totalTime / (double) days;
        String hourByDayAverage = oneDecimalFormatter.format(hoursByDay) + " h/dia";
//      Desempenho - Resumo
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
        bundle.putString("quantityByDay", quantityByDayAverage);
        bundle.putString("reaisByDay", reaisByDaysMid);
        bundle.putString("reaisByHour", reaisByHourMid);
        bundle.putString("distanceByDay", distanceByDayMid);
        bundle.putString("hoursByDay", hourByDayAverage);
        bundle.putString("indice", indiceMid);

        intent.putExtras(bundle);

        context.startActivity(intent);

        return true;
    }

    public static void showPerformanceGraph(Context context, List<Ride> rideList) {

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

    public static void showGanhoCombustivelGraph (Context context, List<Ride> rideList) {

        ArrayList<String> graphGanhoKm = new ArrayList<>();
        ArrayList<String> graphCombustivelKm = new ArrayList<>();

        Intent intent = new Intent(context, GraphGanhoCombustivel.class);

        double reaisByDistance;
        double gasByDistance;

        String data_a, data_b;

        for (int i = 0; i < rideList.size(); i++ ){
            reaisByDistance = Double.parseDouble(rideList.get(i).getPayment()) /
                    Double.parseDouble(rideList.get(i).getDistance());
//            gasByDistance = GasPrice / GasConsumption
            gasByDistance = Double.parseDouble(rideList.get(i).getGasPrice()) / Double.parseDouble(rideList.get(i).getGasConsumption());
            data_a = String.valueOf(reaisByDistance);
            data_b = String.valueOf(gasByDistance);
            graphGanhoKm.add(data_a);
            graphCombustivelKm.add(data_b);
        }

        intent.putStringArrayListExtra("graphGanhoKm", graphGanhoKm);
        intent.putStringArrayListExtra("graphCombustivelKm", graphCombustivelKm);

        context.startActivity(intent);
    }
}
