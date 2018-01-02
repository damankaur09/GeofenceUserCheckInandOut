package com.android.maplocation.webservices;


import com.android.maplocation.bean.AddUserBean;
import com.android.maplocation.bean.CheckInTimeBean;
import com.android.maplocation.bean.CheckOutTimeBean;
import com.android.maplocation.bean.OfficeLocationBean;
import com.android.maplocation.bean.ReportsBean;
import com.android.maplocation.bean.UserLoginBean;
import com.android.maplocation.serviceparams.AddUserParams;
import com.android.maplocation.serviceparams.CheckInTimeParams;
import com.android.maplocation.serviceparams.CheckOutTimeParams;
import com.android.maplocation.serviceparams.OfficeLocationParams;
import com.android.maplocation.serviceparams.ReportParams;
import com.android.maplocation.serviceparams.UserLoginParams;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface RetrofitApis {


    @POST("010/employeeTracking/server/actionUser.php")
    Call<AddUserBean> add(@Body AddUserParams params);

    @POST("010/employeeTracking/server/actionUser.php")
    Call<UserLoginBean> login(@Body UserLoginParams params);

    @POST("010/employeeTracking/server/actionSitelocation.php")
    Call<OfficeLocationBean> location(@Body OfficeLocationParams params);

    @POST("010/employeeTracking/server/actionWorklog.php")
    Call<CheckInTimeBean> checkin(@Body CheckInTimeParams params);

    @POST("010/employeeTracking/server/actionWorklog.php")
    Call<CheckOutTimeBean> checkout(@Body CheckOutTimeParams params);

    @POST("010/employeeTracking/server/actionWorklog.php")
    Call<ReportsBean> reports(@Body ReportParams params);


}
