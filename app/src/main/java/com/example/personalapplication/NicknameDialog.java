package com.example.personalapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NicknameDialog extends DialogFragment implements View.OnClickListener {

    private View view;
    private Button btn_destory;
    private Button btn_save;
    private EditText edit_nickName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.nickname_dialog_layout,container);
        }
        btn_destory=view.findViewById(R.id.destroy);
        btn_save=view.findViewById(R.id.save);
        edit_nickName=view.findViewById(R.id.nickname_edit);
        btn_destory.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.destroy:
                this.dismiss();
                break;
            case R.id.save:
                SharedPreferences.Editor editor=getContext().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                String nickName=edit_nickName.getText().toString();
                editor.putString("nickName",nickName);
                editor.apply();
                this.dismiss();
                editor.clear();
                break;
            default:
                break;
        }
    }
}
