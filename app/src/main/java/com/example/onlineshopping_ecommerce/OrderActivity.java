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
import android.widget.Toast;

import com.example.onlineshopping_ecommerce.adapter.OrderAdapter;
import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.model.Order;
import com.example.onlineshopping_ecommerce.url.Url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Order> catList;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrderActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        recyclerView=findViewById(R.id.recyclerView);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("My Order");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, DashboardActivity.class);
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
        String id=Url.userid;
        Call<List<Order>> call = api.getOrder(Url.token,id);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {

                if(!response.isSuccessful())
                {
                    Toast.makeText(OrderActivity.this, "Error" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body()==null)
                {
                    Toast.makeText(OrderActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    return;
                }

                catList = response.body();
                OrderAdapter catAdapter = new OrderAdapter(catList, OrderActivity.this);
                recyclerView.setAdapter(catAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(OrderActivity.this,"Error "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();


            }
        });

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}
