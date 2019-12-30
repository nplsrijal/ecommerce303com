package com.example.onlineshopping_ecommerce.model;

public class LoginSignupResponse {
    private boolean success;
    private String status;
    private String token;
    private String userid;

    public boolean getSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }
    public String getToken() {
        return token;
    }

    public String getUserid() {
        return userid;
    }
}
