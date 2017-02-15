package com.perqin.gua.modules.claim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.perqin.gua.R;
import com.perqin.gua.data.repositories.AccountsRepository;

public class ClaimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);

        Button declineButton = (Button) findViewById(R.id.decline_button);
        declineButton.setOnClickListener(v -> finish());
        Button acceptButton = (Button) findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(v -> {
            AccountsRepository.getInstance(ClaimActivity.this).acceptClaim();
            finish();
        });
    }
}
