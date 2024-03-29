package com.example.personalapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalapplication.R;
import com.example.personalapplication.model.Orders;
import com.example.personalapplication.ui.activity.MyOrdersActivity;

import org.litepal.LitePal;

import java.util.List;

public class PopupWindowAdapter extends RecyclerView.Adapter<PopupWindowAdapter.ViewHolder> {

    private List<String> list;
    private RecyclerViewAdapterListener rvaListener;
    private Context context;

    public PopupWindowAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        Button dropdownButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dropdownButton = itemView.findViewById(R.id.dropdown);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dropdown_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        final MyOrdersActivity myOrdersActivity= (MyOrdersActivity) context;
        viewHolder.dropdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                String dropdownText = list.get(position);
                if (dropdownText.equals("全部")) {
                    List<Orders> orders = LitePal.findAll(Orders.class);
                    rvaListener.setAdapterList(orders);
                } else if (dropdownText.equals("待确认")) {
                    List<Orders> orders = LitePal.where("status = ?", "待确认").find(Orders.class);
                    rvaListener.setAdapterList(orders);
                } else if (dropdownText.equals("待维修")) {
                    List<Orders> orders = LitePal.where("status = ?", "待维修").find(Orders.class);
                    rvaListener.setAdapterList(orders);
                } else if (dropdownText.equals("已维修")) {
                    List<Orders> orders = LitePal.where("status = ?", "已维修").find(Orders.class);
                    rvaListener.setAdapterList(orders);
                } else if (dropdownText.equals("已取消")) {
                    List<Orders> orders = LitePal.where("status = ?", "已取消").find(Orders.class);
                    rvaListener.setAdapterList(orders);
                }
                myOrdersActivity.getPopupWindow().dismiss();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String dropdownButtonText = list.get(position);
        holder.dropdownButton.setText(dropdownButtonText);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerViewAdapterListener {
        void setAdapterList(List<Orders> list);
    }

    public void setRvaListener(RecyclerViewAdapterListener rvaListener) {
        this.rvaListener = rvaListener;
    }
}
