package com.android.maplocation.registeruser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maplocation.R;
import com.android.maplocation.bean.AddUserBean;
import com.android.maplocation.serviceparams.AddUserParams;
import com.android.maplocation.webservices.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserActivity extends AppCompatActivity {

    private Button btRegister;
    private DialogTitle dialogTitle;
    private TextView alert;
    private EditText etfirstname,etlastname,etemail,etpassword,etaddress,etcountry,etstate,etcity,etphoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        etfirstname=findViewById(R.id.et_firstname);
        etlastname=findViewById(R.id.et_lastname);
        etemail=findViewById(R.id.et_email);
        etpassword=findViewById(R.id.et_password);
        btRegister=findViewById(R.id.bt_registration);
        etaddress=findViewById(R.id.et_address);
        etcountry=findViewById(R.id.et_country);
        etcity=findViewById(R.id.et_city);
        etstate=findViewById(R.id.et_state);
        etphoneno=findViewById(R.id.et_phoneno);
        alert=findViewById(R.id.tv_alert);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
                alert.setVisibility(View.VISIBLE);
            }
        });

    }

    private void registerUser()
    {
        AddUserParams params=new AddUserParams();
        params.setTask("addUser");
        params.setFirst_name(etfirstname.getText().toString());
        params.setLast_name(etlastname.getText().toString());
        params.setEmail(etemail.getText().toString());
        params.setPassword(etpassword.getText().toString());
        params.setAddress(etaddress.getText().toString());
        params.setCity(etcity.getText().toString());
        params.setCountry(etcountry.getText().toString());
        params.setState(etstate.getText().toString());
        params.setContact_no(etphoneno.getText().toString());
        Call<AddUserBean> registerUser= RetrofitClient.getRetrofitClient().add(params);
        registerUser.enqueue(new Callback<AddUserBean>() {
            @Override
            public void onResponse(Call<AddUserBean> call, Response<AddUserBean> response) {

                Toast.makeText(RegisterUserActivity.this, "Thanks for registration.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<AddUserBean> call, Throwable t) {

            }
        });
    }
}
