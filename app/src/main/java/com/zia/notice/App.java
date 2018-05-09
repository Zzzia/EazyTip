package com.zia.notice;

import android.app.Application;

/**
 * Created by zia on 2018/5/6.
 */
public class App extends Application {

    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
