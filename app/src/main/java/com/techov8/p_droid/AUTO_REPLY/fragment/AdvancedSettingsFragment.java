package com.techov8.p_droid.AUTO_REPLY.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.techov8.p_droid.R;
import com.techov8.p_droid.AUTO_REPLY.activity.contactselector.ContactSelectorActivity;
import com.techov8.p_droid.AUTO_REPLY.model.utils.ContactsHelper;

public class AdvancedSettingsFragment extends PreferenceFragmentCompat {

    private Preference advancedPref;
    private SwitchPreference enable_contact_replies_preference;
    private ContactsHelper contactsHelper;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_advanced_settings, rootKey);

        contactsHelper = ContactsHelper.getInstance(getContext());

        enable_contact_replies_preference = findPreference(getString(R.string.pref_reply_contacts));
        enable_contact_replies_preference.setOnPreferenceChangeListener((preference, newValue) -> {
            if ((Boolean) newValue) {
                if (contactsHelper.hasContactPermission())
                    return true;
                else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        contactsHelper.requestContactPermission(getActivity());
                    }
                    return false;
                }
            }
            return true;
        });

        advancedPref = findPreference(getString(R.string.key_pref_select_contacts));
        advancedPref.setOnPreferenceClickListener(preference -> {
            if (contactsHelper.hasContactPermission())
                startActivity(new Intent(getActivity(), ContactSelectorActivity.class));
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    contactsHelper.requestContactPermission(getActivity());
                }
            }
            return true;
        });
    }
}
