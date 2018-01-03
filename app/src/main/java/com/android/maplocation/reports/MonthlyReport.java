package com.android.maplocation.reports;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.ImageView;

import android.widget.LinearLayout;
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

public class MonthlyReport extends Fragment implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout linearLayout;
    private ArrayList<ReportData> list = new ArrayList<>();
    TextView totalhours;

    private RecycleAdapter adapter;
    private AppCompatSpinner spinnerMonth, spinnerYear;
    private String month = "", selectyear = "";
    private ArrayAdapter<String> monthAdapter, yearAdapter;

    private Button btSelectDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.reportlist, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout=view.findViewById(R.id.yearmonth);
        linearLayout.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecycleAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

        totalhours = view.findViewById(R.id.textView5);
        spinnerMonth = view.findViewById(R.id.spinner_month);
        spinnerYear = view.findViewById(R.id.spinner_year);

        spinnerMonth.setOnItemSelectedListener(this);
        spinnerYear.setOnItemSelectedListener(this);

        if (getActivity() != null) {
            monthAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getActivity().getResources().getStringArray(R.array.month));
            yearAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getActivity().getResources().getStringArray(R.array.year));

            spinnerMonth.setAdapter(monthAdapter);
            spinnerYear.setAdapter(yearAdapter);
        }

        /*button = view.findViewById(R.id.selectdate);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                createDialogWithoutDateField().show();

            }
        });*/

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
        } catch (Exception ex) {
        }
        return dpd;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
            selectyear = String.valueOf(year);
            month = String.valueOf(monthOfYear + 1);

        }
    };


    //fetch data

    public void fetchReportData() {
       /*ReportData [] userReportsData={new ReportData("2017-12-29","Chandigarh","10.00.00","12.00.00","2 hours")
       ,new ReportData("2017-12-29","Chandigarh","13.00.00","15.00.00","2 hours")};

        ArrayList<ReportData> list=new ArrayList(Arrays.asList(userReportsData));*/


        ReportParams params = new ReportParams();
        params.setTask("getWorklog");
        params.setUserTimeZone("Asia/Kolkata");
        params.setUserId(SharedPreferencesHandler.getStringValues(getActivity(), getString(R.string.pref_user_id)));
        params.setMonth(month);
        params.setYear(selectyear);
        params.setMonthlyLog("1");

        Call<ReportsBean> call = RetrofitClient.getRetrofitClient().reports(params);
        call.enqueue(new Callback<ReportsBean>() {
            @Override
            public void onResponse(Call<ReportsBean> call, Response<ReportsBean> response) {

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
                ReportsBean.DataBean dataBean = response.getData();
                String intime, outtime, location, shifthours, currentdate;


                List<ReportsBean.DataBean.UserlogBean> userlog = dataBean.getUserlog();
                if (!list.isEmpty())
                    list.clear();


                for (int i = 0; i < userlog.size(); i++) {
                    currentdate = userlog.get(i).getUserIndate();
                    intime = userlog.get(i).getUserIntime();
                    outtime = userlog.get(i).getUserOuttime();
                    location = userlog.get(i).getSiteAddress();
                    shifthours = userlog.get(i).getHours();
                    list.add(new ReportData(currentdate, location, intime, outtime, shifthours));

                }
                totalhours.setText("Total hrs:"+dataBean.getTotaldayhours());
                if (adapter != null)
                    adapter.notifyDataSetChanged();

                break;
            case 400:
                if (!list.isEmpty())
                    list.clear();
                totalhours.setText("");
                if (adapter != null)
                    adapter.notifyDataSetChanged();
                Snackbar.make(recyclerView, response.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    private void onError(String errorMessage) {
        Snackbar.make(recyclerView, errorMessage, Snackbar.LENGTH_SHORT).show();


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spinner_month:
                if (i != 0) {
                    System.out.println("Monthly : " + spinnerMonth.getSelectedItem());
                    month = String.valueOf(spinnerMonth.getSelectedItem());

                    if (selectyear.trim().isEmpty())
                        Snackbar.make(spinnerMonth, "Please select year first", Snackbar.LENGTH_SHORT).show();
                    else
                        fetchReportData();
                } else {
                    month = "";
                }
                break;

            case R.id.spinner_year:
                if (i != 0) {
                    System.out.println("Yearly : " + spinnerYear.getSelectedItem());
                    selectyear = String.valueOf(spinnerYear.getSelectedItem());
                    fetchReportData();
                } else {
                    selectyear = "";
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
