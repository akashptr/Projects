package com.live.programming.learningapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.live.programming.learningapp.R;
import com.live.programming.learningapp.databinding.HomeRecyclerItemsBinding;
import com.live.programming.learningapp.models.Food;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.InnerClass> {

    List<Food> foodList;
    Context context;

    public HomeAdapter(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public InnerClass onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View adapterView = LayoutInflater.from(parent.getContext()) //or we could have used context member
                            .inflate(R.layout.home_recycler_items, parent, false);
        return new InnerClass(adapterView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InnerClass holder, int position) {
        //assign values to the child views
        Food food = foodList.get(position);
        holder.txtV.setText(food.getFoodName());

        //use image library to render image from a URL
        Glide.with(context)
                .load(food.getFoodImage())
                .error(android.R.drawable.stat_notify_error)
                .into(holder.imgV);

    }

    @Override
    public int getItemCount() {
        return foodList.size(); //no. of items to be there in the recycler view
    }

    public class InnerClass extends RecyclerView.ViewHolder {

        HomeRecyclerItemsBinding binding;
        ImageView imgV;
        CardView cardV;
        TextView txtV;

        public InnerClass(@NonNull @NotNull View itemView) {
            super(itemView);
            binding = HomeRecyclerItemsBinding.bind(itemView);
            imgV = binding.itemImage;
            cardV = binding.itemCard;
            txtV = binding.itemTitle;
        }
    }
}
