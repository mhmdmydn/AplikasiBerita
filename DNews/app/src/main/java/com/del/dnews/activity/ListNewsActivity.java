package com.del.dnews.activity;

import com.del.dnews.R;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import java.util.List;
import com.del.dnews.model.ModelNews;
import java.util.ArrayList;
import com.del.dnews.util.MainUtils;
import com.del.dnews.api.HttpTaskLoader;
import com.del.dnews.api.Constants;
import com.del.dnews.adapter.AdapterNews;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.animation.AnimationSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;

public class ListNewsActivity extends BaseActivity {
    
    private Toolbar toolbar;
    private RecyclerView recyclerViewList;
    private String getNumber;
    
    private List<ModelNews> modelNews = new ArrayList<>();
    private AdapterNews adapterNews;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);
        initViews();
        initLogic();
        initListeners();
        MainUtils.changeActivityFont(this, "sans_medium");
    }
    
    @Override
    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerViewList = findViewById(R.id.rv_general);
    }

    @Override
    public void initLogic() {
        getNumber = getIntent().getStringExtra("number");
        
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(MainUtils.getTitleCategory((int)Double.parseDouble(getNumber)));
        getSupportActionBar().setSubtitle("Total news : " + 0);
        
        new HttpTaskLoader(ListNewsActivity.this, new HttpTaskLoader.HttpCallBack(){

                @Override
                public void onResponse(String result) {
                    try {
                        modelNews = MainUtils.jsonParseData(result);
                        MainUtils.sortNameByDesc(modelNews);
                        adapterNews = new AdapterNews(ListNewsActivity.this, modelNews);
                        recyclerViewList.setLayoutManager(new LinearLayoutManager(ListNewsActivity.this));
                        recyclerViewList.setHasFixedSize(true);
                        recyclerViewList.setAdapter(adapterNews);
                        MainUtils.setAnimation(recyclerViewList);
                        adapterNews.notifyDataSetChanged();
                        getSupportActionBar().setSubtitle("Total news : " + modelNews.size());
                    } catch (Exception e) {
                        MainUtils.showToast(ListNewsActivity.this, e.toString());
                    }
                }

                @Override
                public void onError(String error) {
                    MainUtils.showToast(ListNewsActivity.this, error);
                }
            }).execute(MainUtils.setUrlNews(ListNewsActivity.this,(int)Double.parseDouble(getNumber)));
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
