package com.techov8.p_droid.SCHEDULE;

public class SmsSentReceiver extends WakefulBroadcastReceiver {

    @Override
    protected Class getServiceClass() {
        return SmsSentService.class;
    }
}
