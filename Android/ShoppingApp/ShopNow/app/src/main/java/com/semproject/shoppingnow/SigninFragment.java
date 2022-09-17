package com.semproject.shoppingnow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.semproject.shoppingnow.databinding.FragmentSigninBinding;

import java.util.Objects;

public class SigninFragment extends Fragment {

    FragmentSigninBinding binding;
    LocalSession session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        binding = FragmentSigninBinding.bind(view);
        session = new LocalSession(requireContext());

        binding.btnSignin.setOnClickListener(v -> {
            String userEmail = String.valueOf(binding.signinTxtEmail.getText());
            String userPass = String.valueOf(binding.signinTxtPassword.getText());
            if(validate(userEmail, userPass))
                logIn(userEmail, userPass);
            else
                callSnackbar("Validation unsuccessful");
        });

        return view;
    }

    private void callSnackbar(String msg) {
        Snackbar.make(requireView(),msg, Snackbar.LENGTH_LONG).show();
    }

    private boolean validate(String email, String password) {
        if(email == null || email.isEmpty()) {
            binding.signinTxtEmail.setError("Required field");
            return false;
        }
        else if(password == null || password.isEmpty()){
            binding.signinTxtPassword.setError("Required field");
            return false;
        }
        else if(password.length() < 6){
            binding.signinTxtPassword.setError("Password should be minimum 6 characters long");
            return false;
        }
        return true;
    }

    private void logIn(String email, String pass) {
        FirebaseAuth auth;
        if (email == null || pass == null)
            callSnackbar("Unsuccessful");
        else
        {
            auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful())
                        {
                            session.setLoginStatus();
                            session.setUserId(task.getResult().getUser().getUid());
                            ((HomeActivity) getActivity()).updateDrawer();
                            binding.signinTxtEmail.setText("");
                            binding.signinTxtPassword.setText("");
                            callSnackbar("Successful");
                            NavController navController = Navigation.findNavController(requireView());
                            navController.navigate(R.id.action_menu_sign_in_to_menu_home);
                        }
                        else
                            callSnackbar("Unsuccessful");
                    })
                    .addOnFailureListener(e -> callSnackbar(e.getMessage()));
        }

    }
}