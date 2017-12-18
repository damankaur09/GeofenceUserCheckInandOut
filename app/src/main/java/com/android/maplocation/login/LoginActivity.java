package com.android.maplocation.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.maplocation.GetMapLocationActivity;
import com.android.maplocation.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent mapLocation=new Intent(LoginActivity.this, GetMapLocationActivity.class);
        startActivity(mapLocation);
    }
}
