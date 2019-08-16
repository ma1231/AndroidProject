package com.example.personalapplication.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;

import com.example.personalapplication.R;
import com.example.personalapplication.util.DateUtils;

import java.util.Date;

//自定义组合控件
public class MouldView extends RelativeLayout {

    private CustomCircleView customCircleView;
    private TextView info_textView;
    private TextView edit_textView;
    private CardView cardView;

    public MouldView(Context context) {
        this(context, null);
    }

    public MouldView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MouldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.mouldView);
        int img_src = mTypedArray.getResourceId(R.styleable.mouldView_img_src, R.mipmap.ic_launcher);
        String text = mTypedArray.getString(R.styleable.mouldView_text);
        String visibility =mTypedArray.getString(R.styleable.mouldView_edit_visibility);
        customCircleView.setImageResource(img_src);
        info_textView.setText(text);
        if(visibility.equals("visible")){
            edit_textView.setVisibility(View.VISIBLE);
        }else if(visibility.equals("invisible")){
            edit_textView.setVisibility(View.INVISIBLE);
        }
        mTypedArray.recycle();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.mould_layout, this, true);
        customCircleView = findViewById(R.id.mould_img);
        info_textView = findViewById(R.id.mould_info);
        edit_textView = findViewById(R.id.mould_edit);
        cardView = findViewById(R.id.mould_cardView);
    }

    //设置Edit_textView内容
    public void setText(String text) {
        edit_textView.setText(text);
    }

    public Date getText(){
        return DateUtils.string2Date((String) edit_textView.getText());
    }

    //设置Edit_textView点击监听事件
    public void setEditTextViewListener(OnClickListener onClickListener){
        edit_textView.setOnClickListener(onClickListener);
    }
}
