package com.android.maplocation.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.maplocation.geofence.GetMapLocationActivity;
import com.android.maplocation.R;
import com.android.maplocation.bean.Locations;
import com.android.maplocation.bean.OfficeLocationBean;
import com.android.maplocation.bean.UserLoginBean;
import com.android.maplocation.pojo.OfficeLocations;
import com.android.maplocation.pojo.UserDetails;
import com.android.maplocation.registeruser.RegisterUserActivity;
import com.android.maplocation.serviceparams.OfficeLocationParams;
import com.android.maplocation.serviceparams.UserLoginParams;
import com.android.maplocation.utils.SharedPreferencesHandler;
import com.android.maplocation.webservices.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btregister, btlogin;
    EditText etemail, etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btregister = findViewById(R.id.bt_registrer);
        btlogin = findViewById(R.id.bt_signin);
        etemail = findViewById(R.id.et_email);
        etpassword = findViewById(R.id.et_password);

        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adduser = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(adduser);
            }
        });

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etemail.getText().toString().trim();
                String password = etpassword.getText().toString().trim();

                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email.", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                } else {

                    userLogin(email, password);
                }

            }
        });
    }

    private void userLogin(String email, String password) {
        UserLoginParams params = new UserLoginParams();
        params.setTask("getUserDetails");
        params.setEmail(email);
        params.setPassword(password);

        Call<UserLoginBean> call = RetrofitClient.getRetrofitClient().login(params);
        call.enqueue(new Callback<UserLoginBean>() {
            @Override
            public void onResponse(Call<UserLoginBean> call, Response<UserLoginBean> response) {

                if (response.isSuccessful()) {
                    onSuccess(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<UserLoginBean> call, Throwable t) {
                onError(t.getMessage());
            }
        });
    }

    private void onError(String errorMessage) {
        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void onSuccess(UserLoginBean response) {

        switch (response.getStatus()) {
            case 200:
                Toast.makeText(LoginActivity.this, response.getStatusMessage(), Toast.LENGTH_SHORT).show();
                SharedPreferencesHandler.setStringValues(this, getString(R.string.pref_user_id), response.getBean().getUser_id());
                SharedPreferencesHandler.setStringValues(this,"pref_user_name",response.getBean().getFirst_name());
                SharedPreferencesHandler.setBooleanValues(this, getString(R.string.pref_is_login), true);
                startActivity(new Intent(this, GetMapLocationActivity.class));
                break;
            case 400:
                Toast.makeText(LoginActivity.this, response.getStatusMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

