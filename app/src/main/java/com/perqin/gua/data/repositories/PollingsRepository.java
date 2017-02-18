package com.perqin.gua.data.repositories;

import com.perqin.gua.data.models.PollingModel;
import com.perqin.gua.data.retrofit.PollingService;
import com.perqin.gua.data.retrofit.PostPollingReq;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public class PollingsRepository implements IPollingsRepository {
    private static PollingsRepository sInstance;

    private PollingService mService;

    public static PollingsRepository getInstance() {
        if (sInstance == null) {
            sInstance = new PollingsRepository();
        }
        return sInstance;
    }

    private PollingsRepository() {
        mService = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PollingService.BASE_URL)
                .build().create(PollingService.class);
    }

    @Override
    public Observable<PollingModel> startPolling(String studentId, String cookie, String service, String clientToken) {
        PostPollingReq req = new PostPollingReq();
        req.cookie = cookie;
        req.service = service;
        req.clientToken = clientToken;
        return mService.startPolling(studentId, req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Void> stopPolling(String studentId, String cookie) {
        return mService.stopPolling(studentId, cookie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<PollingModel> getPolling(String studentId, String cookie) {
        return mService.getPolling(studentId, cookie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
