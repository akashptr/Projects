package com.semproject.shoppingnow.order;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.semproject.shoppingnow.R;

import org.jetbrains.annotations.NotNull;

public class OrderViewHolder extends RecyclerView.ViewHolder
{
    ImageView productImage;
    TextView orderId, productId, purchaseDate, deliveryDate, quantity;
    public OrderViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        orderId = itemView.findViewById(R.id.text_order_id);
        productId = itemView.findViewById(R.id.text_order_pro_id);
        purchaseDate = itemView.findViewById(R.id.text_order_pur_date);
        deliveryDate = itemView.findViewById(R.id.text_order_deli_date);
        quantity = itemView.findViewById(R.id.text_order_quantity);
    }
}
