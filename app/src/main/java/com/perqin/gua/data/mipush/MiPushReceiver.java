package com.perqin.gua.data.mipush;

import android.content.Context;
import android.content.Intent;

import com.perqin.gua.data.PushScoresReceiver;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/**
 * Author   : perqin
 * Date     : 17-2-18
 */

public class MiPushReceiver extends PushMessageReceiver {
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        Intent intent = new Intent(PushScoresReceiver.ACTION_PUSH_SCORES);
        intent.putExtra(PushScoresReceiver.EXTRA_JSON, miPushMessage.getContent());
        context.sendBroadcast(intent);
    }
}
