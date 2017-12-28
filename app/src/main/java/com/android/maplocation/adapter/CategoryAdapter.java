package com.android.maplocation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.maplocation.reports.DailyReport;
import com.android.maplocation.reports.MonthlyReport;
import com.android.maplocation.reports.WeeklyReport;

/**
 * Created by Daman on 12/28/2017.
 */
public class CategoryAdapter extends FragmentPagerAdapter {

    private String [] tabTitles={"Daily","Weekly","Monthly"};
    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {

            return new DailyReport();
        }
        if(position==1)
        {
            return new WeeklyReport();
        }
        if(position==2)
        {
            return new MonthlyReport();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
