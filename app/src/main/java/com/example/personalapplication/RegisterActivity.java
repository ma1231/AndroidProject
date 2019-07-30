package com.example.personalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.pickerview.view.TimePickerView;
import com.example.personalapplication.db.User;
import com.example.personalapplication.util.PvCustomTimeUtil;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomToolbar toolbar;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPasswordAga;
    private EditText mBirthday;
    private Button mRegisterBtn;
    private TimePickerView pvCustomTime;

    private String username, password, passwordAga;
    private boolean usernameFlag, passwordFlag, passwordAgaFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        pvCustomTime = PvCustomTimeUtil.initCustomTimePicker(this);
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
                username = mUsername.getText().toString().trim();
                usernameFlag = checkUsername(username);
                break;
            case R.id.password:
                password = mPassword.getText().toString().trim();
                passwordFlag = checkPassword(password);
                break;
            case R.id.password_again:
                passwordAga = mPasswordAga.getText().toString().trim();
                if (!passwordAga.equals(password) || password == null) {
                    mPasswordAga.setText("");
                    Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    passwordAgaFlag = false;
                }
                break;
            case R.id.birthday:
                pvCustomTime.show();
                break;
            case R.id.register:
                if (usernameFlag && passwordFlag && passwordAgaFlag) {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                }
                break;
            default:
                break;
        }
    }

    private boolean checkUsername(String username) {
        //用户名以字母开头，只能包含数字、字母、下划线，长度为6-16位
        String regex = "[a-zA-z][0-9a-zA-z_]{5,15}";
        if (username.matches(regex)) {
            return true;
        } else
            return false;
    }

    private boolean checkPassword(String password) {
        //密码 6-16位数字和字母的组合
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if (password.matches(regex)) {
            return true;
        } else
            return false;
    }
}
