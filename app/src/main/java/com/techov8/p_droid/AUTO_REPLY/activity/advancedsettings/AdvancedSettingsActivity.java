package com.techov8.p_droid.AUTO_REPLY.activity.advancedsettings;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;

import com.techov8.p_droid.R;
import com.techov8.p_droid.AUTO_REPLY.activity.BaseActivity;

public class AdvancedSettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_settings);

        ActionBar actionBar = getSupportActionBar();

        setTitle(R.string.advanced_settings);
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#30D5C8"));

        //Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
    }
}
