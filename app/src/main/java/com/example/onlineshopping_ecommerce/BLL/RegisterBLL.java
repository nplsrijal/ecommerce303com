package com.example.onlineshopping_ecommerce.BLL;

import android.widget.Toast;

import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.model.LoginSignupResponse;
import com.example.onlineshopping_ecommerce.url.Url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterBLL {
    private String FullName;
    private String EmailId;
    private String Password;
    private String Phone;
    private String Location;
    private String RoleId;
    boolean isSuccess=false;

    public RegisterBLL(String fullName, String emailId, String password, String phone, String location, String roleId) {
        FullName = fullName;
        EmailId = emailId;
        Password = password;
        Phone = phone;
        Location = location;
        RoleId = roleId;
    }

    public boolean checkRegister(){
        APIServer api = Url.getInstance().create(APIServer.class);
        Call<Void> heroesCall = api.register(FullName,EmailId,Phone,Location,Password,RoleId);

        heroesCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    isSuccess=false;
                }
                isSuccess=true;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isSuccess=false;
            }
        });
        return isSuccess;
    }
}
