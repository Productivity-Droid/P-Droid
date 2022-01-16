package com.techov8.p_droid.AUTO_REPLY.activity.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.techov8.p_droid.BuildConfig
import com.techov8.p_droid.R
import com.techov8.p_droid.AUTO_REPLY.activity.BaseActivity
import com.techov8.p_droid.databinding.ActivityAboutBinding
import com.techov8.p_droid.AUTO_REPLY.viewmodel.SwipeToKillAppDetectViewModel

class AboutActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.about)

        val viewModel = ViewModelProvider(this).get(SwipeToKillAppDetectViewModel::class.java)

        binding.appVersion.text = String.format(resources.getString(R.string.app_version), BuildConfig.VERSION_NAME)
        binding.privacyPolicyCardView.setOnClickListener {
          /*  val url = getString(R.string.url_privacy_policy);
            val i = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
            startActivity(i)

           */
        }
        binding.developerLink.setOnClickListener {
            /*val url = getString(R.string.url_adeekshith_twitter)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)

             */
        }
    }
}