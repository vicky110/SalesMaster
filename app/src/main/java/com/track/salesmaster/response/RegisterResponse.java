package com.track.salesmaster.response;

public class RegisterResponse {

    private Boolean error;
    private String error_msg;
    private Integer data;

    public RegisterResponse(Boolean error, String error_msg, Integer data) {
        this.error = error;
        this.error_msg = error_msg;
        this.data = data;
    }

    public Boolean getError() {
        return error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public Integer getData() {
        return data;
    }
}
