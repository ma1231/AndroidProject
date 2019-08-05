package com.example.personalapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn;
    private Button registerBtn;
    private ImageView backgroundImg;//欢迎界面背景图通过网络获取，待完成

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        MyApplication.addActivity(this);
        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);
        backgroundImg = findViewById(R.id.background_picture);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
                Intent intentReg = new Intent(this, RegisterActivity.class);
                startActivity(intentReg);
                break;
            case R.id.login_btn:
                Intent intentLog=new Intent(this,LoginActivity.class);
                startActivity(intentLog);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MyApplication.finishAll();
    }
}
