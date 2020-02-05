package com.track.salesmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.track.salesmaster.activity.LoginActivity;
import com.track.salesmaster.response.Data;
import com.track.salesmaster.utility.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView_welcome = findViewById(R.id.welcome_text);
        Data data = SharedPreferencesManager.getInstance(this).getUser();

        textView_welcome.setText("Welcome " + data.getUserName());

         findViewById(R.id.button_startLocationUpdate).setOnClickListener(this);
         findViewById(R.id.button_stopLocationUpdate).setOnClickListener(this);
         findViewById(R.id.button_logOut).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //check if user logged in or not
        if(!SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_startLocationUpdate :
                Toast.makeText(this, "Location start button clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button_stopLocationUpdate:
                Toast.makeText(this, "Location stop button clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button_logOut:
                SharedPreferencesManager.getInstance(this).clear();
                Toast.makeText(this, "Logged Out Successfuly", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
        }

    }
}
