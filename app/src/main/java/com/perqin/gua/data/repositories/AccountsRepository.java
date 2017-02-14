package com.perqin.gua.data.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public class AccountsRepository {
    private static final String PK_APP_STUDENT_ID = "APP_STUDENT_ID";
    private static AccountsRepository sInstance;
    private final SharedPreferences mSharedPreferences;

    public static AccountsRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AccountsRepository(context);
        }
        return sInstance;
    }

    private AccountsRepository(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public String getStudentId() {
        return mSharedPreferences.getString(PK_APP_STUDENT_ID, "");
    }

    public Observable<String> saveStudentId(final String studentId) {
//        mSharedPreferences.edit()
//                .putString(PK_APP_STUDENT_ID, studentId).apply();
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                mSharedPreferences.edit()
                        .putString(PK_APP_STUDENT_ID, studentId).apply();
//                JPushInterface.setAlias(App.context, studentId, new TagAliasCallback() {
//                    @Override
//                    public void gotResult(int responseCode, String alias, Set<String> tags) {
                        if (!subscriber.isUnsubscribed()) {
//                            if (responseCode == 0) {
//                                subscriber.onNext(alias);
                                subscriber.onCompleted();
//                            } else {
//                                subscriber.onError(new RuntimeException("[JPushInterface.setAlias] Error code: " + responseCode));
//                            }
                        }
//                    }
//                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
