package com.example.personalapplication.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalapplication.MyApplication;
import com.example.personalapplication.R;
import com.example.personalapplication.adapter.OrdersAdapter;
import com.example.personalapplication.adapter.PopupWindowAdapter;
import com.example.personalapplication.adapter.SpaceItemDecoration;
import com.example.personalapplication.model.Orders;
import com.example.personalapplication.ui.custom.CustomToolbar;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends AppCompatActivity implements PopupWindowAdapter.RecyclerViewAdapterListener, OrdersAdapter.OnItemClickListener {

    private List<Orders> orderList = new ArrayList<>();
    private List<String> dropdownList = new ArrayList<>();
    private CustomToolbar toolbar;
    private PopupWindow popupWindow;
    private RecyclerView recyclerView;
    private OrdersAdapter adapter;
    private Button rightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        MyApplication.addActivity(this);
        initDropdownText();
        orderList = LitePal.findAll(Orders.class);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OrdersAdapter(orderList, this);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(30));
        toolbar = findViewById(R.id.toolbar);
        rightButton = toolbar.getRightButton();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showPopupWindow();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @SuppressLint("WrongConstant")
    private void showPopupWindow() {
        if (popupWindow == null ) {
            View view = LayoutInflater.from(this).inflate(R.layout.custom_popupwindow, null);
            RecyclerView dropdownRecyclerView = view.findViewById(R.id.dropdown_recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            dropdownRecyclerView.setLayoutManager(layoutManager);
            PopupWindowAdapter adapter = new PopupWindowAdapter(dropdownList,this);
            adapter.setRvaListener(this);
            dropdownRecyclerView.setAdapter(adapter);
            //产生背景变暗效果
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 0.6f;
            getWindow().setAttributes(lp);
            popupWindow = new PopupWindow(view);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setOutsideTouchable(false);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.showAsDropDown(rightButton);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 1f;
                    getWindow().setAttributes(lp);
                }
            });
        }else if(popupWindow.isShowing()){
            popupWindow.dismiss();
        }else {
            popupWindow.showAsDropDown(rightButton);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 0.6f;
            getWindow().setAttributes(lp);
        }
    }

    private void initDropdownText() {
        dropdownList.add("全部");
        dropdownList.add("待确认");
        dropdownList.add("待维修");
        dropdownList.add("已维修");
        dropdownList.add("已取消");
    }

    @Override
    public void setAdapterList(List<Orders> list) {
        orderList = list;
        OrdersAdapter ordersAdapter = new OrdersAdapter(list, this);
        adapter = ordersAdapter;
        ordersAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(ordersAdapter);
    }

    @Override
    public void onItemLongClick(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //解决删除item时的position错乱问题
                //使用RecyclerView的局部刷新方法notifyItemRangeChanged(int positionStart, int itemCount)
                Orders remove = orderList.remove(position);
                LitePal.delete(remove.getClass(), remove.getId());
                adapter.notifyItemRemoved(position);
                if (position != orderList.size()) {
                    adapter.notifyItemRangeChanged(position, orderList.size() - position);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

/* public void dismissPopupWindow() {
        popupWindow.dismiss();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }*/
}
