package com.del.dnews.helper;

import android.os.AsyncTask;
import android.content.Context;
import com.del.dnews.util.MainUtils;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.BufferedReader;
import java.net.URL;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.io.IOException;
import org.json.JSONException;
import com.del.dnews.util.HttpHandler;
import com.kaopiz.kprogresshud.KProgressHUD;

public class HttpTaskLoader extends AsyncTask<String, String, String> {

    private Context con;
    private HttpCallBack mCallBackNew;
    private KProgressHUD hud;
    public HttpTaskLoader(Context con, HttpCallBack mCallBackNews){
        this.con = con;
        this.mCallBackNew = mCallBackNews;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(MainUtils.isNetworkAvailable(con)){
            hud = new KProgressHUD(con);
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
            hud.show();
        }else{
            mCallBackNew.onError("No connection");
        }
        
    }

    @Override
    protected String doInBackground(String... params) {
        HttpHandler sh = new HttpHandler();

        String jsonStr = sh.makeServiceCall(params[0]);

		return jsonStr;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(hud != null && hud.isShowing()){
            mCallBackNew.onResponse(result);
            hud.dismiss();
        }
    }
    
    public interface HttpCallBack{
        void onResponse(String result);
        void onError(String error);
    }
}
