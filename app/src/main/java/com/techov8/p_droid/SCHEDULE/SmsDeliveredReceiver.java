package com.techov8.p_droid.SCHEDULE;

public class SmsDeliveredReceiver extends WakefulBroadcastReceiver {

    @Override
    protected Class getServiceClass() {
        return SmsDeliveredService.class;
    }
}
