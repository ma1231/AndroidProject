package com.example.personalapplication;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.personalapplication.model.Orders;
import com.example.personalapplication.model.Wallet;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyApplication extends Application {

    private static List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        SQLiteDatabase db = Connector.getDatabase();
        Orders Order1 =new Orders();
        Order1.setOrderId("2019043001");
        Order1.setContact("王大川");
        Order1.setPhoneNumber("188888888");
        Order1.setDate(new Date());
        Order1.setStatus("已维修");
        Order1.save();
        Orders Order2 =new Orders();
        Order2.setOrderId("2019043001");
        Order2.setContact("王大川");
        Order2.setPhoneNumber("188888888");
        Order2.setDate(new Date());
        Order2.setStatus("已维修");
        Order2.save();
        Orders Order3 =new Orders();
        Order3.setOrderId("2019043001");
        Order3.setContact("王大川");
        Order3.setPhoneNumber("188888888");
        Order3.setDate(new Date());
        Order3.setStatus("已维修");
        Order3.save();
        Orders Order4 =new Orders();
        Order4.setOrderId("2019043001");
        Order4.setContact("王大川");
        Order4.setPhoneNumber("188888888");
        Order4.setDate(new Date());
        Order4.setStatus("已维修");
        Order4.save();
        Orders Order5 =new Orders();
        Order5.setOrderId("2019043001");
        Order5.setContact("王大川");
        Order5.setPhoneNumber("188888888");
        Order5.setDate(new Date());
        Order5.setStatus("已维修");
        Order5.save();
        Orders Order6 =new Orders();
        Order6.setOrderId("2019043001");
        Order6.setContact("王大川");
        Order6.setPhoneNumber("188888888");
        Order6.setDate(new Date());
        Order6.setStatus("待确认");
        Order6.save();
        Wallet wallet1=new Wallet();
        wallet1.setTitle("维修保养");
        wallet1.setAmount("200");
        wallet1.setDate(new Date());
        wallet1.save();
        Wallet wallet2=new Wallet();
        wallet2.setTitle("维修保养");
        wallet2.setAmount("200");
        wallet2.setDate(new Date());
        wallet2.save();
        Wallet wallet3=new Wallet();
        wallet3.setTitle("维修保养");
        wallet3.setAmount("10");
        wallet3.setDate(new Date());
        wallet3.save();
        Wallet wallet4=new Wallet();
        wallet4.setTitle("维修保养");
        wallet4.setAmount("50");
        wallet4.setDate(new Date());
        wallet4.save();
        Wallet wallet5=new Wallet();
        wallet5.setTitle("维修保养");
        wallet5.setAmount("50");
        wallet5.setDate(new Date());
        wallet5.save();
        Wallet wallet6=new Wallet();
        wallet6.setTitle("维修保养");
        wallet6.setAmount("100");
        wallet6.setDate(new Date());
        wallet6.save();
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
