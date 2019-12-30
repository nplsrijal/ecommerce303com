package com.example.onlineshopping_ecommerce.BLL;




import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.model.Product;
import com.example.onlineshopping_ecommerce.url.Url;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductBLL {
    private String Name;
    private String Token;
    private String CategoryId;
    boolean isSuccess=false;

    public ProductBLL(String name, String token, String categoryId) {
        Name = name;
        Token = token;
        CategoryId = categoryId;
    }

    public boolean checkSearchProductByName(){
        APIServer api = Url.getInstance().create(APIServer.class);
        Call<List<Product>> productCall = api.getProductByName(Token,CategoryId,Name);

        productCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

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
            public void onFailure(Call<List<Product>> call, Throwable t) {

                 isSuccess=false;
            }
        });
        return isSuccess;
    }
}
