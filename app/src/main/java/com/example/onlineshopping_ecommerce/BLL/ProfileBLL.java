package com.example.onlineshopping_ecommerce.BLL;

import com.example.onlineshopping_ecommerce.api.APIServer;

import com.example.onlineshopping_ecommerce.url.Url;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileBLL {
    private String id;
    private String FullName;
    private String EmailId;
    private String Phone;
    private String Location;
    private String Token;
    private String image;
    boolean isSuccess;

    public ProfileBLL(String id, String fullName, String emailId, String phone, String location, String token, String image) {
        this.id = id;
        FullName = fullName;
        EmailId = emailId;
        Phone = phone;
        Location = location;
        Token = token;
        this.image = image;
    }

    public boolean checkProfileUpdate(){
        APIServer api = Url.getInstance().create(APIServer.class);
        Call<Void> profileCall = api.updateProfile(Token,id,FullName,EmailId,Phone,Location,image);

        profileCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful())
                {
                    isSuccess=false;

                }

                if(response.body()==null)
                {
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
