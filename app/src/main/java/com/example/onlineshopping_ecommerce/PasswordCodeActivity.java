package com.example.onlineshopping_ecommerce;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.model.OTP;
import com.example.onlineshopping_ecommerce.model.PasswordResponse;
import com.example.onlineshopping_ecommerce.url.Url;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordCodeActivity extends AppCompatActivity {
    private EditText txtcode;
    private Button btnsend;

    public String currtime,finaltime,code,email;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PasswordCodeActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_code);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Recovery Account");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordCodeActivity.this, ForgotPasswordActivity.class);
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
        Bundle bundle =getIntent().getExtras();
        if(bundle !=null){

            currtime=bundle.getString("currtime");
            finaltime=bundle.getString("finaltime");
            code=bundle.getString("val");
            email=bundle.getString("email");
          }

        txtcode=findViewById(R.id.txtcode);
        btnsend=findViewById(R.id.send);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()) {
                    submitQuery();
                }



            }
        });

    }
    private boolean validate(){
        boolean checkvalidate=true;
        if (txtcode.getText().toString().equals("") || txtcode.getText().toString().length() == 0
        ) {
            checkvalidate=false;
            Toast.makeText(PasswordCodeActivity.this,"Enter OTP PIN !",Toast.LENGTH_SHORT).show();

        }


        return  checkvalidate;

    }

    private void submitQuery() {
        String usercode = txtcode.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        //Toast.makeText(this, ""+currentDateandTime, Toast.LENGTH_SHORT).show();

        APIServer server = Url.getInstance().create(APIServer.class);


        Call<OTP> usersCall = server.codevalidate(code,usercode,currtime,finaltime,currentDateandTime);
        usersCall.enqueue(new Callback<OTP>() {
            @Override
            public void onResponse(Call<OTP> call, Response<OTP> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PasswordCodeActivity.this, "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body().getSuccess()){
                    //Toast.makeText(PasswordCodeActivity.this, "success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PasswordCodeActivity.this, NewPasswordActivity.class);
                    intent.putExtra("email",email);


                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(PasswordCodeActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();


                }



            }

            @Override
            public void onFailure(Call<OTP> call, Throwable t) {
                Toast.makeText(PasswordCodeActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }

}
