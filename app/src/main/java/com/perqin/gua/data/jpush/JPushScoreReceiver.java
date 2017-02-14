package com.perqin.gua.data.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public class JPushScoreReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        String action = intent.getAction();
//        if (action.equals(JPushInterface.ACTION_MESSAGE_RECEIVED)) {
//            String jsonMessage = intent.getExtras().getString(JPushInterface.EXTRA_MESSAGE);
//            Log.i("EXTRA", jsonMessage);
//            ScoreModel scoreModel = new Gson().fromJson(jsonMessage, ScoreModel.class);
//            ScoresRepository.getInstance(context).saveNewScore(scoreModel);
//        }
    }
}
