package com.example.personalapplication.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        MyApplication.addActivity(this);
        orderList = LitePal.findAll(Orders.class);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OrdersAdapter(orderList,this);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(30));
        toolbar = findViewById(R.id.toolbar);
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
        initDropdownText();
        View view = LayoutInflater.from(this).inflate(R.layout.custom_popupwindow, null);
        RecyclerView dropdownRecyclerView = view.findViewById(R.id.dropdown_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dropdownRecyclerView.setLayoutManager(layoutManager);
        PopupWindowAdapter adapter = new PopupWindowAdapter(dropdownList);
        adapter.setRvaListener(this);
        dropdownRecyclerView.setAdapter(adapter);
        popupWindow=new PopupWindow(view);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(dropdownRecyclerView);
        popupWindow.setOutsideTouchable(true);
//        if(toolbar!=null){
//            toolbar.removeAllViews();
//        }
        //popupWindow.showAsDropDown();
    }

    private void initDropdownText() {
        dropdownList.add("全部");
        dropdownList.add("待确认");
        dropdownList.add("待维保");
        dropdownList.add("已维保");
        dropdownList.add("已取消");
    }

    @Override
    public void setAdapterList(List<Orders> list) {
        OrdersAdapter ordersAdapter = new OrdersAdapter(list,this);
        recyclerView.setAdapter(ordersAdapter);
    }

    @Override
    public void onItemLongClick(View view, final int position) {
        PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.popupmenu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //解决删除item时的position错乱问题
                //使用RecyclerView的局部刷新方法notifyItemRangeChanged(int positionStart, int itemCount)
                Orders remove = orderList.remove(position);
                //LitePal.deleteAll(remove.getClass(),)
                adapter.notifyItemRemoved(position);
                if(position!=orderList.size()){
                    adapter.notifyItemRangeChanged(position,orderList.size()-position);
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
