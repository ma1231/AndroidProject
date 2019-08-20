package com.example.personalapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalapplication.R;
import com.example.personalapplication.model.Wallet;
import com.example.personalapplication.util.DateUtils;

import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

    private List<Wallet> walletList;

    public WalletAdapter(List<Wallet> walletList) {
        this.walletList = walletList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView amount;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_couponview, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Wallet wallet=walletList.get(position);
        holder.title.setText(wallet.getTitle());
        holder.amount.setText(wallet.getAmount());
        holder.date.setText(DateUtils.date2String(wallet.getDate()));
    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }
}
