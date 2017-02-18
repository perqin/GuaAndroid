package com.perqin.gua.utils;

import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;
import com.perqin.gua.data.C;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * Author   : perqin
 * Date     : 17-2-18
 */

public final class PushServiceUtils {
    private PushServiceUtils() {
        // Prevent construction
    }

    public static String getClientToken(Context context, String service) {
        switch (service) {
            case C.SERVICE_FCM:
                return FirebaseInstanceId.getInstance().getToken();
            case C.SERVICE_MIPUSH:
                return MiPushClient.getRegId(context);
            case C.SERVICE_JPUSH:
                return "";
            default:
                return "";
        }
    }
}
