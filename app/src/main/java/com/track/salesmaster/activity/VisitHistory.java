package com.track.salesmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.track.salesmaster.R;
import com.track.salesmaster.utility.SharedPreferencesManager;

import java.util.ArrayList;

public class VisitHistory extends AppCompatActivity implements View.OnClickListener {
    public String TAG = "v-v " + getClass().getName();

    ListView lv;

    ArrayList<String> visitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_history);
        findViewById(R.id.button_clear_visit).setOnClickListener(this);

        lv = findViewById(R.id.address_listview);

        displayList();
    }

    private void displayList() {
        visitList = SharedPreferencesManager.getInstance(this).getAddressList();
        Log.d(TAG, "gson list = " + visitList);
        if (visitList != null) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_view, R.id.textView_address,visitList);
            lv.setAdapter(arrayAdapter);

        } else {
            Log.d(TAG, "gson list = Nothing to show");
            Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_clear_visit:
                SharedPreferencesManager.getInstance(this).clear_address();
                Toast.makeText(this, "All visit cleared", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
        }
            
    }
}
