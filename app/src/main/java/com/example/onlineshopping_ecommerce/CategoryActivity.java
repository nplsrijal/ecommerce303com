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

import com.example.onlineshopping_ecommerce.adapter.CategoryAdapter;
import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.model.Category;
import com.example.onlineshopping_ecommerce.url.Url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Category> catList;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CategoryActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        recyclerView=findViewById(R.id.recyclerView);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Category");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        APIServer api = Url.getInstance().create(APIServer.class);
        Call<List<Category>> call = api.getAllCategory(Url.token);
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                if(!response.isSuccessful())
                {
                    Toast.makeText(CategoryActivity.this, "Error" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body()==null)
                {
                    Toast.makeText(CategoryActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    return;
                }

                catList = response.body();
                CategoryAdapter catAdapter = new CategoryAdapter(catList, CategoryActivity.this);
                recyclerView.setAdapter(catAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(CategoryActivity.this,2));
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(CategoryActivity.this,"Error "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();


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
