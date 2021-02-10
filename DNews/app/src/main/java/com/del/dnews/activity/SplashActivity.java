package com.del.dnews.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import com.del.dnews.R;
import com.del.dnews.util.MainUtils;
import com.del.dnews.util.AppIconView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.del.dnews.App;
import androidx.appcompat.app.AppCompatDelegate;

public class SplashActivity extends BaseActivity {

    private AppIconView img;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        initLogic();
        initListeners();

    }

    @Override
    public void initViews() {
        img = findViewById(R.id.app_logo);
        text = (TextView)findViewById(R.id.app_name);
    }

    @Override
    public void initLogic() {
        MainUtils.changeActivityFont(this, "sans_regular");

        new Handler().postDelayed(new Runnable(){

                @Override
                public void run() {
                    Intent next = new Intent();
                    next.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(next);
                    finish();
                }
            }, 3000);

    }

    @Override
    public void initListeners() {

    }

}
