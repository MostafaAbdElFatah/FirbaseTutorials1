package com.example.mostafaabdelfatah.firbasetutorials1;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import static com.example.mostafaabdelfatah.firbasetutorials1.MainActivity.sendRegistrationToServer;

public class FBInstanceIdServices extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.d("msg*****************", "onTokenRefresh: "+recent_token);
        sendRegistrationToServer(recent_token);
    }
}
