package com.del.dnews.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.del.dnews.App;
import androidx.appcompat.app.AppCompatDelegate;
import com.del.dnews.R;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

public abstract class BaseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.setting_preferences, true);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(settings.getBoolean("dark_theme", true)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            
        }
		super.onCreate(savedInstanceState);
	}
	
	public abstract void initViews();
	public abstract void initLogic();
	public abstract void initListeners();
}
