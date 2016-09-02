package com.example.alanpasi.spindroidupoa;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public DatePickerFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year;
        int month;
        int day;

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        TextView mRideDate = (TextView) getActivity().findViewById(R.id.rideDate);

        int monthPlusOne = datePicker.getMonth() + 1;

        NumberFormat numberFormat = new DecimalFormat("##00");

        String strDay = numberFormat.format(i2);
        String strMonth = numberFormat.format(monthPlusOne);

        mRideDate.setText(strDay + "/" + strMonth + "/" + datePicker.getYear());
//        Toast.makeText(getContext(), "Data selecionada...", Toast.LENGTH_SHORT).show();

    }
}
