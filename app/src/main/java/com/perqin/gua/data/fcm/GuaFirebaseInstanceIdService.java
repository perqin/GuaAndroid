package com.perqin.gua.data.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Author   : perqin
 * Date     : 17-2-15
 */

public class GuaFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "FCMService";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "onTokenRefresh: Refreshed token : " + token);
        // TODO: Send it to server
    }
}
