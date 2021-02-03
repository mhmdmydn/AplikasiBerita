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
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdListener;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    

    private Toolbar toolbar;
    
    private CardView cardGeneral, cardBusiness, cardEntertainment, cardHealth, cardSports, cardTechnology;
    
    private AdView adView;

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
        adView = (AdView)findViewById(R.id.adView);
    }

    @Override
    public void initLogic() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setSubtitle(MainUtils.getDayAndDate());
        MainUtils.changeActivityFont(this, "sans_medium");
        
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener(){
                @Override 
                    public void onAdLoaded() {
                        MainUtils.showToast(MainActivity.this, "Banner onAdLoaded()");
                }
                @Override 
                    public void onAdFailedToLoad(int errorCode) {
                        MainUtils.showToast(MainActivity.this, "Banner onAdFailedToLoad()" + errorCode);
                    }
                @Override 
                    public void onAdOpened() {
                        MainUtils.showToast(MainActivity.this, "onAdOpened()");
                    }
                @Override 
                    public void onAdLeftApplication() {
                        MainUtils.showToast(MainActivity.this, "onAdLeftApplication()");
                }
                @Override 
                public void onAdClosed() {
                    MainUtils.showToast(MainActivity.this, "onAdClosed()");
                } 
        });
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
                MainUtils.startNews(MainActivity.this, 0);
            break;
            
            case R.id.card_health:
                MainUtils.startNews(MainActivity.this, 1);
            break;
            
            case R.id.card_business:
                MainUtils.startNews(MainActivity.this, 2);
            break;
            
            case R.id.card_sports:
                MainUtils.startNews(MainActivity.this, 3);
            break;
            
            case R.id.card_entertainment:
                MainUtils.startNews(MainActivity.this, 4);
            break;
            
            case R.id.card_technology:
                MainUtils.startNews(MainActivity.this, 5);
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
                
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
}
