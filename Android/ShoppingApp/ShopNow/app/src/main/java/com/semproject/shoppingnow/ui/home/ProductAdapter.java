package com.semproject.shoppingnow.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.semproject.shoppingnow.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>
{
    ArrayList<ProductRecModel> data;

    public ProductAdapter(ArrayList<ProductRecModel> data) {
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductViewHolder holder, int position) {
        holder.productName.setText(data.get(position).getProductName());
        holder.productDesc.setText(data.get(position).getProductDesc());
        Glide.with(holder.productImage.getContext()).load(data.get(position).getImageUrl()).into(holder.productImage);
        holder.productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionMenuHomeToProductDetailFragment action = HomeFragmentDirections.actionMenuHomeToProductDetailFragment(data.get(position).getProductId());
                NavController navController = Navigation.findNavController(v);
                navController.navigate(action);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView productName, productDesc;
        CardView productCard;
        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productDesc = itemView.findViewById(R.id.product_desc);
            productCard = itemView.findViewById(R.id.product_card_item);
        }
    }
}
