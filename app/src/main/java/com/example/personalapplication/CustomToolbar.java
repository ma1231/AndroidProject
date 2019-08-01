package com.example.personalapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

//自定义Toolbar
public class CustomToolbar extends Toolbar {

    private TextView leftText;
    private Button rightButton;

    public CustomToolbar(Context context) {
        this(context, null);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.toolbarView);
        String text = typedArray.getString(R.styleable.toolbarView_left_text);
        String buttonText = typedArray.getString(R.styleable.toolbarView_button_text);
        String visibility = typedArray.getString(R.styleable.toolbarView_button_visibility);
        if (visibility.equals("visible")) {
            rightButton.setVisibility(View.VISIBLE);
        } else if (visibility.equals("gone")) {
            rightButton.setVisibility(View.GONE);
        }
        leftText.setText(text);
        rightButton.setText(buttonText);
        typedArray.recycle();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_toolbar_layout, this, true);
        leftText = findViewById(R.id.leftText);
        rightButton = findViewById(R.id.rightButton);
    }

    public void setRightButtonListener(OnClickListener onClickListener) {
        rightButton.setOnClickListener(onClickListener);
    }
}
