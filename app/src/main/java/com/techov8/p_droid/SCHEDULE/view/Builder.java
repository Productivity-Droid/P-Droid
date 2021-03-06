package com.techov8.p_droid.SCHEDULE.view;

import android.view.View;

import com.techov8.p_droid.SCHEDULE.Activity.AddSmsActivity;
import com.techov8.p_droid.SCHEDULE.SmsModel;


public abstract class Builder {

    protected View view;
    protected SmsModel sms;
    protected AddSmsActivity activity;

    abstract protected View getView();
    abstract public View build();

    public Builder setView(View view) {
        this.view = view;
        return this;
    }

    public Builder setSms(SmsModel sms) {
        this.sms = sms;
        return this;
    }

    public Builder setActivity(AddSmsActivity activity) {
        this.activity = activity;
        return this;
    }
}
