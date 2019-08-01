package com.example.personalapplication;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        SQLiteDatabase db = Connector.getDatabase();
    }
}
