package com.techov8.p_droid.AUTO_REPLY.activity.main;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.techov8.p_droid.R;
import com.techov8.p_droid.AUTO_REPLY.activity.BaseActivity;
import com.techov8.p_droid.AUTO_REPLY.viewmodel.SwipeToKillAppDetectViewModel;

public class MainAutoReplyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_autoreply);

        setTitle("Auto reply");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#30D5C8"));

        //Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        SwipeToKillAppDetectViewModel viewModel = new ViewModelProvider(this).get(SwipeToKillAppDetectViewModel.class);
    }

    /////////////////////////////////back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}