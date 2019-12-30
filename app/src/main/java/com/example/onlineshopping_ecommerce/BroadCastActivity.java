package com.example.onlineshopping_ecommerce;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.onlineshopping_ecommerce.broadcast.BroadcastReceiverEcom;

public class BroadCastActivity extends AppCompatActivity {


    BroadcastReceiverEcom broadcastReceiverEcom = new BroadcastReceiverEcom(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast);
    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter= new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiverEcom,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiverEcom);
    }
}
