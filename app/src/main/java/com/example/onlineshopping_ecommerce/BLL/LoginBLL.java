package com.example.onlineshopping_ecommerce.BLL;



import android.content.Context;

import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.model.LoginSignupResponse;
import com.example.onlineshopping_ecommerce.url.Url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginBLL {
    private String email;
    private String Password;
    boolean isSuccess=false;


    public LoginBLL(String email, String Password) {
        this.email = email;
        this.Password = Password;
    }




    public boolean checkLogin(){
        APIServer api = Url.getInstance().create(APIServer.class);
        Call<LoginSignupResponse> heroesCall = api.checkUser(email,Password);

        try {
            Response<LoginSignupResponse> userCall = heroesCall.execute();
            if (userCall.body().getSuccess()){

                 Url.token=userCall.body().getToken();
                isSuccess = true;
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
        return isSuccess;
    }





}
