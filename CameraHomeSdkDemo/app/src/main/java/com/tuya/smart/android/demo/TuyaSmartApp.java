package com.tuya.smart.android.demo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.android.demo.login.activity.LoginActivity;
import com.tuya.smart.android.demo.utils.FrescoManager;
import com.tuya.smart.api.SmartInitializer;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.sdk.TuyaSdk;
import com.tuya.smart.sdk.api.INeedLoginListener;
import com.tuya.smart.wrapper.api.TuyaWrapper;


public class TuyaSmartApp extends MultiDexApplication {

    private static final String TAG = "TuyaSmartApp";

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        L.d(TAG, "onCreate " + getProcessName(this));
        L.setSendLogOn(true);
        TuyaWrapper.init(this);
        TuyaHomeSdk.init(this, BuildConfig.APP_KEY, BuildConfig.APP_SERECT);
        TuyaHomeSdk.setOnNeedLoginListener(new INeedLoginListener() {
            @Override
            public void onNeedLogin(Context context) {
                Intent intent = new Intent(context, LoginActivity.class);
                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                startActivity(intent);
            }
        });
        FrescoManager.initFresco(this);
        TuyaSdk.setDebugMode(true);
    }

    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    private static Context context;

    public static Context getAppContext(){
        return context;
    }


}
