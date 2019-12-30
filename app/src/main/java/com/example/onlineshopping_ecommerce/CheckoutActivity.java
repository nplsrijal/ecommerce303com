package com.example.onlineshopping_ecommerce;

import android.app.Notification;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.channel.Channel;
import com.example.onlineshopping_ecommerce.model.Checkout;
import com.example.onlineshopping_ecommerce.model.CheckoutResponse;
import com.example.onlineshopping_ecommerce.url.Url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    private TextView txtview;
    private EditText address,city,landmark,numopt;
    private Button btncheckout;

    private SensorManager sensorManager;
    NotificationManagerCompat notificationManagerCompact;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CheckoutActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        txtview=findViewById(R.id.txtview);
        address=findViewById(R.id.address);
        city=findViewById(R.id.city);
        landmark=findViewById(R.id.landmark);
        numopt=findViewById(R.id.numopt);
        btncheckout=findViewById(R.id.btncheckout);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Checkout");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        sensorGyro();
        notificationManagerCompact=NotificationManagerCompat.from(this);
        Channel channel = new Channel(this);
        channel.createChannel();



        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        String id= Url.userid;
        APIServer server = Url.getInstance().create(APIServer.class);
        Call<Checkout> usersCall = server.getCheckout(Url.token,id);
        usersCall.enqueue(new Callback<Checkout>() {
            @Override
            public void onResponse(Call<Checkout> call, Response<Checkout> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(CheckoutActivity.this, "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body()==null)
                {
                    Toast.makeText(CheckoutActivity.this, "Empty Cart . Couldnot Proceed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CheckoutActivity.this,DashboardActivity.class);

                    startActivity(intent);
                    finish();


                }else{
                    String content="";
                    content +="Order Summary:"+"\n";

                    content +="Subtotal: रू "+response.body().getSum() + "\n";
                    content +="Extra Charge: रू 0"+"\n";
                    content +="Total: रू "+response.body().getSum()+ "\n";



                    content +="........................."+ "\n";
                    txtview.append(content);
                }



            }

            @Override
            public void onFailure(Call<Checkout> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btncheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             submitform();
            }
        });





    }

    private void submitform() {
        String Address=address.getText().toString();
        String City=city.getText().toString();
        String Landmark=landmark.getText().toString();
        String Phone=numopt.getText().toString();

        String Userid=Url.userid;

        APIServer server = Url.getInstance().create(APIServer.class);
        Call<CheckoutResponse> usersCall = server.checkout(Url.token,Userid,Address,City,Landmark,Phone);
        usersCall.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {


                if(!response.isSuccessful()){
                    Toast.makeText(CheckoutActivity.this,"Something Went Wrong !! ",Toast.LENGTH_SHORT).show();
                    return;

                }else{


                    if(response.body().getSuccess()){

                        Toast.makeText(CheckoutActivity.this,"Success",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CheckoutActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(CheckoutActivity.this,"Please Update Your Cart. Product : Out of Stock !",Toast.LENGTH_SHORT).show();


                    }
                }
            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this,"Error "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

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
                    Intent intent = new Intent(CheckoutActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                    sensorManager.unregisterListener(this);
                } else if(sensorEvent.values[2] < -0.5f) { // clockwise
                    submitform();
                    sensorManager.unregisterListener(this);

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

    private void displaySuccessNotificaiton() {
        Notification notification= new NotificationCompat.Builder(CheckoutActivity.this, Channel.CHANNEL_1)

                .setSmallIcon(R.drawable.ic_store_black_24dp)
                .setContentTitle("Success")
                .setContentText("Recieved Your Order")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompact.notify(1,notification);
    }


}
