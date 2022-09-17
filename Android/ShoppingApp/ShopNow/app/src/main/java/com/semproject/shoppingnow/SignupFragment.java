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
import com.google.firebase.firestore.FirebaseFirestore;
import com.semproject.shoppingnow.databinding.FragmentSignupBinding;

import java.util.HashMap;
import java.util.Objects;

public class SignupFragment extends Fragment {


    private FragmentSignupBinding binding;
    private HashMap<String, Object> personDetails;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        binding = FragmentSignupBinding.bind(view);
        personDetails = null;

        binding.submitReg.setOnClickListener(v -> {
            personDetails = fetchInfo();
            if (validate()) {
                createProfile();
                clearFields();
            }
        });
        return view;
    }

    private HashMap<String, Object> fetchInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put(AppConstants.NAME, String.valueOf(binding.fName.getText()).trim());
        info.put(AppConstants.EMAIL, String.valueOf(binding.emailReg.getText()).trim());
        info.put(AppConstants.PASSWORD, String.valueOf(binding.password.getText()));
        info.put(AppConstants.RE_PASSWORD, String.valueOf(binding.cPassword.getText()));
        info.put(AppConstants.PHONE, String.valueOf(binding.pnNo.getText()));
        switch (binding.radioGrpGender.getCheckedRadioButtonId())
        {
            case R.id.male:
                info.put(AppConstants.GENDER, "male");
                break;
            case R.id.female:
                info.put(AppConstants.GENDER, "female");
        }
        return info;
    }

    private boolean validate() {
        if(personDetails != null)
        {
            if(String.valueOf(personDetails.get(AppConstants.NAME)).isEmpty())
            {
                binding.fName.setError("Required field");
                return false;
            }
            else if(String.valueOf(personDetails.get(AppConstants.EMAIL)).isEmpty())
            {
                binding.emailReg.setError("Required field");
                return false;
            }
            else if(String.valueOf(personDetails.get(AppConstants.PASSWORD)).isEmpty())
            {
                binding.password.setError("Required field");
                return false;
            }
            else if(String.valueOf(personDetails.get(AppConstants.PASSWORD)).length() < 6)
            {
                binding.password.setError("Minimum 6 characters required");
                return false;
            }
            else if(String.valueOf(personDetails.get(AppConstants.RE_PASSWORD)).isEmpty())
            {
                binding.cPassword.setError("Required field");
                return false;
            }
            else if(!String.valueOf(personDetails.get(AppConstants.PASSWORD)).equals(personDetails.get(AppConstants.RE_PASSWORD)))
            {
                binding.cPassword.setError("Does not match with password");
                return false;
            }
            else if(String.valueOf(personDetails.get(AppConstants.PHONE)).isEmpty())
            {
                binding.pnNo.setError("Required field");
                return false;
            }
            else
            {
                personDetails.remove(AppConstants.RE_PASSWORD);
                return true;
            }
        }
        return false;
    }

    private void createProfile() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (personDetails != null) {
            auth.createUserWithEmailAndPassword(String.valueOf(personDetails.get(AppConstants.EMAIL)),
                    String.valueOf(personDetails.get(AppConstants.PASSWORD)))
                    .addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
                            personDetails.put(AppConstants.UID, task1.getResult().getUser().getUid());
                            fireStore.collection(AppConstants.COLLECTION_ACCOUNT)
                                    .document(String.valueOf(personDetails.get(AppConstants.UID)))
                                    .set(personDetails)
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            Snackbar.make(SignupFragment.this.requireContext(), binding.getRoot(), "Successful", Snackbar.LENGTH_LONG).show();
                                        } else {
                                            Snackbar.make(SignupFragment.this.requireContext(), binding.getRoot(), "Profile creation unsuccessful", Snackbar.LENGTH_LONG).show();
                                        }
                                    });
                        } else {
                            Snackbar.make(SignupFragment.this.requireContext(), binding.getRoot(), task1.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(e -> Snackbar.make(SignupFragment.this.requireContext(), binding.getRoot(), e.getMessage(), Snackbar.LENGTH_LONG).show());
        }
    }

    private void clearFields()
    {
        binding.fName.setText("");
        binding.fName.setError(null);
        binding.emailReg.setText("");
        binding.emailReg.setError(null);
        binding.password.setText("");
        binding.password.setError(null);
        binding.cPassword.setText("");
        binding.cPassword.setError(null);
        binding.pnNo.setText("");
        binding.pnNo.setError(null);
    }
}

