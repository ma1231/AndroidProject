package com.example.personalapplication.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.personalapplication.R;

import java.util.Calendar;
import java.util.Date;

public class PvCustomTimeUtil {

    private static TimePickerView pvCustomTime;

    public static TimePickerView initCustomTimePicker(Context context, OnTimeSelectListener onTimeSelectListener) {
        /*Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(new Date());
        Calendar selectedDate = Calendar.getInstance();*/
        /*selectedDate.setTime(new Date());
        Class clazz = context.getClass();
        if (clazz.getName().endsWith("PersonalDetailsActivity")) {
            try {
                PersonalDetailsActivity instance = (PersonalDetailsActivity) clazz.newInstance();
                Date date = instance.getDate();
                selectedDate.setTime(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        pvCustomTime = new TimePickerBuilder(context, onTimeSelectListener)
                .setType(new boolean[]{true, true, true, false, false, false})
                //.setDate(selectedDate)
                //.setRangDate(startDate, endDate)
                .setCancelText("取消")
                .setSubmitText("确认")
                .setTitleText("生日")
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setOutSideCancelable(false)
                .setBgColor(Color.parseColor("#80413F3F"))
                .setTextColorCenter(Color.WHITE)
                .setTextColorOut(Color.GRAY)
                .isCyclic(true)
                .isDialog(true)
                .setLayoutRes(R.layout.pickviewr_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final Button mConfirmBtn = v.findViewById(R.id.confirm);
                        Button mCancelBtn = v.findViewById(R.id.cancel);
                        Button mDestroyBtn = v.findViewById(R.id.destroy);
                        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        mCancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                pvCustomTime.dismiss();
                            }
                        });
                        mDestroyBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                }).build();
        return pvCustomTime;
    }
}
