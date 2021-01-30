package com.del.dnews.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.CheckBox;
import android.widget.Button;
import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AlertDialog;

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
}
