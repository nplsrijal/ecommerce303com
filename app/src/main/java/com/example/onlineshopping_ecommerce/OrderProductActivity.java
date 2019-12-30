package com.example.onlineshopping_ecommerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineshopping_ecommerce.adapter.OrderProductAdapter;
import com.example.onlineshopping_ecommerce.adapter.ProductAdapter;
import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.model.Product;
import com.example.onlineshopping_ecommerce.url.Url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderProductActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Product> productList;
    public String id;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrderProductActivity.this, OrderActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);
        Bundle bundle =getIntent().getExtras();
        if(bundle !=null){
            id=(bundle.getString("id"));


        }
        recyclerView=findViewById(R.id.recyclerView_p);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_p);
        mToolbar.setTitle("My Order");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderProductActivity.this, OrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        APIServer api = Url.getInstance().create(APIServer.class);
        Call<List<Product>> call = api.getOrderProduct(Url.token,id);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                if(!response.isSuccessful())
                {
                    Toast.makeText(OrderProductActivity.this, "Error" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(response.body()==null)
                {
                    Toast.makeText(OrderProductActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    return;
                }

                productList = response.body();
                OrderProductAdapter productAdapter = new OrderProductAdapter(productList, OrderProductActivity.this);
                recyclerView.setAdapter(productAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(OrderProductActivity.this));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(OrderProductActivity.this,"Error "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();


            }
        });
    }
}
