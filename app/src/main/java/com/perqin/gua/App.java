package com.perqin.gua;

import android.app.Application;
import android.content.Context;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public class App extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
    }
}
