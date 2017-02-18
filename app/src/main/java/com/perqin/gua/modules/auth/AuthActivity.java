package com.perqin.gua.modules.auth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.perqin.gua.R;
import com.perqin.gua.data.C;
import com.perqin.gua.data.repositories.AccountsRepository;
import com.perqin.gua.data.repositories.PollingsRepository;
import com.perqin.gua.utils.PushServiceUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class AuthActivity extends AppCompatActivity implements PollingOptionsDialogFragment.PollingOptionsDialogListener {
    private static final String WJW_URL = "http://wjw.sysu.edu.cn";

    private ProgressDialog mProgressDialog;

    private String grepStudentId(String cookie) {
        Pattern pattern = Pattern.compile(".*sno=([0-9]{8}).*");
        Matcher matcher = pattern.matcher(cookie);
        if (matcher.matches() && matcher.groupCount() > 0) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    private void startPollingAndDone(String studentId, String cookie, String service) {
        String clientToken = PushServiceUtils.getClientToken(this, service);
        PollingsRepository.getInstance().startPolling(studentId, cookie, service, clientToken).subscribe(pollingModel -> {
            AccountsRepository ar = AccountsRepository.getInstance(AuthActivity.this);
            ar.saveStudentId(studentId);
            ar.saveCookie(cookie);
            ar.savePushService(service);
            mProgressDialog.dismiss();
            Toast.makeText(AuthActivity.this, R.string.start_polling, Toast.LENGTH_SHORT).show();
            finish();
        }, throwable -> Toast.makeText(AuthActivity.this, "Exception: " + throwable.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_done_black_24dp);
        }

        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(WJW_URL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            PollingOptionsDialogFragment.newInstance().show(getSupportFragmentManager(), "PollingOptionsDialogFragment");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AuthActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onStartClick(PollingOptionsDialogFragment.PollingOptions options) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.starting_polling));
        mProgressDialog.show();

        String cookie = CookieManager.getInstance().getCookie(WJW_URL);
        String studentId = grepStudentId(cookie);
        String service = options.getPushService();
        if (cookie.isEmpty() || studentId.isEmpty()) {
            Toast.makeText(this, R.string.fail_to_get_cookies_or_student_id, Toast.LENGTH_SHORT).show();
        } else if (service.equals(C.SERVICE_FCM)) {
            // Try to choose FCM
            if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
                // Available, proceed
                startPollingAndDone(studentId, cookie, C.SERVICE_FCM);
            } else {
                mProgressDialog.dismiss();
                Toast.makeText(this, R.string.valid_google_play_service_is_not_available_on_your_device, Toast.LENGTH_SHORT).show();
            }
        } else if (service.equals(C.SERVICE_MIPUSH)) {
            // Request permission and proceed
            AuthActivityPermissionsDispatcher.getPermissionAndStartPollingAndDoneWithCheck(this, studentId, cookie, C.SERVICE_MIPUSH);
        }
    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    protected void getPermissionAndStartPollingAndDone(String studentId, String cookie, String service) {
        startPollingAndDone(studentId, cookie, service);
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    protected void showMiPushRequirePermissionToast() {
        Toast.makeText(this, R.string.permissions_are_required_by_mipush_sdk, Toast.LENGTH_SHORT).show();
    }
}
