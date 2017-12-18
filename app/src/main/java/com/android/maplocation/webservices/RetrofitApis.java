package com.android.maplocation.webservices;


import com.android.maplocation.bean.AddUserBean;
import com.android.maplocation.serviceparams.AddUserParams;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface RetrofitApis {


    @POST("010/employeeTracking/server/actionUser.php")
    Call<AddUserBean> add(@Body AddUserParams params);


}
