package com.example.personalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.personalapplication.db.User;
import com.example.personalapplication.util.CheckFormatUtils;

import org.litepal.LitePal;

import java.util.List;

public class ChangePasswordActivity extends AppCompatActivity {

    private CustomPasswordInput mOldPassword;
    private CustomPasswordInput mNewPassword;
    private CustomPasswordInput mNewPasswordAga;
    private CustomToolbar toolbar;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        MyApplication.addActivity(this);
        mOldPassword = findViewById(R.id.old_password);
        mNewPassword = findViewById(R.id.new_password);
        mNewPasswordAga = findViewById(R.id.new_passwordAga);
        mOldPassword.setImgClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOldPassword.changeVisibility();
            }
        });
        mNewPasswordAga.setImgClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewPasswordAga.changeVisibility();
            }
        });
        mNewPassword.setImgClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewPassword.changeVisibility();
            }
        });
        toolbar = findViewById(R.id.toolbar);
        toolbar.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref = getSharedPreferences("currentUsername", MODE_PRIVATE);
                String currentUsername = pref.getString("currentUsername", "");
                List<User> users = LitePal.select("password").where("username = ?", currentUsername).find(User.class);
                String password = users.get(0).getPassword();
                Log.d("pass", password);
                String oldPassword = mOldPassword.getText();
                String newPassword = mNewPassword.getText();
                String newPasswordAga = mNewPasswordAga.getText();
                boolean passwordFlag, successFlag = false;
                passwordFlag = CheckFormatUtils.checkPassword(newPassword);
                while (!successFlag) {
                    if (!password.equals(oldPassword)) {
                        Toast.makeText(ChangePasswordActivity.this, "您输入的旧密码错误", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (oldPassword.equals(newPassword)) {
                        Toast.makeText(ChangePasswordActivity.this, "请不要重复设置密码", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (!passwordFlag) {
                        Toast.makeText(ChangePasswordActivity.this, "新密码格式错误", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (!newPassword.equals(newPasswordAga)) {
                        Toast.makeText(ChangePasswordActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    successFlag = true;
                    User user = new User();
                    user.setPassword(newPassword);
                    user.updateAll("username = ?", currentUsername);
                    Toast.makeText(ChangePasswordActivity.this, "修改密码成功，请您重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
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
                Intent intent = new Intent(this, PersonalDetailsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    /*@Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar:
                Intent intent=new Intent(this,PersonalDetailsActivity.class);
                startActivity(intent);
        }
    }*/
}
