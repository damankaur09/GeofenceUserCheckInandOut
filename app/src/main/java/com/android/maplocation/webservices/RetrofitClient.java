package com.android.maplocation.webservices;

import com.android.maplocation.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient<T> {

    private static Retrofit mRetrofit;

    private RetrofitClient() {

    }

    private static OkHttpClient getClient() {
        OkHttpClient client;
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        } else {
            client = new OkHttpClient.Builder().build();
        }

        OkHttpClient.Builder builder = client.newBuilder().readTimeout(120, TimeUnit.SECONDS).connectTimeout(120, TimeUnit.SECONDS);
        return builder.build();
    }

    public static RetrofitApis getRetrofitClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("http://zeroguess.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient())
                    .build();
        }
        return mRetrofit.create(RetrofitApis.class);
    }
}