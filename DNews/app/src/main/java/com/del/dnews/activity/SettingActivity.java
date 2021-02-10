package com.del.dnews.activity;

import android.os.Bundle;
import com.del.dnews.fragment.SettingFragment;
import androidx.appcompat.widget.Toolbar;
import com.del.dnews.R;
import android.view.View.OnClickListener;
import android.view.View;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.content.Intent;
import com.del.dnews.App;

public class SettingActivity extends BaseActivity {
    
    private Toolbar toolbar;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        initLogic();
        initListeners();
        
    }
    
    @Override
    public void initViews() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        
    }

    @Override
    public void initLogic() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
            .replace(R.id.setting_content, new SettingFragment())
            .commit();
    }

    @Override
    public void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
