package com.example.personalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.personalapplication.db.User;
import com.example.personalapplication.util.DateUtils;
import com.example.personalapplication.util.PvCustomTimeUtil;

import org.litepal.LitePal;

import java.util.Date;
import java.util.List;

public class PersonalDetailsActivity extends AppCompatActivity implements OnTimeSelectListener {

    private MouldView mBirthdayText;
    private MouldView mPasswordText;
    private CustomToolbar toolbar;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private TimePickerView pvCustomTime;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_details_layout);
        mBirthdayText = findViewById(R.id.mould_birthday);
        mPasswordText = findViewById(R.id.mould_password);
        pvCustomTime = PvCustomTimeUtil.initCustomTimePicker(this, this);
        pref = getSharedPreferences("currentUsername", MODE_PRIVATE);
        currentUsername = pref.getString("currentUsername", "");
        List<User> users = LitePal.select("birthday").where("username = ?", currentUsername).find(User.class);
        mBirthdayText.setText(DateUtils.date2String(users.get(0).getBirthday()));
        mPasswordText.setText("修改密码");
        mBirthdayText.setEditTextViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvCustomTime.show();
            }
        });
        mPasswordText.setEditTextViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDetailsActivity.this,ChangePassActivity.class);
                startActivity(intent);
            }
        });
        toolbar = findViewById(R.id.toolbar);
        toolbar.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //退出登录，清空当前登录用户记录
                editor = pref.edit();
                editor.putString("currentUsername", "");
                editor.apply();
                editor.clear();
                Intent intent = new Intent(PersonalDetailsActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onTimeSelect(Date date, View v) {
        mBirthdayText.setText(DateUtils.date2String(date));
        User user = new User();
        user.setBirthday(date);
        user.updateAll("username = ?", currentUsername);
    }
}
