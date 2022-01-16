package com.techov8.p_droid.AUTO_REPLY.model.utils;

import android.content.Context;
import android.os.Build;
import android.service.notification.StatusBarNotification;

import androidx.annotation.RequiresApi;

import com.techov8.p_droid.AUTO_REPLY.model.CustomRepliesData;
import com.techov8.p_droid.AUTO_REPLY.model.logs.AppPackage;
import com.techov8.p_droid.AUTO_REPLY.model.logs.MessageLog;
import com.techov8.p_droid.AUTO_REPLY.model.logs.MessageLogsDB;

public class DbUtils {
    private Context mContext;
    private CustomRepliesData customRepliesData;

    public DbUtils(Context context){
        mContext = context;
    }

    public long getNunReplies(){
        MessageLogsDB messageLogsDB = MessageLogsDB.getInstance(mContext.getApplicationContext());
        return messageLogsDB.logsDao().getNumReplies();
    }

    public void purgeMessageLogs(){
        MessageLogsDB messageLogsDB = MessageLogsDB.getInstance(mContext.getApplicationContext());
        messageLogsDB.logsDao().purgeMessageLogs();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void logReply(StatusBarNotification sbn, String title){
        customRepliesData = CustomRepliesData.getInstance(mContext);
        MessageLogsDB messageLogsDB = MessageLogsDB.getInstance(mContext.getApplicationContext());
        int packageIndex = messageLogsDB.appPackageDao().getPackageIndex(sbn.getPackageName());
        if(packageIndex <= 0){
            AppPackage appPackage = new AppPackage(sbn.getPackageName());
            messageLogsDB.appPackageDao().insertAppPackage(appPackage);
            packageIndex = messageLogsDB.appPackageDao().getPackageIndex(sbn.getPackageName());
        }
        MessageLog logs = new MessageLog(packageIndex, title, sbn.getNotification().when, customRepliesData.getTextToSendOrElse(null), System.currentTimeMillis());
        messageLogsDB.logsDao().logReply(logs);
    }

    public long getLastRepliedTime(String packageName, String title){
        MessageLogsDB messageLogsDB = MessageLogsDB.getInstance(mContext.getApplicationContext());
        return messageLogsDB.logsDao().getLastReplyTimeStamp(title, packageName);
    }

    public long getFirstRepliedTime(){
        MessageLogsDB messageLogsDB = MessageLogsDB.getInstance(mContext.getApplicationContext());
        return messageLogsDB.logsDao().getFirstRepliedTime();
    }
}
