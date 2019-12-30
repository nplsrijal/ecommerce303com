package com.example.onlineshopping_ecommerce;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import com.example.onlineshopping_ecommerce.url.Url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactActivity extends AppCompatActivity {
    private  EditText txtsubject,txtbody;
    private Button btnsend;

    private SensorManager sensorManager;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Contact");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        sensorGyro();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        txtsubject=findViewById(R.id.subject);
        txtbody=findViewById(R.id.body);
        btnsend=findViewById(R.id.send);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitQuery();



            }
        });
    }

    private void sensorGyro() {


            sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
            Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            SensorEventListener gyro=new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    if(sensorEvent.values[2] > 0.5f) { // anticlockwise
                        Intent intent = new Intent(ContactActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        sensorManager.unregisterListener(this);
                    } else if(sensorEvent.values[2] < -0.5f) { // clockwise
                        submitQuery();

                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };

            if(sensor != null){
                sensorManager.registerListener(gyro,sensor,SensorManager.SENSOR_DELAY_NORMAL);
            }else{
                Toast.makeText(this, "No Sensor Found", Toast.LENGTH_SHORT).show();
            }

    }

    private void submitQuery() {
        APIServer server = Url.getInstance().create(APIServer.class);
        String Subject = txtsubject.getText().toString();
        String Message = txtbody.getText().toString();
        String Userid=Url.userid;




        Call<Void> usersCall = server.contact(Url.token,Userid,Subject,Message);
        usersCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ContactActivity.this, "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Snackbar
                        .make(btnsend, "Your Queries has been sent !", Snackbar.LENGTH_LONG)
                        .show();                       }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ContactActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
