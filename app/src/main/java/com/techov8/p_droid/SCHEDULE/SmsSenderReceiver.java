package com.techov8.p_droid.SCHEDULE;

public class SmsSenderReceiver extends WakefulBroadcastReceiver {

    @Override
    protected Class getServiceClass() {
        return SmsSenderService.class;
    }
}
