package com.example.alanpasi.spindroidupoa;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

/**
 * Created by alanpasi on 04/09/16.
 */

public class PickerDialogs extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);


        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

//        Toast.makeText(getContext(), "Selected date " + dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();

        TextView selectedDate = (TextView) getActivity().findViewById(R.id.rideDate);
        int monthPlusOne = view.getMonth() + 1;
        NumberFormat numberFormat = new DecimalFormat("##00");
        String strDay = numberFormat.format(dayOfMonth);
        String strMonth = numberFormat.format(monthPlusOne);

        selectedDate.setText(strDay + "/" + strMonth + "/" + year);
    }
}
