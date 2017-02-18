package com.perqin.gua;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public class App extends Application {
    private static final String APP_ID = "";
    private static final String APP_KEY = "";
    private static final String TAG = "GUA";

    @Override
    public void onCreate() {
        super.onCreate();

        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }

        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String s) {
                // Ignore
            }

            @Override
            public void log(String s) {
                Log.i(TAG, s);
            }

            @Override
            public void log(String s, Throwable throwable) {
                Log.i(TAG, s, throwable);
            }
        };
        Logger.setLogger(this, newLogger);
    }

    private boolean shouldInit() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
