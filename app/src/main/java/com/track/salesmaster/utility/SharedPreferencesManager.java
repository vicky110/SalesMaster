package com.track.salesmaster.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.track.salesmaster.MainActivity;
import com.track.salesmaster.response.Data;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPreferencesManager {
    private String TAG = "v-v "+ getClass().getName();
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String SHARED_PREF_ADDRESS = "my_address";
    private static SharedPreferencesManager mInstance;
    private Context mContext;

    private SharedPreferencesManager(Context mContext){
        this.mContext = mContext;
    }
    //singleton class
    public static synchronized SharedPreferencesManager getInstance(Context mContext){
        if(mInstance == null){
            mInstance = new SharedPreferencesManager(mContext);
        }
        return mInstance;
    }

    public void saveUser(Data data) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("id",data.getId());
        editor.putString("name",data.getUserName());
        editor.putString("email", data.getUserEmail());
        editor.putString("mobile", data.getUserMobile());

        editor.apply();
    }

    //saving array list to shared preference
    public void saveAddress(ArrayList arrayList) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_ADDRESS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("LIST", json);
        editor.apply();
        Log.d(TAG, "save address");
    }

    //re-calling array list from shared preference
    public ArrayList<String> getAddressList() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_ADDRESS, Context.MODE_PRIVATE);
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls() // modified way to initializing gson because of this error "java.lang.SecurityException: Can't make a java.lang.reflect.Method constructor accessible"
                .create();
        String jsonData = sharedPreferences.getString("LIST", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(jsonData, type); // returning array list
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", null) != null;
    }

    public void clear() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public Data getUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return new Data(
                sharedPreferences.getString("id", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("mobile", null)
                );
    }


}
