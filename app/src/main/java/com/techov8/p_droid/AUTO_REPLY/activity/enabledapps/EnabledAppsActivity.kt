package com.techov8.p_droid.AUTO_REPLY.activity.enabledapps

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.techov8.p_droid.R
import com.techov8.p_droid.AUTO_REPLY.activity.BaseActivity

class EnabledAppsActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enabled_apps)

        val actionBar = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#30D5C8"))

        //Set BackgroundDrawable

        //Set BackgroundDrawable
        actionBar!!.setBackgroundDrawable(colorDrawable)
    }
}