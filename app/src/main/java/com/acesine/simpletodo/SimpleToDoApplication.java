package com.acesine.simpletodo;

import android.app.Application;

import com.acesine.simpletodo.utils.TypefaceUtil;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class SimpleToDoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Aleo-Bold.otf");
    }
}
