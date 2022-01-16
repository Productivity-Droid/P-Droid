package com.techov8.p_droid.FOCUS_TREE.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;

import androidx.core.app.NotificationCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.techov8.p_droid.FOCUS_TREE.db.AppDatabase;
import com.techov8.p_droid.FOCUS_TREE.db.entity.Skill;
import com.techov8.p_droid.FOCUS_TREE.db.entity.Task;
import com.techov8.p_droid.MainActivity;
import com.techov8.p_droid.MenuActivity;
import com.techov8.p_droid.R;
import com.google.gson.Gson;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import static android.content.ContentValues.TAG;

public class SkillCountActivity extends AppCompatActivity {

    int k = 0;

    private boolean adswitch;


    boolean completedd = false;

    private AdView mAdView;

    CircularProgressBar progressBar;
    ImageView ivSCStage;
    int[] stagesPic;
    CountDownTimer mainTimer;
    long selectedTime, mainTimerCount = 0, MIN_TIME = 5 * 60 * 1000;
    Skill skill;
    TextView tvSCTime, tvMusicMessage;
    String skillAsString, skillname;
    MediaPlayer mediaPlayer;
    boolean music = false;
    ImageButton ibtnMusic;
    Button btnAbort;
    ListView listView;
    AlertDialog musicListDialog;
    int selectedMusic = 0;
    int musicList[];
    String musicNameList[];
    boolean unExpectedPause = true;
    AppDatabase db;
    NotificationCompat.Builder notification;
    private static final int UNIQUE = 12121212;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_count);


        skillname = getIntent().getStringExtra("skillname");

        setTitle(skillname);
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

        ///////////////////////////////////////////////////////////////firebase to control add
        FirebaseFirestore Fdbb = FirebaseFirestore.getInstance();


        try {
            Fdbb.collection("ADS").document("Banner").addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot valuee, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                    adswitch = valuee.getBoolean("SkillCountActivity");

                    ///////////////////////////////////////////////////////////////addmob

                    MobileAds.initialize(SkillCountActivity.this, new OnInitializationCompleteListener() {
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


        ///////////////////////////////////


        //get reference to controls
        tvSCTime = findViewById(R.id.tvSCTime);
        ivSCStage = findViewById(R.id.ivSCStage);
        progressBar = findViewById(R.id.progressBar);
        ibtnMusic = findViewById(R.id.ibtnMusic);
        btnAbort = findViewById(R.id.btnAbort);
        tvMusicMessage = findViewById(R.id.tvMusicMessage);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "20hours")
                .allowMainThreadQueries()
                .build();


        ///////////////////////////////////


        stagesPic = new int[]{R.drawable.s1, R.drawable.s2, R.drawable.s3, R.drawable.s4, R.drawable.s5};
        ivSCStage.setImageResource(stagesPic[0]);

        /////////////////////////////////////

        //get skill selected_time
        Gson gson = new Gson();
        skillAsString = getIntent().getStringExtra("skill");

        skill = gson.fromJson(skillAsString, Skill.class);
        selectedTime = getIntent().getLongExtra("selectedTime", MIN_TIME);
        tvMusicMessage.setVisibility(View.INVISIBLE);

        listView = new ListView(this);
        musicNameList = new String[]{"Forest", "Cricket Chirping", "Ocean Waves"};
        musicList = new int[]{R.raw.rainforest, R.raw.cricket, R.raw.ocean_waves};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, musicNameList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMusic = i;
                setMusic(selectedMusic);
            }
        });

        //ibtnMusic Longpress
        ibtnMusic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showMusicListDialog();
                return false;
            }
        });

        ibtnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (music) {
                    mediaPlayer.stop();
                    ibtnMusic.setImageResource(R.drawable.ic_baseline_notifications_off_24);
                    music = false;
                } else {
                    setMusic(selectedMusic);
                    ibtnMusic.setImageResource(R.drawable.ic_baseline_notifications_active_24);
                    music = true;
                    tvMusicMessage.setText("{" + musicNameList[selectedMusic] + "} Longpress for others music");
                    tvMusicMessage.setVisibility(View.VISIBLE);
                    new CountDownTimer(2000, 1000) {
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            tvMusicMessage.setVisibility(View.INVISIBLE);
                            this.cancel();
                        }
                    }.start();
                }
            }
        });

        //btnAbort
        btnAbort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (completedd == true) {


                    goBack();

                } else {
                    showCancelTimerDialog();

                }

            }
        });
        startMainTimer();

        //notificataion
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
    }

    public void setMusic(int musicId) {
        if (mediaPlayer != null) mediaPlayer.stop(); //stop if music playing
        mediaPlayer = MediaPlayer.create(getApplicationContext(), musicList[musicId]);
        mediaPlayer.setLooping(true);
        //mediaPlayer.start();
        music = true;
        ibtnMusic.setImageResource(R.drawable.ic_baseline_notifications_active_24);
    }

    public void startMainTimer() {
        mainTimer = new CountDownTimer(selectedTime, 1000) {
            @Override
            public void onTick(long l) {
                mainTimerCount += 1000;
                // int progress = 100 - (int)((l*100)/selectedTime);

                k++;


                try {

                    int progress = (int) ((int) k * 100 / (selectedTime / 1000));
                    progressBar.setProgress(progress);

                    ///////////////////////
                    formatTimeAndShow(l);

                    //////////////////////////////////

                    // Toast.makeText(SkillCountActivity.this, "jj" + k, Toast.LENGTH_SHORT).show();


                    if (progress == 1) {
                        ivSCStage.setImageResource(stagesPic[0]);
                    } else if (progress == 20) {
                        ivSCStage.setImageResource(stagesPic[1]);
                    } else if (progress == 40) {
                        ivSCStage.setImageResource(stagesPic[2]);
                    } else if (progress == 60) {
                        ivSCStage.setImageResource(stagesPic[3]);
                    } else if (progress >= 95) {
                        ivSCStage.setImageResource(stagesPic[4]);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                //////////////////////////////////////

            }

            @Override
            public void onFinish() {
                //store skill task in database and set skilltask as true
                stopMainTimer();


                completedd = true;

                try {
                    Thread.sleep(4000);
                    btnAbort.setText("Completed");

                    if (music) {
                        mediaPlayer.start();


                    }

                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }


            }
        }.start();
    }

    public void stopMainTimer() {
        mainTimer.cancel();
        skill.setTimeSpent(skill.getTimeSpent() + mainTimerCount);
        db.skillDao().update(skill);
        Task task = new Task(skill.getId(), System.currentTimeMillis(), selectedTime, mainTimerCount);
        db.taskDao().insertAll(task);
    }

    public void formatTimeAndShow(long millis) {

        int seconds = (int) millis / (1000);
        int h = seconds / 3600;
        int rem = seconds % 3600;
        int m = rem / 60;
        rem = rem % 60;
        int s = rem;
        String hr = Integer.toString(h), min = Integer.toString(m), sec = Integer.toString(s);
        if (h <= 9) hr = "0" + Integer.toString(h);
        if (m <= 9) min = "0" + Integer.toString(m);
        if (s <= 9) sec = "0" + Integer.toString(s);
        tvSCTime.setText(hr + ":" + min + ":" + sec);
    }

    private void showCancelTimerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to abort ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        stopMainTimer();
                        if (music) mediaPlayer.stop();
                        goBack();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
    }

    private void showMusicListDialog() {
        AlertDialog.Builder builder = null;
        if (musicListDialog == null) {
            builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setMessage("Select the music you want to listen to");
            builder.setPositiveButton("Ok", null);
            builder.setView(listView);
            musicListDialog = builder.create();
            musicListDialog.show();
        } else {
            musicListDialog.show();
        }
    }

    private void goBack() {
        unExpectedPause = false;
        if (music) {
            mediaPlayer.stop();
        }
        Intent intent = new Intent(getApplicationContext(), SkillActivity.class);
        intent.putExtra("skill", skillAsString);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {


        if (completedd == true) {


            goBack();

        } else {
            showCancelTimerDialog();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!unExpectedPause) return;
       /* if (music) {
            mediaPlayer.stop();
           ibtnMusic.setImageResource(R.drawable.ic_baseline_notifications_off_24);
        }

        */
        notification.setPriority(Notification.PRIORITY_MAX);
        notification.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
        notification.setTicker("This is a ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Return to to yor work.");
        notification.setContentText("Your plant is going to die.");
//      pattern(stop for 0 millis, vibrate for 1000, sleep for 1000, vibfrate for 500, slep...)
        notification.setVibrate(new long[]{0, 1000, 1000, 500, 1000, 1000});
        Intent intent = new Intent(this, SkillCountActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(UNIQUE, notification.build());
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
