package com.grocers.hub;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

/*Application class to initialize all the required features at the application level*/
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication BaseApplication = new BaseApplication();
    public static final String TAG = "GrocersHub";

    public static BaseApplication getInstance() {
        return BaseApplication;
    }

    /**
     * Returns the application context
     *
     * @return application context
     */

    public static Context getContext() {
        return BaseApplication.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplication = this;
    }
}