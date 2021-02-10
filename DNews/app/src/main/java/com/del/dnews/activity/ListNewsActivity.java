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
import com.unity3d.services.banners.BannerView;
import android.widget.RelativeLayout;
import com.del.dnews.util.LogUtils;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.services.banners.UnityBannerSize;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Collections;
import android.view.SubMenu;
import android.view.MenuInflater;

public class ListNewsActivity extends BaseActivity {
    
    private Toolbar toolbar;
    private RecyclerView recyclerViewList;
    private String getNumber;
    
    private List<ModelNews> modelNews = new ArrayList<>();
    private AdapterNews adapterNews;
    
    private BannerView bottomBanner;
    private RelativeLayout baseUnityBanner;
    
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
        baseUnityBanner = (RelativeLayout)findViewById(R.id.base_unity_banner);
        
    }

    @Override
    public void initLogic() {
        getNumber = getIntent().getStringExtra("number");
        
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(MainUtils.getTitleCategory((int)Double.parseDouble(getNumber)));
        getSupportActionBar().setSubtitle("Total news : " + 0);
        
        refreshWithSort(0);
        
        Resources res = getResources();
        boolean TESTMODE_UNITY_ADS = res.getBoolean(R.bool.TEST_MODE);
        String unityGameID = res.getString(R.string.GAME_ID);
        String Unity_BANNER = res.getString(R.string.BANNER_ID);
        
        final UnityAdsListener myAdsListener = new UnityAdsListener();
        UnityAds.initialize(this, unityGameID, myAdsListener, TESTMODE_UNITY_ADS);

        bottomBanner = new BannerView(this, Unity_BANNER, new UnityBannerSize(320, 50));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_list_menu, menu);
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.by_title:
                refreshWithSort(0);
                break;
           case R.id.by_author:
                refreshWithSort(1);
                break;
            case R.id.by_date:
                refreshWithSort(2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    
    private static class UnityAdsListener implements IUnityAdsListener {

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
    
   public void refreshWithSort(final int pos){
       new HttpTaskLoader(ListNewsActivity.this, new HttpTaskLoader.HttpCallBack(){

               @Override
               public void onResponse(String result) {
                   try {
                       modelNews = MainUtils.jsonParseData(result);
                       MainUtils.sortByAsc(modelNews, pos);
                       adapterNews = new AdapterNews(ListNewsActivity.this, modelNews);
                       recyclerViewList.setLayoutManager(new LinearLayoutManager(ListNewsActivity.this));
                       MainUtils.setAnimFallDown(ListNewsActivity.this, recyclerViewList);
                       recyclerViewList.setHasFixedSize(true);
                       recyclerViewList.setAdapter(adapterNews);
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
}
