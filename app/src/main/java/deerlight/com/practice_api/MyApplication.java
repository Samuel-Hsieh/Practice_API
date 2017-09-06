package deerlight.com.practice_api;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by samuel_hsieh on 2017/9/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
