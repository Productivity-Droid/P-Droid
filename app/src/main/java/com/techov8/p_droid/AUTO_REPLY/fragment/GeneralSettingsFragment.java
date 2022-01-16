package com.techov8.p_droid.AUTO_REPLY.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.techov8.p_droid.R;
import com.techov8.p_droid.AUTO_REPLY.activity.main.MainAutoReplyActivity;
import com.techov8.p_droid.AUTO_REPLY.model.preferences.PreferencesManager;

public class GeneralSettingsFragment extends PreferenceFragmentCompat {
    private ListPreference languagePref;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_general_settings, rootKey);

        languagePref = findPreference(getString(R.string.key_pref_app_language));
        languagePref.setOnPreferenceChangeListener((preference, newValue) -> {
            String thisLangStr = PreferencesManager.getPreferencesInstance(getActivity()).getSelectedLanguageStr(null);
            if(thisLangStr == null || !thisLangStr.equals(newValue)){
                //switch app language here
                //Should restart the app for language change to take into account
                restartApp();
            }
            return true;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.preference_category_general_label);
    }

    private void restartApp() {
        Intent intent = new Intent(getActivity(), MainAutoReplyActivity.class);
        getActivity().startActivity(intent);
        getActivity().finishAffinity();
    }
}
