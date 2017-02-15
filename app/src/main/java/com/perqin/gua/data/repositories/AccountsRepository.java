package com.perqin.gua.data.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public class AccountsRepository {
    private static final String PK_APP_STUDENT_ID = "APP_STUDENT_ID";
    private static final String PK_APP_CLIENT_TOKEN = "APP_CLIENT_TOKEN";
    private static final String PK_APP_ACCEPT_CLAIM = "APP_ACCEPT_CLAIM";

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

    public void saveStudentId(String studentId) {
        mSharedPreferences.edit()
                .putString(PK_APP_STUDENT_ID, studentId).apply();
    }

    public String getClientToken() {
        return mSharedPreferences.getString(PK_APP_CLIENT_TOKEN, "");
    }

    public void saveClientToken(String token) {
        mSharedPreferences.edit()
                .putString(PK_APP_CLIENT_TOKEN, token).apply();
    }

    public void acceptClaim() {
        mSharedPreferences.edit()
                .putBoolean(PK_APP_ACCEPT_CLAIM, true).apply();
    }

    public boolean isAcceptingClaim() {
        return mSharedPreferences.getBoolean(PK_APP_ACCEPT_CLAIM, false);
    }
}
