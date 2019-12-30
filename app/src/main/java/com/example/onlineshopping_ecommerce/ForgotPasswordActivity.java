package com.example.onlineshopping_ecommerce;

import android.content.Intent;
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
import com.example.onlineshopping_ecommerce.model.PasswordResponse;
import com.example.onlineshopping_ecommerce.url.Url;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText txtemail;
    private Button btnsend;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Recovery Account");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
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

        txtemail=findViewById(R.id.txtemail);
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
        if (txtemail.getText().toString().equals("") || txtemail.getText().toString().length() == 0
            ) {
            checkvalidate=false;
            Toast.makeText(ForgotPasswordActivity.this,"Enter Associated Email Account !",Toast.LENGTH_SHORT).show();

        }


        return  checkvalidate;

    }

    private void submitQuery() {


        APIServer server = Url.getInstance().create(APIServer.class);
        String email = txtemail.getText().toString();

        Call<PasswordResponse> usersCall = server.forgotpassword(email);
        usersCall.enqueue(new Callback<PasswordResponse>() {
            @Override
            public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(response.body().getVal())!=0){
                    Snackbar
                            .make(btnsend, "Please Check your Mail !", Snackbar.LENGTH_LONG)
                            .show();

                    Intent intent = new Intent(ForgotPasswordActivity.this, PasswordCodeActivity.class);
                    intent.putExtra("email",txtemail.getText().toString());

                    intent.putExtra("currtime",response.body().getCurrtime());
                    intent.putExtra("finaltime",response.body().getFinaltime());
                    intent.putExtra("val",response.body().getVal());
                    startActivity(intent);
                    finish();

                }else{
                    Snackbar
                            .make(btnsend, "This account is not associated with us !", Snackbar.LENGTH_LONG)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<PasswordResponse> call, Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
