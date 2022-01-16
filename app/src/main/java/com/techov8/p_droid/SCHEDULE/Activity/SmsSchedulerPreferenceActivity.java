package com.techov8.p_droid.SCHEDULE.Activity;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.techov8.p_droid.R;


public class SmsSchedulerPreferenceActivity extends PreferenceActivity {

    public static final String PREFERENCE_DELIVERY_REPORTS = "PREFERENCE_DELIVERY_REPORTS";
    public static final String PREFERENCE_REMINDERS = "PREFERENCE_REMINDERS";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);



        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#30D5C8"));

    //Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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