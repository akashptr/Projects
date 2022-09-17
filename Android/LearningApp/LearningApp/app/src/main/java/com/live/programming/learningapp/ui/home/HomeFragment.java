package com.live.programming.learningapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.live.programming.learningapp.R;
import com.live.programming.learningapp.databinding.FragmentHomeBinding;
import com.live.programming.learningapp.models.Food;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView gridRecyclerView;
    private GridLayoutManager gridManager;
    private FirebaseFirestore fire;

    List<Food> foodList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        gridRecyclerView = binding.homeRecyclerView;
        //grid layout define
        gridManager = new GridLayoutManager(requireContext(),2);
        //set layout pattern against RV
        gridRecyclerView.setLayoutManager(gridManager);

        fetchAllData();

        return root;
    }

    private void fetchAllData() {
        //code to access the entire document within a collection
        fire = FirebaseFirestore.getInstance();
        fire.collection("food")
                .get() //to fetch all at a time
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot snapshots = task.getResult();
                        for (DocumentSnapshot documentSnapshot:
                             snapshots.getDocuments()) {
                            //food details
                            Food food = new Food(
                                    documentSnapshot.getId(),
                                    documentSnapshot.getString("name"),
                                    "Rs. " + documentSnapshot.get("cost"),
                                    documentSnapshot.getString("picture")
                            );
                            //adding to existing list
                            foodList.add(food);
                        }
                        //now use for recycler view
                        HomeAdapter adapter = new HomeAdapter(foodList, requireContext());
                        gridRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(e -> {
                    Snackbar.make(requireContext(), binding.getRoot(), "failed: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}