package com.perqin.gua.data.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.perqin.gua.App;
import com.perqin.gua.data.repositories.AccountsRepository;

/**
 * Author   : perqin
 * Date     : 17-2-15
 */

public class GuaFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "FCMService";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        AccountsRepository.getInstance(App.context).saveClientToken(token);
    }
}
