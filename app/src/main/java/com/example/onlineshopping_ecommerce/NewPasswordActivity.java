package com.example.onlineshopping_ecommerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.model.OTP;
import com.example.onlineshopping_ecommerce.url.Url;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPasswordActivity extends AppCompatActivity {
    private EditText txtpassword,txtrepassword;
    private Button btnsend;
    private String email;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewPasswordActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Password Reset");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewPasswordActivity.this, ForgotPasswordActivity.class);
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

            email=bundle.getString("email");

        }
        txtpassword=findViewById(R.id.txtpassword);
        txtrepassword=findViewById(R.id.txtrepassword);
        btnsend=findViewById(R.id.send);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    submitQuery();
                }




            }
        });
    }
    private boolean validate(){
        boolean checkvalidate=true;
        if (txtpassword.getText().toString().equals("") || txtpassword.getText().toString().length() == 0
                || txtrepassword.getText().toString().equals("") || txtrepassword.getText().toString().length() == 0) {
            checkvalidate=false;
            Toast.makeText(NewPasswordActivity.this,"All Fields Are Required !",Toast.LENGTH_SHORT).show();

        }

        if(!txtpassword.getText().toString().equals(txtrepassword.getText().toString())){
            checkvalidate=false;
            Toast.makeText(NewPasswordActivity.this,"Password donot matched !",Toast.LENGTH_SHORT).show();


        }
        return  checkvalidate;

    }
    private void submitQuery() {
        String password = txtpassword.getText().toString();
        String repassword = txtrepassword.getText().toString();

            APIServer server = Url.getInstance().create(APIServer.class);


            Call<Void> usersCall = server.reset(email,password);
            usersCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(NewPasswordActivity.this, "code " + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(NewPasswordActivity.this, "Password Changed Successfully !", Toast.LENGTH_SHORT).show();
                     txtpassword.setText("");
                     txtrepassword.setText("");



                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(NewPasswordActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });







    }

}
