package com.track.salesmaster.response;

public class Data {

    private String id;
    private String userName;
    private String userEmail;
    private String userMobile;

    public Data(String id, String userName, String userEmail, String userMobile) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }
}
