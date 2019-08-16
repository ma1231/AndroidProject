package com.example.personalapplication.adapter;

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

    public OrdersAdapter(List<Orders> OrderList) {
        mOrderList = OrderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_order_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orders Order=mOrderList.get(position);
        holder.contact.setText(Order.getContact());
        holder.orderId.setText(Order.getOrderId());
        holder.date.setText(DateUtils.date2String(Order.getDate()));
        holder.phoneNumber.setText(Order.getPhoneNumber());
        holder.status.setText(Order.getStatus());
        if(holder.status.getText().equals("待确认")){
            holder.button.setVisibility(View.VISIBLE);
        }else{
            holder.button.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}
