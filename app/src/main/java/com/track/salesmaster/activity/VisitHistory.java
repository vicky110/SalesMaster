package com.track.salesmaster.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.track.salesmaster.MainActivity;
import com.track.salesmaster.R;
import com.track.salesmaster.utility.SharedPreferencesManager;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class VisitHistory extends AppCompatActivity {
    public String TAG = "v-v " + getClass().getName();

    ArrayList<String> visitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_history);

        visitList = SharedPreferencesManager.getInstance(this).getAddressList();

        Log.d(TAG, "gson list = " + visitList);

    }
}
