package com.del.dnews.activity;

import android.os.Bundle;
import com.del.dnews.R;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import com.del.dnews.util.MainUtils;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.graphics.Bitmap;
import android.content.res.Resources;
import com.unity3d.ads.IUnityAdsListener;
import com.del.dnews.util.LogUtils;
import com.unity3d.ads.UnityAds;
import android.view.Menu;
import android.view.MenuItem;

public class ViewContentActivity extends BaseActivity {
    
    
    private Toolbar toolbar;
    private String subtitle, authorName;
    private WebView webContent;
    private ProgressBar progressBar;
    
    private static final String DESKTOP_USER_AGENT = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/63.0";
    private static final String MOBILE_USER_AGENT = "Mozilla/5.0 (Linux; Android 4.0.4;Galaxy Nexus Build/IMM76B)AppleWebKit/535.19 (KHTML, like Gecko)Chrome/18.0.1025.133 MobileSafari/535.19";
    
    private String contentURL = "https://www.google.com";
    
    private boolean TESTMODE_UNITY_ADS = false;
    private String unityGameID = "3994707";
    
    private String Unity_INTER = "InterstitialAds";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_content);
        initViews();
        initLogic();
        initListeners();
        MainUtils.changeActivityFont(this, "sans_medium");
    }

    @Override
    public void initViews() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        webContent =(WebView)findViewById(R.id.web_content);
        progressBar =(ProgressBar)findViewById(R.id.progress);
        
    }

    @Override
    public void initLogic() {
        //Choose Mobile/Desktop client.
        //WebSettings webSettings = webContent.getSettings();
        //webSettings.setUserAgentString(DESKTOP_USER_AGENT);
        progressBar.setMax(100);
        subtitle = getIntent().getStringExtra("title");
        contentURL = getIntent().getStringExtra("url");
        authorName = getIntent().getStringExtra("author");
        
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(authorName);
        getSupportActionBar().setSubtitle(subtitle);
        
        webContent.getSettings().setJavaScriptEnabled(true);
        webContent.getSettings().setLoadWithOverviewMode(true);
        webContent.getSettings().setUseWideViewPort(true);
        webContent.getSettings().setUserAgentString(MOBILE_USER_AGENT);
        webContent.getSettings().setAppCacheMaxSize(5*1024*1024); 
        webContent.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath()); 
        webContent.getSettings().setAllowFileAccess(true);
        webContent.getSettings().setAppCacheEnabled(true);
        webContent.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webContent.getSettings().setDomStorageEnabled(true);
        webContent.getSettings().setSaveFormData(true);
        
        webContent.loadUrl(contentURL);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        webContent.setWebViewClient(new WebViewClient());
        webContent.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    progressBar.setProgress(progress);
                    if(progress==100) progressBar.setVisibility(View.GONE);
                }
        });
        
        final UnityAdsListener myAdsListener = new UnityAdsListener();
        UnityAds.initialize(this, unityGameID, myAdsListener, TESTMODE_UNITY_ADS);
        

    }

    @Override
    public void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if(webContent.canGoBack()){
                        webContent.goBack();
                    }else{
                        finish();
                        if (UnityAds.isReady(Unity_INTER)) {
                            UnityAds.show(ViewContentActivity.this,Unity_INTER);
                        }
                    }
                    
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_content_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sharing){
            MainUtils.shareUrl(ViewContentActivity.this, subtitle, authorName, contentURL);
          }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(webContent.canGoBack()){
            webContent.goBack();
            progressBar.setVisibility(View.VISIBLE);
        } else{
            super.onBackPressed();
            if (UnityAds.isReady(Unity_INTER)) {
                UnityAds.show(ViewContentActivity.this,Unity_INTER);
            }
        }
        
    }
    
    public class UnityAdsListener implements IUnityAdsListener {

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
            MainUtils.showToast(ViewContentActivity.this, "Thank you for supporting ❤️");
            LogUtils.i(placementId, "onUnityAdsFinish()");
        }
        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
            LogUtils.e("onUnityAdsError", message +" "+ error);
        }
    }
    
    
    
}
