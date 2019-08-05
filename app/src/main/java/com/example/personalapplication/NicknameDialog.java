package com.example.personalapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NicknameDialog extends DialogFragment implements View.OnClickListener {

    private View view;
    private Button btn_destory;
    private Button btn_save;
    private EditText edit_nickname;
    private onNicknameEditedListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.nickname_dialog_layout, container);
        }
        btn_destory = view.findViewById(R.id.destroy);
        btn_save = view.findViewById(R.id.save);
        edit_nickname = view.findViewById(R.id.nickname_edit);
        btn_destory.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.destroy:
                this.dismiss();
                break;
            case R.id.save:
                String nickname = edit_nickname.getText().toString();
                if (nickname.length() > 9) {
                    Toast.makeText(getContext(), "昵称长度不能超过8个字符，请重新输入", Toast.LENGTH_SHORT).show();
                    edit_nickname.getText().clear();
                    break;
                } else {
                    listener.onNicknameEdited(nickname);
                    this.dismiss();
                }
                break;
            default:
                break;
        }
    }

    //回调接口
    public interface onNicknameEditedListener {
        void onNicknameEdited(String nickname);
    }

    public void setonNickNameEditedListener(onNicknameEditedListener listener) {
        this.listener = listener;
    }
}
