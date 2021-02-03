package com.del.dnews.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
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
import android.view.animation.AnimationSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import androidx.recyclerview.widget.RecyclerView;
import com.del.dnews.R;
import com.del.dnews.api.Constants;
import android.content.Intent;
import com.del.dnews.activity.ListNewsActivity;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Collections;

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
           if(temp.getString("author").equals("null")){
               mn.setAuthorName("No Author");
           } else{
               mn.setAuthorName(temp.getString("author"));
           }
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
    
    public static String getCountryCode(Context context){
        return new String(context.getResources().getConfiguration().locale.getCountry());
    }
    
    public static void setAnimation(RecyclerView rvList){
        AnimationSet set = new AnimationSet(true); 
        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f); 
        fadeIn.setDuration(1000); 
        fadeIn.setFillAfter(true); 
        set.addAnimation(fadeIn); 
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.2f); 
        rvList.setLayoutAnimation(controller); 
    }
    
    public static String getTitleCategory(int numCategory){
        
        switch(numCategory){
            case 0:
                return "Headline";
            case 1:
                return "Health";
            case 2:
                return "Business";
            case 3:
                return "Sports";
            case 4:
                return "Entertainment";
            case 5:
                return "Technology";
            default:
                return "Invalid Category";
        }
    }
    
    public static String setUrlNews(Context con, int numCategory){
        
        switch(numCategory){
            case 0:
                return Constants.BASE_URL + getCountryCode(con) + Constants.API_KEY;
            case 1:
                return Constants.BASE_URL + getCountryCode(con) + Constants.HEALTH + Constants.API_KEY;
            case 2:
                return Constants.BASE_URL + getCountryCode(con) + Constants.BUSINESS + Constants.API_KEY;
            case 3:
                return Constants.BASE_URL + getCountryCode(con) + Constants.SPORTS + Constants.API_KEY;
            case 4:
                return Constants.BASE_URL + getCountryCode(con) + Constants.ENTERTAINMENT + Constants.API_KEY;
            case 5:
                return Constants.BASE_URL + getCountryCode(con) + Constants.TECHNOLOGY + Constants.API_KEY;
            default:
                return new String("Invalid URL");
        }
    }
    
    public static void startNews(Activity activity, int setType){
        Intent in = new Intent();
        in.setAction(Intent.ACTION_VIEW);
        in.putExtra("number", String.valueOf(setType));
        in.setClass(activity, ListNewsActivity.class);
        activity.startActivity(in);
    }
    
    public static CharSequence getDayAndDate(){
        Calendar cal = Calendar.getInstance();
        return new SimpleDateFormat("EEE, d MMM yyyy").format(cal.getTime());
    }
    
    public static void sortNameByAsc(List<ModelNews> list){
        Comparator<ModelNews> comparator = new Comparator<ModelNews>() {

            @Override
            public int compare(ModelNews object1, ModelNews object2) {
                return object1.getAuthor().compareToIgnoreCase(object2.getAuthor());
            }
        };
        Collections.sort(list, comparator);
    }
    
    public static void sortNameByDesc(List<ModelNews> list){
        Comparator<ModelNews> comparator = new Comparator<ModelNews>() {

            @Override
            public int compare(ModelNews object1, ModelNews object2) {
                return object2.getAuthor().compareToIgnoreCase(object1.getAuthor());
            }
        };
        Collections.sort(list, comparator);
    }
}
