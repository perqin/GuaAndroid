package com.perqin.gua.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.perqin.gua.data.models.SyncModel;
import com.perqin.gua.data.repositories.ScoresRepository;
import com.perqin.gua.utils.NotificationUtils;

public class PushScoresReceiver extends BroadcastReceiver {
    public static final String ACTION_PUSH_SCORES = "com.perqin.gua.PUSH_SCORES";
    public static final String EXTRA_JSON = "JSON";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_PUSH_SCORES.equals(intent.getAction())) {
            String jsonString = intent.getStringExtra(EXTRA_JSON);
            SyncModel syncModel = new Gson().fromJson(jsonString, SyncModel.class);
            ScoresRepository.getInstance(context).syncScores(syncModel);
            NotificationUtils.sendNotificationsFromSync(context, syncModel);
        }
    }
}
