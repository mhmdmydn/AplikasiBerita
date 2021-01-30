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
import com.del.dnews.Interfaces.CallBackApi;

public class CountryCodeTask extends AsyncTask<String, String, String> {
    
    private Context con;
    private CallBackApi mCallBackNew;
    
    public CountryCodeTask(Context con, CallBackApi mCallBackNews){
        this.con = con;
        this.mCallBackNew = mCallBackNews;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(!MainUtils.isNetworkAvailable(con)){
            mCallBackNew.onError("No connection");
        }
    }
    
    @Override
    protected String doInBackground(String... params) {
        String json = null;
        HttpURLConnection connection=null;
        InputStream inputStream=null;
		BufferedReader reader=null;
        
        try {
            URL url=new URL(params[0]);
            connection=(HttpURLConnection)url.openConnection();
            connection.connect();
            inputStream=connection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer=new StringBuffer();
            String line="";
            
            while((line=reader.readLine())!=null){
                buffer.append(line);
            }
			json =buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            if(connection!=null)
            {
                connection.disconnect();
            }
            if(reader!=null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
        return json;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mCallBackNew.onReceiveJsonApi(result);
    }
}
