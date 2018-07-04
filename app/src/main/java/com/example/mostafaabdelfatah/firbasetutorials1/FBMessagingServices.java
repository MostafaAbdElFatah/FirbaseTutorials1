package com.example.mostafaabdelfatah.firbasetutorials1;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FBMessagingServices extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0 ){
            Map<String,String> payload = remoteMessage.getData();
            showNotification(payload);
        }else
            showNotification(remoteMessage);
    }

    private void showNotification(Map<String, String> payload) {
        String content = "User id:"+payload.get("id")+
                "\nname:"+payload.get("name")+"\nage:"+payload.get("age")+
                "\naddress:"+payload.get("address");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(payload.get("name"))
                .setContentText(content);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }
    private void showNotification(RemoteMessage message) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Notification From Server")
                .setContentText(message.getNotification().getBody());
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }
}
