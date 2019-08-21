package com.example.personalapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalapplication.R;
import com.example.personalapplication.model.Orders;
import com.example.personalapplication.util.DateUtils;


import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private List<Orders> mOrderList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView orderId;
        private TextView contact;
        private TextView phoneNumber;
        private TextView date;
        private TextView status;
        private Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderId);
            status = itemView.findViewById(R.id.status);
            contact = itemView.findViewById(R.id.contact);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            date = itemView.findViewById(R.id.date);
            button = itemView.findViewById(R.id.button);
        }
    }

    public OrdersAdapter(List<Orders> OrderList,Context context) {
        mOrderList = OrderList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_order_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Orders order=mOrderList.get(position);
        holder.contact.setText(order.getContact());
        holder.orderId.setText(order.getOrderId());
        holder.date.setText(DateUtils.date2String(order.getDate()));
        holder.phoneNumber.setText(order.getPhoneNumber());
        holder.status.setText(order.getStatus());
        if(holder.status.getText().equals("待确认")){
            holder.button.setVisibility(View.VISIBLE);
        }else{
            holder.button.setVisibility(View.INVISIBLE);
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog=new AlertDialog.Builder(context);
                dialog.setMessage("确定取消订单？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        holder.status.setText("已取消");
                        order.setStatus("已取消");
                        order.save();
                        holder.button.setVisibility(View.INVISIBLE);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
            }
        });
        //if(holder.status.getText().equals("已取消")) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(holder.itemView,position);
                    return false;
                }
            });
        //}
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemLongClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
