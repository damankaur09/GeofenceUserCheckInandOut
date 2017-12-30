package com.android.maplocation.reports;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;

import com.android.maplocation.R;
import com.android.maplocation.adapter.RecycleAdapter;
import com.android.maplocation.bean.ReportsBean;
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

public class DailyReport extends Fragment {


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
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
        fetchReportData();
    }

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


                final ArrayList<ReportData> list=new ArrayList<>();
                String date,intime,outtime,location,shifthours,currentdate,startDate,endDate,totlhours;
                startDate=dataBean.getStartDate();
                endDate=dataBean.getStartDate();
                totlhours=dataBean.get_$TotalHours122().toString();

                List<ReportsBean.DataBean.UserlogBean> userlog=dataBean.getUserlog();
                for (int i=0;i<userlog.size()-1;i++)
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
            }

            @Override
            public void onFailure(Call<ReportsBean> call, Throwable t) {

            }
        });

    }

}


