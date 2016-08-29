package com.example.alanpasi.spindroidupoa;

/**
 * Created by alanpasi on 28/08/16.
 */

public class Ride {

    private String date;
    private String distance;
    private String payment;
    private String quantity;
    private String timeHour;
    private String timeMinute;

    public Ride() {
    }

    public Ride(String date, String distance, String payment, String quantity, String timeHour, String timeMinute) {
        this.date = date;
        this.distance = distance;
        this.payment = payment;
        this.quantity = quantity;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(String timeHour) {
        this.timeHour = timeHour;
    }

    public String getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(String timeMinute) {
        this.timeMinute = timeMinute;
    }
}
