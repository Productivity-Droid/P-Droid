package com.techov8.p_droid.SCHEDULE.notification;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

//import com.techov8.p_droid.BuildConfig;
import androidx.multidex.BuildConfig;

import com.techov8.p_droid.R;



@TargetApi(Build.VERSION_CODES.O)
class NotificationBuilderO extends NotificationBuilderJellybean {
    
    public NotificationBuilderO(Context context) {
        super(context);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = manager.getNotificationChannel(BuildConfig.APPLICATION_ID);
        if (null == channel) {
            manager.createNotificationChannel(new NotificationChannel(
                    BuildConfig.APPLICATION_ID,
                    context.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT
            ));
        }
        builder.setChannelId(BuildConfig.APPLICATION_ID);
    }
}
