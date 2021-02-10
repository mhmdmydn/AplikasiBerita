package com.del.dnews.fragment;

import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.preference.Preference;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.del.dnews.R;
import androidx.appcompat.app.AppCompatDelegate;
import com.del.dnews.util.MainUtils;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.Intent;
import com.del.dnews.activity.SettingActivity;
import androidx.core.content.IntentCompat;
import android.content.ComponentName;
import com.del.dnews.activity.MainActivity;
import android.app.Activity;
import android.preference.SwitchPreference;
import com.del.dnews.App;
import android.content.DialogInterface;

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    
    private static String NIGHT_MODE = "dark_theme";
    
    public SettingFragment(){
        
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preferences);
        setUpAppVersion();
        shareApp();
        privacyPolicy();
    }
    
    @Override
    public void onSharedPreferenceChanged(SharedPreferences mPrefs, String key) {
        if(key.equals(NIGHT_MODE)){
            mPrefs.getBoolean(App.getInstance().NIGHT_MODE, true);
        } else{
            mPrefs.getBoolean(App.getInstance().NIGHT_MODE, false);
            
        }
        MainUtils.restart(getActivity());
    }
    
    
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
    
    
    
    private void setUpAppVersion(){
        try {
            PackageManager pm = getActivity().getPackageManager();

            PackageInfo packageInfo = pm.getPackageInfo(getActivity().getPackageName(), 0);

            findPreference("app_version").setSummary(packageInfo.versionName);

        } catch (PackageManager.NameNotFoundException e) {

        }
    }
    
    private void shareApp(){
        findPreference("share_app").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                @Override
                public boolean onPreferenceClick(Preference p1) {
                    MainUtils.shareApplication(getActivity());
                    
                    return true;
                }
            });
    }
    
    private void privacyPolicy(){
        findPreference("privacy_policy").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                @Override
                public boolean onPreferenceClick(Preference p1) {
                    MainUtils.showPrivacyPolicy(getActivity(), "Privacy Policy", "file:///android_asset/privacy_policy.html", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p2) {
                                p1.dismiss();
                            }
                        });
                    return true;
                }
            });
    }
}
