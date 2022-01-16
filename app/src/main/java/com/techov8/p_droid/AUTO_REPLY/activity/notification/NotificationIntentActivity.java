package com.techov8.p_droid.AUTO_REPLY.activity.notification;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.techov8.p_droid.R;
import com.techov8.p_droid.AUTO_REPLY.activity.BaseActivity;
import com.techov8.p_droid.AUTO_REPLY.activity.main.MainAutoReplyActivity;
import com.techov8.p_droid.AUTO_REPLY.model.utils.NotificationHelper;

public class NotificationIntentActivity extends BaseActivity {

    private static final String TAG = NotificationIntentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_intent_activity); //dummy layout

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.getString("package") != null)
            {
                String packageName = extras.getString("package");
                NotificationHelper.getInstance(getApplicationContext()).markNotificationDismissed(packageName);
                launchApp(packageName);
            }else{
                launchHomeScreen();
            }
        }
    }

    private void launchApp(String packageName){
        Intent intent;
        PackageManager pm = getPackageManager();

        intent = pm.getLaunchIntentForPackage(packageName);

        // ToDo: Getting null intent sometimes when service restart is implemented #291 (Google Play report)
        if (intent == null) {
            // Toast.makeText("Unable to open application").show();
            return;
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);

        finish();
    }

    private void launchHomeScreen(){
        Intent intent = new Intent(this, MainAutoReplyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
