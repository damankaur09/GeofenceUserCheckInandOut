package com.android.maplocation.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.maplocation.GetMapLocationActivity;
import com.android.maplocation.R;
import com.android.maplocation.registeruser.RegisterUserActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
/*
        Intent mapLocation=new Intent(LoginActivity.this, GetMapLocationActivity.class);
        startActivity(mapLocation);*/

        btregister=findViewById(R.id.bt_registrer);

        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adduser=new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(adduser);
            }
        });
    }
}
