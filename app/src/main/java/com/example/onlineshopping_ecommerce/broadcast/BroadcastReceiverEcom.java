package com.example.onlineshopping_ecommerce.broadcast;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.example.onlineshopping_ecommerce.R;
import com.example.onlineshopping_ecommerce.channel.Channel;

public class BroadcastReceiverEcom extends BroadcastReceiver {
    private NotificationManagerCompat notificationManagerCompat;
    Context context;

    public BroadcastReceiverEcom(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean noConnectivity = false;
        notificationManagerCompat=NotificationManagerCompat.from(context);
        Channel channel = new Channel(context);
        channel.createChannel();
        
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            noConnectivity=intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
        }
        if(noConnectivity) {
            Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show();
            DisplayOffNotification();
        }
//         else {
//            Toast.makeText(context,"Connected",Toast.LENGTH_SHORT).show();
//            DisplayOnNotification();
//        }
    }

    private void DisplayOnNotification() {
        Notification notification=new NotificationCompat.Builder(context, Channel.CHANNEL_2)
                .setSmallIcon(R.drawable.ic_wifi_black_24dp)
                .setContentTitle("Connected")
                .setContentText("You have been connected to a network")
                .setCategory(NotificationCompat.CATEGORY_SYSTEM)
                .build();
        notificationManagerCompat.notify(2,notification);
    }

    private void DisplayOffNotification() {
        Notification notification=new NotificationCompat.Builder(context, Channel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_signal_wifi_0_bar_black_24dp)
                .setContentTitle("No Connectivity")
                .setContentText("It seems like your phone is not connected to a network")
                .setCategory(NotificationCompat.CATEGORY_SYSTEM)
                .build();
        notificationManagerCompat.notify(1,notification);
    }
}
