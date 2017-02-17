package com.perqin.gua.data.fcm;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.perqin.gua.R;
import com.perqin.gua.data.models.ScoreModel;
import com.perqin.gua.data.repositories.ScoresRepository;
import com.perqin.gua.modules.main.MainActivity;
import com.perqin.gua.utils.Md5Utils;

public class GuaFirebaseMessagingService extends FirebaseMessagingService {
    private static final String KEY_NEW_SCORE = "new_score";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().containsKey(KEY_NEW_SCORE)) {
            String jsonString = remoteMessage.getData().get(KEY_NEW_SCORE);
            ScoreModel scoreModel = new Gson().fromJson(jsonString, ScoreModel.class);
            ScoresRepository.getInstance(this).saveNewScore(scoreModel);
            // Create notification
            Intent clickIntent = new Intent(this, MainActivity.class);
            PendingIntent clickPendingIntent = PendingIntent.getActivity(this, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_noti_score_reach_bw)
                    .setContentTitle(getString(R.string.you_got_PH_on_PH, scoreModel.getScore(), scoreModel.getCourseName()))
                    .setContentText(getString(R.string.new_score_coming_out))
                    .setContentIntent(clickPendingIntent);
            int notifyId = Md5Utils.getHashedIntFromString(scoreModel.getCourseId());
            Log.d("NOTIFY", String.valueOf(notifyId));
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(notifyId, builder.build());
        }
    }
}
