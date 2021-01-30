package com.del.dnews;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import com.del.dnews.activity.DebugActivity;
import com.del.dnews.helper.CountryCodeTask;
import com.del.dnews.Interfaces.CallBackApi;
import org.json.JSONObject;
import org.json.JSONException;
import com.del.dnews.util.MainUtils;
import com.del.dnews.util.SPUtils;
//import com.google.android.gms.ads.MobileAds;

public class App extends Application {
    
	private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private SPUtils sp;
    
	@Override
	public void onCreate() {

		//MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

		this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread thread, Throwable ex) {
					Intent intent = new Intent(getApplicationContext(), DebugActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					intent.putExtra("error", getStackTrace(ex));
					PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 11111, intent, PendingIntent.FLAG_ONE_SHOT);
					AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
					am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, pendingIntent);
					android.os.Process.killProcess(android.os.Process.myPid());
					System.exit(2);
					uncaughtExceptionHandler.uncaughtException(thread, ex);
				}
			});
		super.onCreate();
        sp = new SPUtils(getApplicationContext());
        
        new CountryCodeTask(getApplicationContext(), new CallBackApi(){

                @Override
                public void onReceiveJsonApi(String result) {
                    try {
                        JSONObject obj = new JSONObject(result);
                        String country = obj.getString("countryCode");
                        sp.saveToSharedPref("countryCode", country);
                    } catch (JSONException e) {
                        
                    } catch (Exception e) {
                        
                    }
                }

                @Override
                public void onError(String error) {
                    
                }
            }).execute("http://ip-api.com/json/");
        
	}
	private String getStackTrace(Throwable th) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		Throwable cause = th;
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		final String stacktraceAsString = result.toString();
		printWriter.close();
		return stacktraceAsString;
	}
}
