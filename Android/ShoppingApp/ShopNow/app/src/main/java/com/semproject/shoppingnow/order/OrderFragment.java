package com.semproject.shoppingnow.order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.semproject.shoppingnow.AppConstants;
import com.semproject.shoppingnow.LocalSession;
import com.semproject.shoppingnow.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFragment extends Fragment {

    private RecyclerView recView;
    private DocumentReference docRef;
    private LocalSession session;
    private ArrayList<OrderModel> orderList;
    OrderAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        recView = view.findViewById(R.id.recycler_order);
        recView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderList = new ArrayList<>();
        adapter = new OrderAdapter(orderList);
        recView.setAdapter(adapter);
        session = new LocalSession(requireContext());
        docRef = FirebaseFirestore.getInstance().collection(AppConstants.COLLECTION_ACCOUNT).document(session.getUserId());
        prepareData();
    }

    public void prepareData()
    {
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists() && documentSnapshot.contains(OrderConstants.ORDER))
                {
                    List<Map<String, Object>> allOrders = (List<Map<String, Object>>)documentSnapshot.get(OrderConstants.ORDER);
                    for(Map<String, Object> eachOrder: allOrders)
                    {
                        OrderModel ordRef = new OrderModel();
                        for(Map.Entry<String, Object> entry: eachOrder.entrySet())
                        {
                            String key = entry.getKey();
                            String value = String.valueOf(entry.getValue());
                            switch (key)
                            {
                                case OrderConstants.ORDER_ID:
                                    ordRef.setOrderId(value);
                                    break;
                                case OrderConstants.PRODUCT_ID:
                                    ordRef.setProductId(value);
                                    break;
                                case OrderConstants.PURCHASE_DATE:
                                    ordRef.setPurchaseDate(value);
                                    break;
                                case OrderConstants.DELIVERY_DATE:
                                    ordRef.setDeliveryDate(value);
                                    break;
                                case OrderConstants.QUANTITY:
                                    ordRef.setQuantity(Integer.parseInt(value));
                                    break;
                            }
                        }
                        orderList.add(ordRef);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

}