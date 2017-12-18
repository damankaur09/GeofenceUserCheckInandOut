package com.android.maplocation.registeruser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.view.View;
import android.widget.Button;

import com.android.maplocation.R;

public class RegisterUserActivity extends AppCompatActivity {

    private Button btRegister;
    private DialogTitle dialogTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        btRegister=findViewById(R.id.bt_registration);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
