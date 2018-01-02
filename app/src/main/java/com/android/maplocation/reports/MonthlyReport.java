package com.android.maplocation.reports;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.android.maplocation.R;
import com.android.maplocation.adapter.RecycleAdapter;
import com.android.maplocation.bean.ReportsBean;

import com.android.maplocation.datepicker.DatePickerFragment;

import com.android.maplocation.pojo.ReportData;
import com.android.maplocation.serviceparams.ReportParams;
import com.android.maplocation.utils.SharedPreferencesHandler;
import com.android.maplocation.webservices.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Arrays;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Daman on 12/28/2017.
 */

public class MonthlyReport extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    Button button;
    TextView tfDescription,tfDate;
    private String month,selectyear;

    private Button btSelectDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView=inflater.inflate(R.layout.reportlist, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recycler_view);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        button =view.findViewById(R.id.selectdate);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                createDialogWithoutDateField().show();
            }
        });
        fetchReportData();
    }

    private DatePickerDialog createDialogWithoutDateField() {
        DatePickerDialog dpd = new DatePickerDialog(getActivity(), myDateListener, 2018, 0, 30);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
        }
        return dpd;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
            selectyear=String.valueOf(year);
            month=String.valueOf(monthOfYear+1);
//            tfDescription.setText(selectyear);
//            tfDate.setText(month);
        }
    };


    //fetch data

    public void fetchReportData()
    {
       /*ReportData [] userReportsData={new ReportData("2017-12-29","Chandigarh","10.00.00","12.00.00","2 hours")
       ,new ReportData("2017-12-29","Chandigarh","13.00.00","15.00.00","2 hours")};

        ArrayList<ReportData> list=new ArrayList(Arrays.asList(userReportsData));*/


        ReportParams params=new ReportParams();
        params.setTask("getWorklog");
        params.setUserTimeZone("Asia/Kolkata");
        params.setUserId(SharedPreferencesHandler.getStringValues(getActivity(), getString(R.string.pref_user_id)));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String formatted = dateFormat.format(cal.getTime());
        params.setCurrentDate(formatted);

        Call<ReportsBean> call= RetrofitClient.getRetrofitClient().reports(params);
        call.enqueue(new Callback<ReportsBean>() {
            @Override
            public void onResponse(Call<ReportsBean> call, Response<ReportsBean> response) {
                Toast.makeText(getActivity(), response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                ReportsBean.DataBean dataBean=response.body().getData();

                if (response.isSuccessful()) {
                    onSuccess(response.body());
                } else {
                    onError(response.errorBody().toString());
                }


            }

            @Override
            public void onFailure(Call<ReportsBean> call, Throwable t) {
                onError(t.getMessage());
            }
        });

    }

    private void onSuccess(ReportsBean response) {
        switch (response.getStatus()) {
            case 200:
                ReportsBean.DataBean dataBean=response.getData();
                final ArrayList<ReportData> list=new ArrayList<>();
                String intime,outtime,location,shifthours,currentdate;

                String date1="00:00:00";
                List<ReportsBean.DataBean.UserlogBean> userlog=dataBean.getUserlog();
                for (int i=0;i<userlog.size();i++)
                {
                    currentdate=userlog.get(i).getCurrentdate();
                    intime=userlog.get(i).getUserIntime();
                    outtime=userlog.get(i).getUserOuttime();
                    location=userlog.get(i).getSiteAddress();
                    shifthours=userlog.get(i).getHours();
                    list.add(new ReportData(currentdate,location,intime,outtime,shifthours));

                }

                RecycleAdapter adapter=new RecycleAdapter(getActivity(),list);
                recyclerView.setAdapter(adapter);
            case 400:
                Toast.makeText(getActivity(), response.getStatusMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void onError(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();


    }



}
