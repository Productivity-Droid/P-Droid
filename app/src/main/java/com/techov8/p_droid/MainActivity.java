package com.techov8.p_droid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.techov8.p_droid.AUTO_REPLY.activity.main.MainAutoReplyActivity;
import com.techov8.p_droid.AUTO_REPLY.model.utils.Constants;
import com.techov8.p_droid.SCHEDULE.Activity.SmsListActivity;
import com.techov8.p_droid.FOCUS_TREE.ui.AllSkillsActivity;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;

import static com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE;

public class MainActivity extends AppCompatActivity {

    private Dialog apptobescheduledialog;

    private int REQUEST_CODE = 11;

    ReviewManager manager;
    ReviewInfo reviewInfo;


    private AdView mAdView;

    private boolean adswitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //////////////////////////////////// IN APP UPDATE FEATURES


        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                        result.isUpdateTypeAllowed(FLEXIBLE)) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(result, FLEXIBLE, MainActivity.this, REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        /////////////////////////////////


        LinearLayout linearLayoutautoreply = findViewById(R.id.linearLayoutAutoreply);
        LinearLayout linearLayoutforest = findViewById(R.id.linearLayoutforest);
        LinearLayout linearLayoutrateus = findViewById(R.id.linearLayoutrateus);
        // Toolbar toolbar=findViewById(R.id.toolbarmain);
      LinearLayout linearLayoutschedule = findViewById(R.id.linearLayoutschedule);
        ImageView shareimg = findViewById(R.id.shareimg);
        ImageView menuimg = findViewById(R.id.imageViewmenu);
        TextView privacyText = findViewById(R.id.textViewprivacy);


        ///////////////////////////////////////////////////////////////firebase to control add
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        try {
            db.collection("ADS").document("Banner").addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot valuee, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                    adswitch = valuee.getBoolean("mainActivity");

                    ///////////////////////////////////////////////////////////////addmob

                    MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
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

        //setSupportActionBar(toolbar);
        apptobescheduledialog = new Dialog(this);
        apptobescheduledialog.setContentView(R.layout.app_to_be_schedule_dialog);
        apptobescheduledialog.setCancelable(true);
        apptobescheduledialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ConstraintLayout whatsappbutton = apptobescheduledialog.findViewById(R.id.whatsappbtn);
        ConstraintLayout smsbutton = apptobescheduledialog.findViewById(R.id.SmstoScheduletxt);


        linearLayoutschedule.setOnClickListener(v -> {


            // startActivity(new Intent(MainActivity.this, SmsListActivity.class));



                try {

                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setType("plain/text");
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.EMAIL_ADDRESS});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Idea !");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }





            ///apptobescheduledialog.show();

            //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();

        });

/*
        whatsappbutton.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Comming Soon", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, SmsListActivity.class));
            apptobescheduledialog.dismiss();
            apptobescheduledialog.dismiss();

        });

        smsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(MainActivity.this, SmsSchedulerActivity.class));
                apptobescheduledialog.dismiss();
            }
        });


 */

        linearLayoutrateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 /*

               Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                manager =ReviewManagerFactory.create(MainActivity.this);
                Task<ReviewInfo> request = manager.requestReviewFlow();
                request.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // We can get the ReviewInfo object
                        reviewInfo = task.getResult();
                        Task<Void> flow = manager.launchReviewFlow(MainActivity.this, reviewInfo);
                        flow.addOnCompleteListener(task2 -> {
                            // The flow has finished. The API does not indicate whether the user
                            // reviewed or not, or even whether the review dialog was shown. Thus, no
                            // matter the result, we continue our app flow.
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show(); // There was some problem, continue regardless of the result.
                    }
                });

                  */


                Uri uri = Uri.parse("market://details?id=com.techov8.p_droid");

                Intent marketIntent = new Intent(Intent.ACTION_VIEW, uri);

                marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                }

                try {
                    startActivity(marketIntent);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.techov8.p_droid")));
                }


            }
        });


        linearLayoutforest.setOnClickListener(v -> {


            startActivity(new Intent(MainActivity.this, AllSkillsActivity.class));
            //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();

        });

        linearLayoutautoreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                startActivity(new Intent(MainActivity.this, MainAutoReplyActivity.class));

              //  startActivity(new Intent(MainActivity.this, SmsListActivity.class));


            }
        });

        shareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.techov8.p_droid" + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }

            }
        });

        menuimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, MenuActivity.class));

            }
        });
        privacyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                String url = "https://super-cadence.github.io/Cadence.github.io/#services";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            Toast.makeText(this, "Start Download", Toast.LENGTH_SHORT).show();

            if (resultCode != RESULT_OK) {
                Log.d("UpdateTest", "Update flow failed" + resultCode);
            }
        }
    }
}