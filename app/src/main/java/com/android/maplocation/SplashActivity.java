package com.android.maplocation;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.maplocation.geofence.GetMapLocationActivity;
import com.android.maplocation.login.LoginActivity;
import com.android.maplocation.utils.SharedPreferencesHandler;

public class SplashActivity extends AppCompatActivity {

    private boolean isNavigate=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isNavigate ) {

                    if (SharedPreferencesHandler.getBooleanValues(SplashActivity.this,getString(R.string.pref_is_login))) {
                        startActivity(new Intent(SplashActivity.this, GetMapLocationActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                }
            }
        }, 1000);

    }

    @Override
    public void onBackPressed() {
        isNavigate = true;
        super.onBackPressed();
    }
}
