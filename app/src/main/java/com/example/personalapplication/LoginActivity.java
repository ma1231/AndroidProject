package com.example.personalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personalapplication.db.User;

import org.litepal.LitePal;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUsername;
    private EditText mPassword;
    private CheckBox mRememberPass;
    private Button mLoginBtn;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CustomToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyApplication.addActivity(this);
        pref = getSharedPreferences("rememberPass", MODE_PRIVATE);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mRememberPass = findViewById(R.id.remember_password);
        mLoginBtn = findViewById(R.id.login);
        toolbar = findViewById(R.id.toolbar);
        mLoginBtn.setOnClickListener(this);
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String username = pref.getString("username", "");
            String password = pref.getString("password", "");
            mUsername.setText(username);
            mPassword.setText(password);
            mRememberPass.setChecked(true);
        }
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
            case R.id.login:
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                editor = pref.edit();
                if (mRememberPass.isChecked()) {
                    editor.putBoolean("remember_password", true);
                    editor.putString("username", username);
                    editor.putString("password", password);
                } else {
                    editor.clear();
                }
                editor.apply();
                List<User> users = LitePal.select("username", "password")
                        .where("username = ? and password = ?", username, password)
                        .find(User.class);
                if (users.size() != 0) {
                    //记录当前登录用户
                    pref = getSharedPreferences("currentUsername", MODE_PRIVATE);
                    editor = pref.edit();
                    editor.putString("currentUsername", username);
                    editor.apply();
                    editor.clear();
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
