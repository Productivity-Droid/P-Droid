package com.techov8.p_droid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;


import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.techov8.p_droid.AUTO_REPLY.activity.settings.SettingsActivity;
import com.techov8.p_droid.AUTO_REPLY.model.utils.Constants;
import com.techov8.p_droid.SCHEDULE.Activity.SmsSchedulerPreferenceActivity;

import static android.content.ContentValues.TAG;

public class MenuActivity extends AppCompatActivity {

    private AdView mAdView;

    private String valuee;

    private boolean adswitch;

    private ConstraintLayout SettingsconstraintLayout, AboutusconstraintLayout, ContactusconstraintLayout, FaqconstraintLayout, SuggestconstraintLayout;
    private TextView Appversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        ///////////////////////////////////////////////////////////////firebase to control add
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        try {
            db.collection("ADS").document("Banner").addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot valuee, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                    adswitch = valuee.getBoolean("MenuActivity");

                    ///////////////////////////////////////////////////////////////addmob

                    MobileAds.initialize(MenuActivity.this, new OnInitializationCompleteListener() {
                        @Override
                        public void onInitializationComplete(InitializationStatus initializationStatus) {
                        }
                    });


                    mAdView = findViewById(R.id.adView);
                    AdRequest adRequest = new AdRequest.Builder().build();


                    if (adswitch==true) {
                        mAdView.setVisibility(View.VISIBLE);


                        mAdView.loadAd(adRequest);

                    }


                    mAdView.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            // Code to be executed when an ad finishes loading.

                            super.onAdLoaded();

                            // Toast.makeText(MainActivity.this, "loaded", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {
                            // Code to be executed when an ad request fails.

                            super.onAdFailedToLoad(adError);
                            mAdView.loadAd(adRequest);

                            // Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
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


                    /////////////////////////////////////////////////////////////


                    Log.e("hhh", "ijfhyd" + adswitch);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
////////////////////////////////////////////////////////////////////////////////////////////////

        setTitle("Menu");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);


            // Define ColorDrawable object and parse color
            // using parseColor method
            // with color hash code as its parameter
            ColorDrawable colorDrawable
                    = new ColorDrawable(Color.parseColor("#30D5C8"));

            //Set BackgroundDrawable
            actionBar.setBackgroundDrawable(colorDrawable);

        }


        SettingsconstraintLayout = findViewById(R.id.SettingconstraintLayout);
        AboutusconstraintLayout = findViewById(R.id.aboutusconstraintLayout);
        ContactusconstraintLayout = findViewById(R.id.contactusconstraintLayout);
        FaqconstraintLayout = findViewById(R.id.faqconstraintLayout);
        SuggestconstraintLayout = findViewById(R.id.suggestusconstraintLayout);
        Appversion = findViewById(R.id.appversiontextview);


        SettingsconstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////startActivityForResult(new Intent(getApplicationContext(), SmsSchedulerPreferenceActivity.class), 1);

                Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        AboutusconstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();

                String url = "https://super-cadence.github.io/Cadence.github.io/#about";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        FaqconstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();

                String url = "https://super-cadence.github.io/Cadence.github.io/#sign-up";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        /*ContactusconstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

         */

        SuggestconstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();


                try {

                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setType("plain/text");
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.EMAIL_ADDRESS});
                    intent.putExtra(Intent.EXTRA_SUBJECT, Constants.EMAIL_SUBJECT);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        ////////////////////////////////////App version


        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = info.versionName;
            // int versionCode=info.versionCode;

            Appversion.setText(versionName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ///////////////////////////////////////////////////


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