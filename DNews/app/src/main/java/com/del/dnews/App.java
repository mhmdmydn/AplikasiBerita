package com.del.dnews;

import com.del.dnews.R;
import com.del.dnews.util.CrashHandler;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.app.Activity;

public class App extends Application {
    
    public static String NIGHT_MODE = "dark_theme";
    private Boolean isNightModeEnabled = false;
    private static App mApp = null;
    private SharedPreferences mPrefs;
    
    public static App getInstance() {
        if (mApp == null) {
            mApp = new App();
        }
        return mApp;
    }
    
	@Override
	public void onCreate() {
		super.onCreate();
        mApp = this;
        CrashHandler.init(mApp);
        
        //PreferenceManager.setDefaultValues(this, R.xml.setting_preferences, false);
        //this.isNightModeEnabled = mPrefs.getBoolean(NIGHT_MODE, false);
	}
    
    public boolean isNightModeEnabled() {
        return isNightModeEnabled;
    }

    public void setIsNightModeEnabled(SharedPreferences mPrefs , boolean isNightModeEnabled) {
        this.isNightModeEnabled = isNightModeEnabled;
        this.mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(NIGHT_MODE, isNightModeEnabled);
        editor.apply();
    }
}
