package com.example.personalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.personalapplication.db.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomToolbar toolbar;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPasswordAga;
    private Button mRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUsername=findViewById(R.id.username);
        mPassword=findViewById(R.id.password);
        mPasswordAga=findViewById(R.id.password_again);
        mRegisterBtn=findViewById(R.id.register);
        mUsername.setOnClickListener(this);
        mPassword.setOnClickListener(this);
        mPasswordAga.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
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
        switch (view.getId()){
            case R.id.username:
                
        }
    }
}
