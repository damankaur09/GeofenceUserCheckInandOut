package com.android.maplocation.reports;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.maplocation.R;
import com.android.maplocation.adapter.RecycleAdapter;
import com.android.maplocation.pojo.ReportData;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Daman on 12/28/2017.
 */

public class WeeklyReport extends Fragment {

    private RecyclerView recyclerView;
    private TextView tv_startDate,tv_endDate;
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
        tv_startDate=view.findViewById(R.id.tv_startDate);
        tv_endDate=view.findViewById(R.id.tv_endDate);
        tv_startDate.setVisibility(View.VISIBLE);
        tv_endDate.setVisibility(View.VISIBLE);
        tv_startDate.setText("Start Date: 2017-12-25");
        tv_endDate.setText("End Date: 2017-12-29");
        fetchReportData();
    }

    public void fetchReportData()
    {
        ReportData[] userReportsData={new ReportData("2017-12-26","Chandigarh","10.00.00","12.00.00","2 hours")
                ,new ReportData("2017-12-26","Chandigarh","13.00.00","16.00.00","3 hours")
        ,new ReportData("2017-12-27","Chandigarh","10.00.00","12.00.00","2 hours")
                ,new ReportData("2017-12-27","Chandigarh","13.00.00","16.00.00","3 hours")
                ,new ReportData("2017-12-28","Chandigarh","10.00.00","12.00.00","2 hours")
                ,new ReportData("2017-12-28","Chandigarh","13.00.00","16.00.00","3 hours")};

        ArrayList<ReportData> list=new ArrayList(Arrays.asList(userReportsData));

        /*String date=null;
        String location=null;
        String intime,outime,hours=null;
        for (int i=0;i<list.size();i++)
        {
           date=list.get(i).getDate();
           location=list.get(i).getLocation();
           intime=list.get(i).getInTime();
           outime=list.get(i).getOutTime();
           hours=list.get(i).getTotalhours();


            list.add(new DataList(name,image,userName));
        }*/

        RecycleAdapter adapter=new RecycleAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);
    }
}
