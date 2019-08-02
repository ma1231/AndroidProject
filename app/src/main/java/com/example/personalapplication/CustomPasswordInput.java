package com.example.personalapplication;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CustomPasswordInput extends RelativeLayout {

    private EditText mPasswordInput;
    private ImageView mPasswordImg;

    public CustomPasswordInput(Context context) {
        this(context, null);
    }

    public CustomPasswordInput(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPasswordInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_password_input, this, true);
        mPasswordInput = findViewById(R.id.password_input);
        mPasswordImg = findViewById(R.id.password_img);
    }

    public String getText() {
        return mPasswordInput.getText().toString().trim();
    }

    public void setImgClickListener(OnClickListener onClickListener) {
        mPasswordImg.setOnClickListener(onClickListener);
    }

    public void changeVisibility() {
        TransformationMethod type = mPasswordInput.getTransformationMethod();
        if (PasswordTransformationMethod.getInstance().equals(type)) {
            mPasswordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mPasswordImg.setImageResource(R.mipmap.visible);
        } else {
            mPasswordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mPasswordImg.setImageResource(R.mipmap.invisible);
        }
    }
}
