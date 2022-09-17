package com.semproject.shoppingnow.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.semproject.shoppingnow.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder>
{
    ArrayList<OrderModel> data;

    public OrderAdapter(ArrayList<OrderModel> data) {
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderViewHolder holder, int position) {
        holder.orderId.setText(data.get(position).getOrderId());
        holder.productId.setText(data.get(position).getProductId());
        holder.purchaseDate.setText(data.get(position).getPurchaseDate());
        holder.deliveryDate.setText(data.get(position).getDeliveryDate());
        holder.quantity.setText(String.valueOf(data.get(position).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
