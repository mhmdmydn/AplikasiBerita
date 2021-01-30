package com.del.dnews.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.del.dnews.Interfaces.CallbackNews;
import com.del.dnews.model.ModelNews;
import com.kaopiz.kprogresshud.KProgressHUD;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import com.del.dnews.util.MainUtils;

public class ReadJsonNews extends AsyncTask<String, String, String> {
    
   private Context con;
   private CallbackNews mCallbackNews;
   private KProgressHUD hud;
   public static String TAG = ReadJsonNews.class.getSimpleName();
   
   public ReadJsonNews(Context con, CallbackNews mCallbackNews){
       this.con = con;
       this.mCallbackNews = mCallbackNews;
   }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        hud = new KProgressHUD(con);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.show();
    }
    
    
    @Override
    protected String doInBackground(String... params) {
        
        HttpURLConnection connection=null;
        InputStream inputStream =null;
        BufferedReader bufferedReader = null;
        String charset = "UTF-8";
        
        
        try {
            URL url=new URL(params[0]);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setConnectTimeout(15000);
            connection.connect();
            inputStream = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String line;
            
            while((line =bufferedReader.readLine()) != null){
                
                sb.append(line + "\n" );
                Log.i("NetworkConnect", "result: " + sb.toString());
                
            }
            
            if(sb.length() == 0){
                return null;
            }
        return sb.toString();
        
        } catch(Exception ex){
                return null;
            }finally{
                if(connection != null){
                    connection.disconnect();
                }
                if(bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(hud != null && hud.isShowing()){
            hud.dismiss();
            mCallbackNews.onReceiveJsonApi(result);
        }
    }
    
    
}
