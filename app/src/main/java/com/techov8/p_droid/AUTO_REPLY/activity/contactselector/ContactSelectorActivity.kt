package com.techov8.p_droid.AUTO_REPLY.activity.contactselector

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.techov8.p_droid.AUTO_REPLY.activity.BaseActivity
import com.techov8.p_droid.AUTO_REPLY.viewmodel.SwipeToKillAppDetectViewModel
import com.techov8.p_droid.R
import com.techov8.p_droid.databinding.ActivityContactSelectorBinding

class ContactSelectorActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityContactSelectorBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        val actionBar = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#30D5C8"))

        //Set BackgroundDrawable

        //Set BackgroundDrawable
        actionBar!!.setBackgroundDrawable(colorDrawable)
        title = getString(R.string.contact_selector)

        val viewModel = ViewModelProvider(this).get(SwipeToKillAppDetectViewModel::class.java)
    }
}