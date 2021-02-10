package com.del.dnews.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.del.dnews.R;
import androidx.cardview.widget.CardView;
import com.del.dnews.util.MainUtils;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import com.unity3d.services.banners.BannerView;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.services.banners.UnityBannerSize;
import com.del.dnews.util.LogUtils;
import android.content.res.Resources;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Handler;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.DialogInterface;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    

    private Toolbar toolbar;
    
    private CardView cardGeneral, cardBusiness, cardEntertainment, cardHealth, cardSports, cardTechnology;
   
    private RelativeLayout baseUnityBanner;
    private BannerView bottomBanner;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initLogic();
        initListeners();
   } 
   
    @Override
    public void initViews() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        cardBusiness = (CardView)findViewById(R.id.card_business);
        cardBusiness.setOnClickListener(this);
        cardGeneral = (CardView)findViewById(R.id.card_general);
        cardGeneral.setOnClickListener(this);
        cardEntertainment = (CardView)findViewById(R.id.card_entertainment);
        cardEntertainment.setOnClickListener(this);
        cardHealth = (CardView)findViewById(R.id.card_health);
        cardHealth.setOnClickListener(this);
        cardSports = (CardView)findViewById(R.id.card_sports);
        cardSports.setOnClickListener(this);
        cardTechnology = (CardView)findViewById(R.id.card_technology);
        cardTechnology.setOnClickListener(this);
        
        baseUnityBanner = (RelativeLayout)findViewById(R.id.base_unity_banner);
        
        
    }

    @Override
    public void initLogic() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setSubtitle(MainUtils.getDayAndDate());
        MainUtils.changeActivityFont(this, "sans_medium");
        
        Resources res = getResources();
        boolean TESTMODE_UNITY_ADS = res.getBoolean(R.bool.TEST_MODE);
        String unityGameID = res.getString(R.string.GAME_ID);
        String Unity_BANNER = res.getString(R.string.BANNER_ID);
        
        final UnityAdsListener myAdsListener = new UnityAdsListener();
        UnityAds.initialize(this, unityGameID, myAdsListener, TESTMODE_UNITY_ADS);
        
        bottomBanner = new BannerView(MainActivity.this, Unity_BANNER, new UnityBannerSize(320, 50));
        baseUnityBanner.addView(bottomBanner);
        bottomBanner.load();
        
}

    @Override
    public void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View _v) {
                    onBackPressed();
                }
         });
    }
    
    @Override
    public void onClick(View v) {
        
        switch(v.getId()){
            case R.id.card_general:
                new Handler().postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            MainUtils.startNews(MainActivity.this, 0);
                        }
                    }, 200);
                
                MainUtils.startButtonAnim(MainActivity.this, cardGeneral);
            break;
            
            case R.id.card_health:
                new Handler().postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            MainUtils.startNews(MainActivity.this, 1);
                        }
                    }, 200);
                
                MainUtils.startButtonAnim(MainActivity.this, cardHealth);
            break;
            
            case R.id.card_business:
                new Handler().postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            MainUtils.startNews(MainActivity.this, 2);
                        }
                    }, 200);
                
                MainUtils.startButtonAnim(MainActivity.this, cardBusiness);
            break;
            
            case R.id.card_sports:
                new Handler().postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            MainUtils.startNews(MainActivity.this, 3);
                        }
                    }, 200);
                
                MainUtils.startButtonAnim(MainActivity.this, cardSports);
            break;
            
            case R.id.card_entertainment:
                new Handler().postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            MainUtils.startNews(MainActivity.this, 4);
                        }
                    }, 200);
                
                MainUtils.startButtonAnim(MainActivity.this, cardEntertainment);
            break;
            
            case R.id.card_technology:
                new Handler().postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            MainUtils.startNews(MainActivity.this, 5);
                        }
                    }, 200);
                
                MainUtils.startButtonAnim(MainActivity.this, cardTechnology);
            break;
            
           default:
           break;
        }
    }
    

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        
        finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    public static class UnityAdsListener implements IUnityAdsListener {

        @Override
        public void onUnityAdsReady(String placementId) {
            LogUtils.i(placementId, "onUnityAdsReady()");
        }
        @Override
        public void onUnityAdsStart(String placementId) {
            LogUtils.i(placementId, "onUnityAdsStart()");
        }
        @Override
        public void onUnityAdsFinish(String placementId, UnityAds.FinishState finishState) {
            LogUtils.i(placementId, "onUnityAdsFinish()");
        }
        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
            LogUtils.e("onUnityAdsError", message +" "+ error);
        }
    }
    
    
}
