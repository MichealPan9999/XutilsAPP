package com.example.panzq.xutilsapp;

import android.app.Application;

import org.xutils.x;

/**
 * Created by panzq on 2017/6/5.
 */
public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//初始化XUtils
        x.Ext.setDebug(BuildConfig.DEBUG);//是否输出debug日志
    }
}
