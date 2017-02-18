package com.perqin.gua.data.fcm;


import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.perqin.gua.data.PushScoresReceiver;

public class GuaFirebaseMessagingService extends FirebaseMessagingService {
    private static final String KEY_NEW_SCORE = "scores";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().containsKey(KEY_NEW_SCORE)) {
            Intent intent = new Intent(PushScoresReceiver.ACTION_PUSH_SCORES);
            intent.putExtra(PushScoresReceiver.EXTRA_JSON, remoteMessage.getData().get(KEY_NEW_SCORE));
            sendBroadcast(intent);
        }
    }
}
