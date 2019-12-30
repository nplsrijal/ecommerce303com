package com.example.onlineshopping_ecommerce;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping_ecommerce.adapter.CategoryAdapter;
import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.model.Category;
import com.example.onlineshopping_ecommerce.model.Users;
import com.example.onlineshopping_ecommerce.url.Url;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private TextView txtname,txtemail,txtphone,txtlocation,txttype;
    private CircleImageView img;
    private ImageView edit;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txtname=findViewById(R.id.user_profile_name);
        txtemail=findViewById(R.id.user_profile_email);
        txtphone=findViewById(R.id.user_profile_phone);
        txtlocation=findViewById(R.id.user_profile_location);
        txttype=findViewById(R.id.user_profile_type);
        img=findViewById(R.id.user_profile_photo);
        edit=findViewById(R.id.edit);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("My Profile");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
                finish();


            }
        });

        String id =Url.userid;
                APIServer api = Url.getInstance().create(APIServer.class);
        Call<Users> call = api.getUserById(Url.token,id);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                if(!response.isSuccessful())
                {
                    Toast.makeText(ProfileActivity.this, "Error" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body()==null)
                {
                    Toast.makeText(ProfileActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    return;
                }
                String imgPath = Url.BASE_URL+"uploads/user/"+ response.body().getImage();
                StrictMode();

                try{
                    URL url = new URL(imgPath);
                    img.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                 txtname.setText(response.body().getFullname());
                 txtemail.setText(response.body().getEmailId());
                 txtphone.setText(response.body().getPhone());
                 txtlocation.setText(response.body().getLocation());
                 txttype.setText("User");


            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(ProfileActivity.this,"Error "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();


            }
        });

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
