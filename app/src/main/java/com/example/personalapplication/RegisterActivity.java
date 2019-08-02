package com.example.personalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.personalapplication.db.User;
import com.example.personalapplication.util.CheckFormatUtils;
import com.example.personalapplication.util.DateUtils;
import com.example.personalapplication.util.PvCustomTimeUtil;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.tablemanager.Connector;

import java.util.Date;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, OnTimeSelectListener {

    private CustomToolbar toolbar;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPasswordAga;
    private EditText mBirthday;
    private Button mRegisterBtn;
    private TimePickerView pvCustomTime;

    private String username, password, passwordAga, birthday;
    private boolean usernameFlag, passwordFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        pvCustomTime = PvCustomTimeUtil.initCustomTimePicker(this, this);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mPasswordAga = findViewById(R.id.password_again);
        mRegisterBtn = findViewById(R.id.register);
        mBirthday = findViewById(R.id.birthday);
        mUsername.setOnClickListener(this);
        mPassword.setOnClickListener(this);
        mPasswordAga.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
        mBirthday.setOnClickListener(this);
        toolbar = findViewById(R.id.toolbar);
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
                Intent intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.username:
                break;
            case R.id.password:
                break;
            case R.id.password_again:
                break;
            case R.id.birthday:
                pvCustomTime.show();
                break;
            case R.id.register:
                username = mUsername.getText().toString().trim();
                password = mPassword.getText().toString().trim();
                passwordAga = mPasswordAga.getText().toString().trim();
                usernameFlag = CheckFormatUtils.checkUsername(username);
                passwordFlag = CheckFormatUtils.checkPassword(password);
                List<User> users = LitePal.select("username").where("username = ?", username).find(User.class);
                boolean registerFlag = false;
                loop:while (!registerFlag) {
                    for (User user : users) {
                        if (user.getUsername().equals(username)) {
                            Toast.makeText(this, "用户名已被注册，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                        break loop;
                    }
                    if (!usernameFlag) {
                        Toast.makeText(this, "用户名以字母开头，只能包含数字、字母、下划线，长度为6-16位", Toast.LENGTH_SHORT).show();
                        break loop;
                    }
                    if (!passwordFlag) {
                        Toast.makeText(this, "密码 6-16位数字和字母的组合", Toast.LENGTH_SHORT).show();
                        break loop;
                    }
                    if (!passwordAga.equals(password)) {
                        mPasswordAga.setText(null);
                        Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                        break loop;
                    }
                    if (birthday == null) {
                        Toast.makeText(this, "请输入您的生日", Toast.LENGTH_SHORT).show();
                        break loop;
                    }
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setBirthday(DateUtils.string2Date(birthday));
                    user.save();
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    registerFlag = true;
                }
                break;
            default:
                break;
        }
    }

    //回调日期
    @Override
    public void onTimeSelect(Date date, View v) {
        if (date != null) {
            birthday = DateUtils.date2String(date);
            mBirthday.setText(birthday);
        }
    }
}
