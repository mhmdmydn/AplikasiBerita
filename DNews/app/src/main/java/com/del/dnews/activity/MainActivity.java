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

public class MainActivity extends BaseActivity  {

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
        cardGeneral = (CardView)findViewById(R.id.card_general);
        cardEntertainment = (CardView)findViewById(R.id.card_entertainment);
        cardHealth = (CardView)findViewById(R.id.card_health);
        cardSports = (CardView)findViewById(R.id.card_sports);
        cardTechnology = (CardView)findViewById(R.id.card_technology);
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
    
        cardGeneral.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1) {
                    String URL = ConstantsNews.BaseUrl + countryCodeUser + ConstantsNews.ApiKey;        
                    MainUtils.showDialog(MainActivity.this, URL, new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface p1, int p2) {
                                p1.dismiss();
                            }
                        });
                   }
            });
            
        cardBusiness.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1) {
                    try {
                        MainUtils.showToast(MainActivity.this, sp.loadStringFromSharedPref("countryCode").toLowerCase());
                    } catch (Exception e) {}
                }
            });
            
        cardEntertainment.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1) {
                }
            });
            
        cardHealth.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1) {
                }
            });
            
        cardSports.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1) {
                }
            });
            
        cardTechnology.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1) {
                }
            });        
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
