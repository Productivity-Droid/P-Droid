package com.techov8.p_droid.SCHEDULE.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.techov8.p_droid.MainActivity;
import com.techov8.p_droid.SCHEDULE.DbHelper;
import com.techov8.p_droid.R;
import com.techov8.p_droid.SCHEDULE.Scheduler;


import com.techov8.p_droid.SCHEDULE.SmsModel;
import com.techov8.p_droid.SCHEDULE.view.BuilderCancel;
import com.techov8.p_droid.SCHEDULE.view.BuilderContact;
import com.techov8.p_droid.SCHEDULE.view.BuilderDate;
import com.techov8.p_droid.SCHEDULE.view.BuilderMessage;
import com.techov8.p_droid.SCHEDULE.view.BuilderRecurringMode;
import com.techov8.p_droid.SCHEDULE.view.BuilderSimCard;
import com.techov8.p_droid.SCHEDULE.view.BuilderTime;
import com.techov8.p_droid.SCHEDULE.view.EmptinessTextWatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

public class AddSmsActivity extends AppCompatActivity {

    private AdView mAdView;




    final public static int RESULT_SCHEDULED = 1;
    final public static int RESULT_UNSCHEDULED = 2;
    final public static String INTENT_SMS_ID = "INTENT_SMS_ID";

    final private static String SMS_STATE = "SMS_STATE";
    final private static int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    final private String[] permissionsRequired = new String[] {
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_CONTACTS
    };

    private SmsModel sms;
    private ArrayList<String> permissionsGranted = new ArrayList<>();

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    */




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //startActivityForResult(new Intent(this, SmsSchedulerPreferenceActivity.class), 1);
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionsGranted()) {
            buildForm();
        }
    }

    private void buildForm() {
        EditText formMessage = findViewById(R.id.form_input_message);
        AutoCompleteTextView formContact = findViewById(R.id.form_input_contact);
        TextWatcher watcherEmptiness = new EmptinessTextWatcher(this, formContact, formMessage);
        formContact.addTextChangedListener(watcherEmptiness);
        formMessage.addTextChangedListener(watcherEmptiness);

        new BuilderMessage().setView(formMessage).setSms(sms).build();
        new BuilderContact().setView(formContact).setSms(sms).setActivity(this).build();

        new BuilderSimCard().setActivity(this).setView(findViewById(R.id.form_sim_card)).setSms(sms).build();
        new BuilderRecurringMode()
            .setRecurringDayView((Spinner) findViewById(R.id.form_recurring_day))
            .setRecurringMonthView((Spinner) findViewById(R.id.form_recurring_month))
            .setDateView((DatePicker) findViewById(R.id.form_date))
            .setActivity(this)
            .setView(findViewById(R.id.form_recurring_mode))
            .setSms(sms)
            .build()
        ;

        new BuilderTime().setActivity(this).setView(findViewById(R.id.form_time)).setSms(sms).build();
        new BuilderDate().setActivity(this).setView(findViewById(R.id.form_date)).setSms(sms).build();

        new BuilderCancel().setView(findViewById(R.id.button_cancel)).setSms(sms).build();
    }

    @Override
    protected void onPause() {
        super.onPause();
        DbHelper.closeDbHelper();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DbHelper.closeDbHelper();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sms);


        setTitle("Create Schedule");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#30D5C8"));

        //Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);


        ////////////////////////////////////////////////////////////////////////////adds

        /*

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.

                super.onAdLoaded();

               // Toast.makeText(AddSmsActivity.this,"loaded",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.

                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);

               // Toast.makeText(AddSmsActivity.this,"error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.

                super.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.

                super.onAdClicked();
                //////yaha 2-3 bar click hone pr visibility gone
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

         */
        /////////////////////////////////////////////////////////////





        long smsId = getIntent().getLongExtra(INTENT_SMS_ID, 0L);
        if (smsId > 0) {
            sms = DbHelper.getDbHelper(this).get(smsId);
        } else if (null != savedInstanceState) {
            sms = savedInstanceState.getParcelable(SMS_STATE);
        }
        if (null == sms) {
            sms = new SmsModel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != sms) {
            outState.putParcelable(SMS_STATE, sms);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (null == sms) {
            sms = savedInstanceState.getParcelable(SMS_STATE);
        }
        if (null == sms) {
            sms = new SmsModel();
        }
    }

    public void scheduleSms(View view) {
        if (!validateForm()) {
            return;
        }
        sms.setStatus(SmsModel.STATUS_PENDING);
        DbHelper.getDbHelper(this).save(sms);
        new Scheduler(getApplicationContext()).schedule(sms);
        setResult(RESULT_SCHEDULED, new Intent());
        finish();
    }

    public void unscheduleSms(View view) {
        DbHelper.getDbHelper(this).delete(sms.getTimestampCreated());
        new Scheduler(getApplicationContext()).unschedule(sms.getTimestampCreated());
        setResult(RESULT_UNSCHEDULED, new Intent());
        finish();
    }

    private boolean validateForm() {
        boolean result = true;
        if (sms.getTimestampScheduled() < GregorianCalendar.getInstance().getTimeInMillis()) {
            Toast.makeText(getApplicationContext(), getString(R.string.form_validation_datetime), Toast.LENGTH_SHORT).show();
            result = false;
        }
        if (TextUtils.isEmpty(sms.getRecipientNumber())) {
            ((AutoCompleteTextView) findViewById(R.id.form_input_contact)).setError(getString(R.string.form_validation_contact));
            result = false;
        }
        if (TextUtils.isEmpty(sms.getMessage())) {
            ((EditText) findViewById(R.id.form_input_message)).setError(getString(R.string.form_validation_message));
            result = false;
        }
        return result;
    }

    private boolean permissionsGranted() {
        boolean granted = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissionsNotGranted = new ArrayList<>();
            for (String required : this.permissionsRequired) {
                if (checkSelfPermission(required) != PackageManager.PERMISSION_GRANTED) {
                    permissionsNotGranted.add(required);
                } else {
                    this.permissionsGranted.add(required);
                }
            }
            if (permissionsNotGranted.size() > 0) {
                granted = false;
                String[] notGrantedArray = permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]);
                requestPermissions(notGrantedArray, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
        }
        return granted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                List<String> requiredPermissions = Arrays.asList(this.permissionsRequired);
                String permission;
                for (int i = 0; i < permissions.length; i++) {
                    permission = permissions[i];
                    if (requiredPermissions.contains(permission) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        this.permissionsGranted.add(permission);
                    }
                }
                if (this.permissionsGranted.size() == this.permissionsRequired.length) {
                    buildForm();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
