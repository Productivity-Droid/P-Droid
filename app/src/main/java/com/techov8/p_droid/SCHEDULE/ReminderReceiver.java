package com.techov8.p_droid.SCHEDULE;

public class ReminderReceiver extends WakefulBroadcastReceiver {

    @Override
    protected Class getServiceClass() {
        return ReminderService.class;
    }
}
