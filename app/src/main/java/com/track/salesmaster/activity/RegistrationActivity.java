package com.track.salesmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.track.salesmaster.R;
import com.track.salesmaster.response.RegisterResponse;
import com.track.salesmaster.rest.RetrofitClient;
import com.track.salesmaster.utility.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "v-v "+ getClass().getName();
    private EditText email, password, name, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        name = findViewById(R.id.et_name);
        mobile = findViewById(R.id.et_mobile);

        findViewById(R.id.button_register).setOnClickListener(this);
        findViewById(R.id.button_goto_login).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //check if user logged in or not
        if(SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_register:
                String email_data = email.getText().toString().trim();
                String password_data = password.getText().toString().trim();
                String name_data = name.getText().toString().trim();
                String mobile_data = mobile.getText().toString().trim();

                if(email_data.isEmpty()) {
                    email.setError("Enter email id");
                    email.requestFocus();
                    return;
                }
                if(password_data.length() < 6 ){
                    password.setError("Password should be 6 digit long");
                    password.requestFocus();
                    return;
                }
                if(name_data.isEmpty()) {
                    name.setError("Enter full name");
                    name.requestFocus();
                    return;
                }
                if(mobile_data.length() != 10) {
                    mobile.setError("Enter correct mobile number");
                    mobile.requestFocus();
                    return;
                }
                doRegister(email_data, password_data, name_data, mobile_data);
                break;

            case R.id.button_goto_login:
                Intent i =new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
        }

    }

    private void doRegister(String email, String password, String name, String mobile) {

        Call<RegisterResponse> register = RetrofitClient.getInstance().getApi().register(email, password, name, mobile);
        register.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.d(TAG, "Response Received");
                if(response.isSuccessful()){
                    Log.d(TAG, "Response successful");
                    RegisterResponse R = response.body();
                    if(R.getError()){
                        Log.d(TAG, "Response error = " + R.getError());
                        Log.d(TAG, "Response error = " + R.getError_msg());
                        Toast.makeText(RegistrationActivity.this, R.getError_msg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "Response data = " + R.getData());
                        Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                } else {
                    Log.d(TAG, "Response Not successful");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d(TAG, "Response Failed");

            }
        });
    }
}
