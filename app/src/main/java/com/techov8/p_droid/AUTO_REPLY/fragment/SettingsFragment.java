package com.techov8.p_droid.AUTO_REPLY.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.techov8.p_droid.R;
import com.techov8.p_droid.AUTO_REPLY.activity.advancedsettings.AdvancedSettingsActivity;
import com.techov8.p_droid.AUTO_REPLY.model.utils.AutoStartHelper;
import com.techov8.p_droid.AUTO_REPLY.model.utils.ServieUtils;

public class SettingsFragment extends PreferenceFragmentCompat {
    private SwitchPreference showNotificationPref, foregroundServiceNotifPref;
    private Preference advancedPref;
    private Preference autoStartPref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey);

        showNotificationPref = findPreference(getString(R.string.pref_show_notification_replied_msg));
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            showNotificationPref.setTitle(getString(R.string.show_notification_label) + "(Beta)");
        }

        autoStartPref = findPreference(getString(R.string.pref_auto_start_permission));
        autoStartPref.setOnPreferenceClickListener(preference -> {
            checkAutoStartPermission();
            return true;
        });

        advancedPref = findPreference(getString(R.string.key_pref_advanced_settings));
        advancedPref.setOnPreferenceClickListener(preference -> {
            Intent advancedSettings = new Intent(getActivity(), AdvancedSettingsActivity.class);
            getActivity().startActivity(advancedSettings);
            return false;
        });

        foregroundServiceNotifPref = findPreference(getString(R.string.pref_show_foreground_service_notification));
        foregroundServiceNotifPref.setOnPreferenceChangeListener((preference, newValue) -> {
            if(newValue.equals(true)){
                ServieUtils.getInstance(getActivity()).startNotificationService();
            }else{
                ServieUtils.getInstance(getActivity()).stopNotificationService();
            }
            return true;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.settings);
    }

    private void checkAutoStartPermission() {
        if(getActivity() != null) {
            AutoStartHelper.getInstance().getAutoStartPermission(getActivity());
        }
    }

}
