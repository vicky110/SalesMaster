package com.track.salesmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.track.salesmaster.MainActivity;
import com.track.salesmaster.R;
import com.track.salesmaster.response.Data;
import com.track.salesmaster.response.LoginResponse;
import com.track.salesmaster.rest.RetrofitClient;
import com.track.salesmaster.rest.RestInterface;
import com.track.salesmaster.utility.SharedPreferencesManager;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "v-v "+ getClass().getName();
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.et_login_email);
        password = findViewById(R.id.et_login_password);

        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.button_goto_register).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //check if user logged in or not
        if(SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_login:

                String email_data = email.getText().toString().trim();
                String password_data = password.getText().toString().trim();

                if(email_data.isEmpty()){
                    email.setError("Email Required");
                    email.requestFocus();
                    return;
                }
                if(password_data.length() < 6){
                    password.setError("Password should be 6 digit long");
                    password.requestFocus();
                    return;
                }
                doLogin(email_data,password_data);

                break;

            case R.id.button_goto_register:
                Intent i = new Intent(this, RegistrationActivity.class);
                startActivity(i);
                break;
        }

    }

    public void doLogin(String email, String password) {
        Call<LoginResponse> login = RetrofitClient.getInstance().getApi().login(email,password);
        login.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Log.d(TAG, "Response Received");

                if (response.isSuccessful()) {
                    Log.d(TAG, "Response isSuccessful");
                    try {
                        LoginResponse s = response.body();
                        if(!s.getError()){
                            SharedPreferencesManager.getInstance(LoginActivity.this)
                                    .saveUser(s.getData());

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "Response isSuccessful = " +e.getMessage());
                    }
                } else {
                    Log.d(TAG, "Response Not Successful");
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.d(TAG, "Response Not Successful = " +e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(TAG, "Response Fail = " +t.getMessage());
            }
        });

    }
}
