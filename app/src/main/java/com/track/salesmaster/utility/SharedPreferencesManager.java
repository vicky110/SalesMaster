package com.track.salesmaster.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.track.salesmaster.response.Data;

public class SharedPreferencesManager {
    private static final String SHARED_PREF_NAME = "my_shared_pref";
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
