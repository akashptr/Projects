package com.live.programming.learningapp.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.live.programming.learningapp.LoginActivity;
import com.live.programming.learningapp.R;
import com.live.programming.learningapp.common.LocalSession;
import com.live.programming.learningapp.databinding.FragmentLogoutBinding;

public class LogoutFragment extends DialogFragment {

    FragmentLogoutBinding binding;
    Button btnConfirm, btnCancel;

    FirebaseAuth auth;
    LocalSession session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLogoutBinding.bind(View.inflate(getContext(),R.layout.fragment_logout,null));
        btnConfirm = binding.btnConfirm;
        btnCancel = binding.btnCancel;

        session = new LocalSession(getActivity());
        auth = FirebaseAuth.getInstance();

        btnConfirm.setOnClickListener(v -> {
            auth.signOut();
            session.logout();
            Snackbar.make(requireContext(),binding.getRoot(),"Successfully logged out", Snackbar.LENGTH_LONG).show();
            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish(); //all the screens pushed in the navigation stack will be removed
        });

        btnCancel.setOnClickListener(v -> {
            Snackbar.make(requireContext(),binding.getRoot(),"Cancelled", Snackbar.LENGTH_LONG).show();
            getDialog().cancel();
        });

        return binding.getRoot();
    }
}