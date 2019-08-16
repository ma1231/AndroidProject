package com.example.personalapplication.ui.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;

public class CustomPopupWindow extends PopupWindow {

    private ListView listView;
    private ArrayList<String> items;
    private int width;
    private int height;
    private int type;
    private AdapterView.OnItemClickListener onItemClickListener;

    public CustomPopupWindow(Context context) {
    }

    public CustomPopupWindow(Context context, int width, int height, int type,
                             AdapterView.OnItemClickListener onItemClickListener, ArrayList<String> items) {


    }

}
