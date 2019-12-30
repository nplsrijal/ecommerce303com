package com.example.onlineshopping_ecommerce.model;

public class PasswordResponse {
    private String currtime;
    private String finaltime;
    private String val;

    public PasswordResponse(String currtime, String finaltime, String val) {
        this.currtime = currtime;
        this.finaltime = finaltime;
        this.val = val;
    }

    public String getCurrtime() {
        return currtime;
    }

    public void setCurrtime(String currtime) {
        this.currtime = currtime;
    }

    public String getFinaltime() {
        return finaltime;
    }

    public void setFinaltime(String finaltime) {
        this.finaltime = finaltime;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
