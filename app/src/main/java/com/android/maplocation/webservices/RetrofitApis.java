package com.android.maplocation.webservices;


import com.android.maplocation.bean.AddUserBean;
import com.android.maplocation.bean.OfficeLocationBean;
import com.android.maplocation.bean.UserLoginBean;
import com.android.maplocation.serviceparams.AddUserParams;
import com.android.maplocation.serviceparams.OfficeLocationParams;
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
}
