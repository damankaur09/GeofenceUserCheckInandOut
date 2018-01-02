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
    private Button btSelectDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.reportlist_display, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recycler_view);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        fetchReportData();
    }

    public void fetchReportData()
    {

        ReportParams params=new ReportParams();
        params.setTask("getWorklog");
        params.setUserTimeZone("Asia/Kolkata");
        params.setUserId(SharedPreferencesHandler.getStringValues(getActivity(), getString(R.string.pref_user_id)));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String formatted = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE,1);
        params.setCurrentDate(formatted);

        Call<ReportsBean> call= RetrofitClient.getRetrofitClient().reports(params);
        call.enqueue(new Callback<ReportsBean>() {
            @Override
            public void onResponse(Call<ReportsBean> call, Response<ReportsBean> response) {
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
                String intime,outtime,location,shifthours,currentdate,startDate,endDate,totlhours;
                startDate=dataBean.getStartDate();
                endDate=dataBean.getStartDate();
                totlhours=dataBean.get_$TotalHours122();

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
