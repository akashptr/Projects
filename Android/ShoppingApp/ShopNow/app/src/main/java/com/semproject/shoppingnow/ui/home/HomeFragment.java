package com.semproject.shoppingnow.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.semproject.shoppingnow.AppConstants;
import com.semproject.shoppingnow.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ArrayList<ProductRecModel> productList;;
    private ProductAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productList = new ArrayList<>();
        prepareData();
        adapter = new ProductAdapter(productList);
        binding.productRecView.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        binding.productRecView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void prepareData()
    {
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection(AppConstants.COLLECTION_PRODUCT);
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            QuerySnapshot qs = task.getResult();
                            for(DocumentSnapshot docSnap : qs.getDocuments())
                            {
                                ProductRecModel product = new ProductRecModel();
                                product.setImageUrl(docSnap.getString(ProductRecModel.PRODUCT_IMAGE_URL));
                                product.setProductId(docSnap.getId());
                                product.setProductName(docSnap.getString(ProductRecModel.PRODUCT_NAME));
                                product.setProductDesc(docSnap.getString(ProductRecModel.PRODUCT_DESC));
                                productList.add(product);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}