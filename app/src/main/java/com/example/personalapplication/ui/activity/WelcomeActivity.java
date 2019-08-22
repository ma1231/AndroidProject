package com.example.personalapplication.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.personalapplication.MyApplication;
import com.example.personalapplication.R;
import com.example.personalapplication.http.GetRequest_Interface;
import com.example.personalapplication.model.BingPic;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn;
    private Button registerBtn;
    private ImageView backgroundImg;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

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
        prefs = getSharedPreferences("bing_pic", MODE_PRIVATE);
        String bingPic = prefs.getString("bing_pic", null);
        //loadBingPic();
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(backgroundImg);
        } else {
            loadBingPic();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
                Intent intentReg = new Intent(this, RegisterActivity.class);
                startActivity(intentReg);
                break;
            case R.id.login_btn:
                Intent intentLog = new Intent(this, LoginActivity.class);
                startActivity(intentLog);
                break;
            default:
                break;
        }
    }

    private void loadBingPic() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cn.bing.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<BingPic> call = request.getImage();
        call.enqueue(new Callback<BingPic>() {
            @Override
            public void onResponse(Call<BingPic> call, Response<BingPic> response) {
                BingPic bp = response.body();
                final String bingPic = "https://cn.bing.com" + bp.getImages().get(0).getUrl();
                editor = prefs.edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WelcomeActivity.this).load(bingPic).into(backgroundImg);
                    }
                });
            }

            @Override
            public void onFailure(Call<BingPic> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {
        MyApplication.finishAll();
    }
}
