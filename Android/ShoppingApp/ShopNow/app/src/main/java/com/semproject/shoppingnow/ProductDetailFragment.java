package com.semproject.shoppingnow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.semproject.shoppingnow.databinding.FragmentProductDetailBinding;
import com.semproject.shoppingnow.order.OrderConstants;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import static com.semproject.shoppingnow.ProductConstants.PRODUCT_COST;
import static com.semproject.shoppingnow.ProductConstants.PRODUCT_DESC;
import static com.semproject.shoppingnow.ProductConstants.PRODUCT_IMAGE;
import static com.semproject.shoppingnow.ProductConstants.PRODUCT_NAME;

public class ProductDetailFragment extends Fragment {

    String productId;
    LocalSession session;
    FragmentProductDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session = new LocalSession(getActivity());
        binding = FragmentProductDetailBinding.bind(view);

        if(session.getLoginStatus())
            binding.buyNow.setVisibility(View.VISIBLE);
        else binding.buyNow.setVisibility(View.INVISIBLE);

        if(getArguments() != null)
        {
            ProductDetailFragmentArgs arg = ProductDetailFragmentArgs.fromBundle(getArguments());
            productId = arg.getProductId();
            inflateData();
        }

        binding.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(String.valueOf(binding.Quantity.getText()));
                binding.Quantity.setText(String.valueOf(qty + 1));
            }
        });

        binding.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(String.valueOf(binding.Quantity.getText()));
                if(qty != 1)
                    binding.Quantity.setText(String.valueOf(qty - 1));
            }
        });

        binding.buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> orderMap = new HashMap<>();
                String orderId = UUID.randomUUID().toString();
                orderMap.put(OrderConstants.ORDER_ID, orderId);
                orderMap.put(OrderConstants.PRODUCT_ID, productId);
                orderMap.put(OrderConstants.QUANTITY, String.valueOf(binding.Quantity.getText()));
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                String purchaseDate = day + "/" + month + "/" + year;
                String deliveryDate = (day + 3) + "/" + month + "/" + year;
                orderMap.put(OrderConstants.PURCHASE_DATE, purchaseDate);
                orderMap.put(OrderConstants.DELIVERY_DATE, deliveryDate);

                DocumentReference docRef = FirebaseFirestore.getInstance().collection(AppConstants.COLLECTION_ACCOUNT).document(session.getUserId());
                docRef.update(OrderConstants.ORDER, FieldValue.arrayUnion(orderMap))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                Snackbar.make(v, "Successfull", Snackbar.LENGTH_LONG).show();
                            }
                        });
            }
        });

    }

    private void inflateData() {
        DocumentReference productRef = FirebaseFirestore.getInstance().collection(AppConstants.COLLECTION_PRODUCT).document(productId);
        productRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot docSnap = task.getResult();
                            binding.productDetailName.setText(docSnap.getString(PRODUCT_NAME));
                            binding.productPriceDetails.setText(String.valueOf(docSnap.get(PRODUCT_COST)));
                            binding.description.setText(docSnap.getString(PRODUCT_DESC));
                            Glide.with(requireContext()).load(docSnap.getString(PRODUCT_IMAGE)).into(binding.productDetailsImage);
                        }
                    }
                });
    }
}