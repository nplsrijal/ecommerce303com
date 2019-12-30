package com.example.onlineshopping_ecommerce.url;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Url {
    public static final String BASE_URL ="http://192.168.1.102:3000/";
    public static  String token="";
    public static  String userid="";

    public static Retrofit getInstance(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }
}
