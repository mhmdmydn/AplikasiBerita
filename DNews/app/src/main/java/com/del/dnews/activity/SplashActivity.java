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
import com.del.dnews.util.DelAppUpdate;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

public class SplashActivity extends BaseActivity {

    private static final String URL ="https://raw.githubusercontent.com/MuhammadMayudin/DNews/main/update.json";
    private AppIconView img;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        initLogic();
        initListeners();
        newUpdate();
    }

    @Override
    public void initViews() {
        img = findViewById(R.id.app_logo);
        text = (TextView)findViewById(R.id.app_name);
    }

    @Override
    public void initLogic() {
        MainUtils.changeActivityFont(this, "sans_regular");
        
    }

    @Override
    public void initListeners() {
        
    }
    
    private void newUpdate(){
        new DelAppUpdate(SplashActivity.this, new DelAppUpdate.UpdateListener(){

                @Override
                public void onResponse(int newVersion, String changeLog,final String UrlDownload) {
                    if (DelAppUpdate.getCurrentVersionCode(SplashActivity.this) < newVersion) {
                        new AlertDialog.Builder(SplashActivity.this)
                            .setTitle("Update available")
                            .setMessage(changeLog)
                            .setCancelable(false)
                            .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DelAppUpdate.startDownloadApp(SplashActivity.this, UrlDownload);
                                }
                            })
                            .show();
                    } else{
                        //Action no update
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
                }

                @Override
                public void onError(String error) {
                    if(error =="No Connection"){
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
                }
            }).execute(URL);
    }
}
