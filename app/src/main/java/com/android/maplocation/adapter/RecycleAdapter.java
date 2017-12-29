package com.android.maplocation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.maplocation.R;
import com.android.maplocation.pojo.ReportData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Daman on 12/29/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<ReportData> data;
    public RecycleAdapter(Context context,ArrayList<ReportData> data) {
        this.context=context;
        this.data=data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_intime,tv_outime,tv_locationname,tv_date,tv_hours;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_date=itemView.findViewById(R.id.tv_value_date);
            tv_locationname=itemView.findViewById(R.id.tv_value_location);
            tv_intime=itemView.findViewById(R.id.tv_value_intime);
            tv_outime=itemView.findViewById(R.id.tv_value_outtime);
            tv_hours=itemView.findViewById(R.id.tv_value_currentshifthours);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.reportlist_display,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            /*Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
            String formatted = dateFormat.format( cal.getTime());

            holder.tv_date.setText(formatted);*/
            holder.tv_date.setText(data.get(position).getDate());
            holder.tv_locationname.setText(data.get(position).getLocation());
            holder.tv_intime.setText(data.get(position).getInTime());
            holder.tv_outime.setText(data.get(position).getOutTime());
            holder.tv_hours.setText(data.get(position).getTotalhours());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
