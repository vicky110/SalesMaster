package com.track.salesmaster.response;

import android.util.Log;

public class LoginResponse {

    public String TAG = "v-v" + getClass().getName();

    private Boolean error;
    private Data data;

    public LoginResponse(Boolean error, Data data) {
        this.error = error;
        this.data = data;
    }

    public Boolean getError() {
        return error;
    }

    public Data getData() {
        return data;
    }
}
