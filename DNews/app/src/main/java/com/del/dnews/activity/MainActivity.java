package com.del.dnews.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.del.dnews.R;
import androidx.cardview.widget.CardView;
import com.del.dnews.util.MainUtils;
import com.del.dnews.Interfaces.CallBackApi;
import com.del.dnews.util.SPUtils;
import com.del.dnews.helper.CountryCodeTask;
import com.del.dnews.helper.ConstantsNews;
import com.del.dnews.helper.ReadJsonNews;
import com.del.dnews.Interfaces.CallbackNews;
import java.util.List;
import com.del.dnews.model.ModelNews;
import org.json.JSONObject;
import org.json.JSONException;
import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.io.InputStreamReader;
import org.json.JSONArray;
import java.util.ArrayList;
import android.content.DialogInterface;
import com.del.dnews.helper.HttpTaskLoader;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    

    private Toolbar toolbar;
    private CardView cardGeneral, cardBusiness, cardEntertainment, cardHealth, cardSports, cardTechnology;
    private SPUtils sp;
    private String countryCodeUser;
    
    private List<ModelNews> modelNews = new ArrayList<>();
    
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
    }

    @Override
    public void initLogic() {
        sp = new SPUtils(MainActivity.this);
        try {
            countryCodeUser = sp.loadStringFromSharedPref("countryCode").toLowerCase();
        } catch (Exception e) {
            
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        MainUtils.changeActivityFont(this, "sans_regular");
        
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
            case R.id.card_business:
                startActivity(new Intent(this, BusinessActivity.class));
            break;
            
            case R.id.card_general:
                startActivity(new Intent(this, GeneralActivity.class));
            break;
            
            case R.id.card_entertainment:
                startActivity(new Intent(this, EntertainmentActivity.class));
            break;
            
            case R.id.card_sports:
                startActivity(new Intent(this, SportsActivity.class));
            break;
            
            case R.id.card_health:
                startActivity(new Intent(this, HealthActivity.class));
            break;
            
            case R.id.card_technology:
                startActivity(new Intent(this, TechnologyActivity.class));
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
    
}
