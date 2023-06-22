package com.example.practice;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.ViewHolder> {
    private List<CoinData> coins;
    private Context context;

    public CoinListAdapter(Context context, List<CoinData> coins) {
        this.context = context;
        this.coins = coins;
    }

    public void updateData(List<CoinData> newData) {
        coins.clear();
        coins.addAll(newData);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_crypto, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CoinData coin = coins.get(position);
        holder.textName.setText(coin.getName());
        holder.textSymbol.setText(coin.getSymbol());
        holder.textPrice.setText(coin.getPriceUsd());
        Glide.with(holder.itemView.getContext())
                .load(coin.getLogoUrl())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        resource.setBounds(0, 0, resource.getIntrinsicWidth(), resource.getIntrinsicHeight());
                        holder.textSymbol.setCompoundDrawables(resource, null, null, null);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        holder.textSymbol.setCompoundDrawables(null, null, null, null);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public void setData(List<CoinData> newData) {
        coins.clear();
        coins.addAll(newData);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textSymbol;
        public TextView textPrice;
        public TextView textCurrencySymbol;
        public ImageView imageLogo;

        public ViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textSymbol = itemView.findViewById(R.id.textSymbol);
            textPrice = itemView.findViewById(R.id.textPrice);
            textCurrencySymbol = itemView.findViewById(R.id.textCurrencySymbol);
            imageLogo = itemView.findViewById(R.id.imageLogo);
        }
    }
    public void clearData() {
        coins.clear();
        notifyDataSetChanged();
    }
}
