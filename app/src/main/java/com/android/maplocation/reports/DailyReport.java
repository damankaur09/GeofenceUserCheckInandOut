package com.android.maplocation.reports;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import com.android.maplocation.R;

/**
 * Created by Daman on 12/28/2017.
 */

public class DailyReport extends Fragment {

    private TableLayout tableLayout;
    TableRow tr;
    TextView tvInTime,tvOutTime;
    String inTime[]={"10.00.00","16.00.00"};
    String outTime[]={"13.00.00","21.00.00"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.dailyreport, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tableLayout=view.findViewById(R.id.tb_dailyreport);

    }


    private void addHeaders()
    {
        /** Create a TableRow dynamically **/
        tr = new TableRow(getActivity());
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        /** Creating a TextView to add to the row **/
        tvInTime = new TextView(getActivity());
        tvInTime.setText("In Time");
        tvInTime.setTextColor(Color.GRAY);
        tvInTime.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tvInTime.setPadding(5, 5, 5, 0);
        tr.addView(tvInTime);  // Adding textView to tablerow.

        /** Creating another textview **/
        tvOutTime = new TextView(getActivity());
        tvOutTime.setText("Out Time");
        tvOutTime.setTextColor(Color.GRAY);
        tvOutTime.setPadding(5, 5, 5, 0);
        tvOutTime.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(tvOutTime); // Adding textView to tablerow.


        // Add the TableRow to the TableLayout
        tableLayout.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

    }

}


