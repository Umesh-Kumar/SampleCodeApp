package com.samplecodeapp;

import android.app.Application;
import android.content.Context;

public class CodeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

}
