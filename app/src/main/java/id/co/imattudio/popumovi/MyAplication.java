package id.co.imattudio.popumovi;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by idn on 12/10/2017.
 */

public class MyAplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Stetho.initializeWithDefaults(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    public static Context getContext(){
        return mContext;
    }
}