package com.example.ylmz.recyclevieweventfinder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_general);

        Preference locationPreference = findPreference("location");
        Preference keyword =findPreference("keyword");
        keyword.setOnPreferenceChangeListener(this);
        locationPreference.setOnPreferenceChangeListener(this);

        Preference catgspref     = (ListPreference)findPreference("categories");

        Preference times=(ListPreference)findPreference("dates");


        SharedPreferences loc_prefs =  PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(locationPreference, loc_prefs.getString(locationPreference.getKey(),""));

        SharedPreferences keywordpref =  PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(keyword, keywordpref.getString(keyword.getKey(),""));



        SharedPreferences times_pref=PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(times,times_pref.getString(times.getKey(),""));
        SharedPreferences catgs_prefs = PreferenceManager
                .getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        onPreferenceChange(catgspref, catgs_prefs.getString(catgspref.getKey(),""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {

        String stringValue = value.toString();
        if (preference instanceof ListPreference) {

            ListPreference listPreference = (ListPreference) preference;
            int            prefIndex      = listPreference.findIndexOfValue(stringValue);

            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
                Log.v( "berbat",(String)listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(stringValue);
        }
        return true;
    }
}
