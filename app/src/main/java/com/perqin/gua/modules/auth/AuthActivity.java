package com.perqin.gua.modules.auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.perqin.gua.R;
import com.perqin.gua.data.repositories.AccountsRepository;
import com.perqin.gua.data.repositories.PollingsRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthActivity extends AppCompatActivity {
    private static final String WJW_URL = "http://wjw.sysu.edu.cn";

    private void getCookiesAndDone() {
        String cookie = CookieManager.getInstance().getCookie(WJW_URL);
        String studentId = grepStudentId(cookie);
        String clientToken = AccountsRepository.getInstance(this).getClientToken();
        if (cookie.isEmpty() || studentId.isEmpty()) {
            Toast.makeText(this, R.string.fail_to_get_cookies_or_student_id, Toast.LENGTH_SHORT).show();
        } else {
            AccountsRepository.getInstance(this).saveStudentId(studentId);
            PollingsRepository.getInstance().startPolling(studentId, cookie, clientToken).subscribe(pollingModel -> {
                Toast.makeText(AuthActivity.this, R.string.start_polling, Toast.LENGTH_SHORT).show();
                finish();
            }, throwable -> Toast.makeText(AuthActivity.this, "Exception: " + throwable.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private String grepStudentId(String cookie) {
        Pattern pattern = Pattern.compile(".*sno=([0-9]{8}).*");
        Matcher matcher = pattern.matcher(cookie);
        if (matcher.matches() && matcher.groupCount() > 0) {
            return matcher.group(1);
        } else {
            return "";
        }
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
            getCookiesAndDone();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
