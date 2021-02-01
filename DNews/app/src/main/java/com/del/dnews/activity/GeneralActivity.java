package com.del.dnews.activity;

import com.del.dnews.R;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.del.dnews.util.SPUtils;
import java.util.List;
import com.del.dnews.model.ModelNews;
import java.util.ArrayList;
import com.del.dnews.util.MainUtils;
import com.del.dnews.helper.HttpTaskLoader;
import com.del.dnews.helper.ConstantsNews;
import com.del.dnews.adapter.AdapterNews;
import androidx.recyclerview.widget.LinearLayoutManager;

public class GeneralActivity extends BaseActivity {
    
    private Toolbar toolbar;
    private RecyclerView rvGeneral;
    private SPUtils sp;
    private String countryCodeUser;

    private List<ModelNews> modelNews = new ArrayList<>();
    private AdapterNews adapterNews;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        initViews();
        initLogic();
        initListeners();
    }
    
    @Override
    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        rvGeneral = findViewById(R.id.rv_general);
    }

    @Override
    public void initLogic() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        sp = new SPUtils(GeneralActivity.this);
        try {
            countryCodeUser = sp.loadStringFromSharedPref("countryCode").toLowerCase();
        } catch (Exception e) {
            MainUtils.showToast(GeneralActivity.this, e.toString());
        }
        String URL = ConstantsNews.BaseUrl + countryCodeUser + ConstantsNews.ApiKey;
        new HttpTaskLoader(GeneralActivity.this, new HttpTaskLoader.HttpCallBack(){

                @Override
                public void onResponse(String result) {
                    try {
                        modelNews = MainUtils.jsonParseData(result);
                        adapterNews = new AdapterNews(GeneralActivity.this, modelNews);
                        rvGeneral.setLayoutManager(new LinearLayoutManager(GeneralActivity.this));
                        rvGeneral.setHasFixedSize(true);
                        rvGeneral.setAdapter(adapterNews);
                        adapterNews.notifyDataSetChanged();
                    } catch (Exception e) {
                        MainUtils.showToast(GeneralActivity.this, e.toString());
                    }
                }

                @Override
                public void onError(String error) {
                    MainUtils.showToast(GeneralActivity.this, error);
                }
            }).execute(URL);
    }

    @Override
    public void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View _v) {
                    onBackPressed();
                }
            });
    }
    
}
