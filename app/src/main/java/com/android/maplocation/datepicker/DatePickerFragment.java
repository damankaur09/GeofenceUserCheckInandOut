package com.android.maplocation.datepicker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.android.maplocation.R;

import java.util.Calendar;

/**
 * Created by Daman on 1/2/2018.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        /*
            Get Android DatePickerDialog without day
            - This code does not work
            THEME_DEVICE_DEFAULT_DARK
            THEME_DEVICE_DEFAULT_LIGHT
            - This code work fine
            THEME_HOLO_DARK
            THEME_HOLO_LIGHT
            THEME_TRADITIONAL
         */

        //DatePickerDialog dpd = new DatePickerDialog(getActivity(),AlertDialog.THEME_HOLO_DARK,this,year, month, day){
        //DatePickerDialog dpd = new DatePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT,this,year, month, day){
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),AlertDialog.THEME_TRADITIONAL,this,year, month, day){
            @Override
            protected void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                int day = getContext().getResources().getIdentifier("android:id/day", null, null);
                if(day != 0){
                    View dayPicker = findViewById(day);
                    if(dayPicker != null){
                        //Set Day view visibility Off/Gone
                        dayPicker.setVisibility(View.GONE);
                    }
                }
            }
        };
        return dpd;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
       /* //TextView tv = (TextView) getActivity().findViewById(R.id.tv_dateset);
        tv.setText("Date changed...");
        tv.setText(tv.getText() + "\nYear: " + year);
        tv.setText(tv.getText() + "\nMonth: " + month);

        String stringOfDate = month + "/" + year;
        tv.setText(tv.getText() + "\n\nFormatted date: " + stringOfDate);*/
    }
}
