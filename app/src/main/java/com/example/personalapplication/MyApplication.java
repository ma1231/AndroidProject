package com.example.personalapplication;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private static List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        SQLiteDatabase db = Connector.getDatabase();
    }

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }
}
