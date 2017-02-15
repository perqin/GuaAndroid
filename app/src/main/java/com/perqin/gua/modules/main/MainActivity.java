package com.perqin.gua.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.perqin.gua.R;
import com.perqin.gua.data.repositories.AccountsRepository;
import com.perqin.gua.data.repositories.PollingsRepository;
import com.perqin.gua.data.repositories.ScoresRepository;
import com.perqin.gua.modules.auth.AuthActivity;
import com.perqin.gua.modules.claim.ClaimActivity;

import retrofit2.adapter.rxjava.HttpException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_ACCEPT_CLAIM = 1;

    private ScoresRecyclerAdapter mAdapter;
    private boolean mLoading;
    private boolean mPollingStarted;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;

    private void checkPollingState(String studentId) {
        setLoading(true);
        PollingsRepository.getInstance().getPolling(studentId).subscribe(pollingModel -> {
            setLoading(false);
            setPollingStarted(true);
        }, throwable -> {
            setLoading(false);
            setPollingStarted(false);
            if (!(throwable instanceof HttpException && ((HttpException) throwable).code() == 404)) {
                Toast.makeText(MainActivity.this, "Exception: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void stopPolling(String studentId) {
        setLoading(true);
        // Stop it
        PollingsRepository.getInstance().stopPolling(studentId).subscribe(aVoid -> {
            setLoading(false);
            setPollingStarted(false);
        }, throwable -> {
            setLoading(false);
            // Set to stopped if this polling not found
            if (throwable instanceof HttpException && ((HttpException) throwable).code() == 404) {
                setPollingStarted(false);
            }
            Toast.makeText(MainActivity.this, "Exception: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    private void startNewPolling() {
        startActivity(new Intent(this, AuthActivity.class));
    }

    private void setLoading(boolean loading) {
        mLoading = loading;
        // TODO: Add animation
    }

    private void setPollingStarted(boolean started) {
        mPollingStarted = started;
        if (started) {
            mFab.setImageResource(R.drawable.ic_stop_black_24dp);
        } else {
            mFab.setImageResource(R.drawable.ic_start_black_24dp);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (!AccountsRepository.getInstance(this).isAcceptingClaim()) {
            startActivityForResult(new Intent(this, ClaimActivity.class), REQUEST_ACCEPT_CLAIM);
        }

        mAdapter = new ScoresRecyclerAdapter();
        mLoading = false;

        String studentId = AccountsRepository.getInstance(this).getStudentId();
        mToolbar.setTitle(studentId.isEmpty() ? getString(R.string.app_name) : studentId);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String studentId = AccountsRepository.getInstance(this).getStudentId();
        if (!studentId.isEmpty()) {
            mAdapter.reloadScores(ScoresRepository.getInstance(this).getScores(studentId));
        }
        if (!studentId.isEmpty() && !mLoading) {
            checkPollingState(studentId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ACCEPT_CLAIM) {
            if (!AccountsRepository.getInstance(this).isAcceptingClaim()) {
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab) {
            if (mLoading) return;

            if (mPollingStarted) {
                stopPolling(AccountsRepository.getInstance(this).getStudentId());
            } else {
                startNewPolling();
            }
        }
    }
}
