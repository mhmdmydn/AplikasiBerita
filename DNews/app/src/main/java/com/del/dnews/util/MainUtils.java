package com.del.dnews.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.del.dnews.model.ModelNews;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainUtils {
    
    /**
    * Method change font activity
    */
   public static void changeActivityFont(Activity con, String fontNameAsset){
        String fontName = fontNameAsset.trim();
        if(fontName.contains(".ttf")){
           fontName = fontName.replaceAll(".ttf", "");
        }
        overrideFonts(con, con.getWindow().getDecorView(), fontNameAsset);
    }
    
    private static void overrideFonts(final Activity con, final View v, final String fontName){
        try{
            Typeface activityTypeFace = Typeface.createFromAsset(con.getAssets(), "fonts/" + fontName + ".ttf");
            if((v instanceof ViewGroup)){
                ViewGroup activityFontGroup = (ViewGroup) v;
                for (int i = 0; i < activityFontGroup.getChildCount(); i++) {
                    View child = activityFontGroup.getChildAt(i);
                    overrideFonts(con, child, fontName);
                }
            }
            else {
                if ((v instanceof TextView)) {
                    ((TextView) v).setTypeface(activityTypeFace);
                }
                else {
                    if ((v instanceof EditText )) {
                        ((EditText) v).setTypeface(activityTypeFace);
                    }
                    else {
                        if ((v instanceof Switch )) {
                            ((Switch) v).setTypeface(activityTypeFace);
                        }
                        else {
                            if ((v instanceof CheckBox )) {
                                ((CheckBox) v).setTypeface(activityTypeFace);
                            }
                            else {
                                if ((v instanceof Button)) {
                                    ((Button) v).setTypeface(activityTypeFace);
                                }
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            showToast(con, e.toString());
        }
    } 
    
    /**
    * Method show toast
    */
    public static void showToast(Context con ,String msg){
        Toast.makeText(con, msg, Toast.LENGTH_SHORT).show();
    }
    
    /**
    * check networking
    */
    
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
    
    public static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    
   public static void showDialog(Context con, String msg, DialogInterface.OnClickListener click){
       AlertDialog.Builder builder = new AlertDialog.Builder(con);
       builder.setMessage(msg)
           .setCancelable(false)
           .setPositiveButton("OK", click);
           
       AlertDialog alert = builder.create();
       alert.show();
   }
   
   public static List<ModelNews> jsonParseData(String textJson) throws Exception {
       List<ModelNews> list = new ArrayList<>();
       
       JSONObject obj = new JSONObject(textJson);
       JSONArray arr = obj.getJSONArray("articles");
       for(int i = 0; i < arr.length(); i++){
           JSONObject temp = arr.getJSONObject(i);
           ModelNews mn = new ModelNews();
           mn.setTitle(temp.getString("title"));
           mn.setPublishedAt(convertTimeToText(temp.getString("publishedAt")));
           mn.setUrl(temp.getString("url"));
           mn.setUrlToImage(temp.getString("urlToImage"));
           list.add(mn);
       }
       return list;
   }
   
    public static Bitmap getbmpfromURL(String surl){
        try {
            URL url = new URL(surl);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setDoInput(true);
            urlcon.connect();
            InputStream in = urlcon.getInputStream();
            Bitmap mIcon = BitmapFactory.decodeStream(in);
            return  mIcon;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String convertTimeToText(String dataDate) {

        String convTime = null;

        String prefix = "";
        String suffix = "Ago";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour   = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day  = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                convTime = second + " Seconds " + suffix;
            } else if (minute < 60) {
                convTime = minute + " Minutes "+suffix;
            } else if (hour < 24) {
                convTime = hour + " Hours "+suffix;
            } else if (day >= 7) {
                if (day > 360) {
                    convTime = (day / 360) + " Years " + suffix;
                } else if (day > 30) {
                    convTime = (day / 30) + " Months " + suffix;
                } else {
                    convTime = (day / 7) + " Week " + suffix;
                }
            } else if (day < 7) {
                convTime = day+" Days "+suffix;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;
    }
}
