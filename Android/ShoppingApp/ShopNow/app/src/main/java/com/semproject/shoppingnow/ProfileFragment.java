package com.semproject.shoppingnow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.semproject.shoppingnow.databinding.FragmentProfileBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private LocalSession session;
    private DocumentReference userDoc;
    private Uri imagePath;
    enum invisibility{
        INVISIBLE_BOTH, INVISIBLE_INFO, INVISIBLE_PHOTO;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        session = new LocalSession(requireActivity());
        binding = FragmentProfileBinding.bind(view);
        userDoc = FirebaseFirestore.getInstance().collection(AppConstants.COLLECTION_ACCOUNT).document(session.getUserId());
        imagePath = null;

        binding.fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(galleryIntent, "Select an image"),1);
                binding.btnProfileUpdatePhoto.setVisibility(View.VISIBLE);
            }
        });

        binding.editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.profileName.setEnabled(true);
                binding.btnProfileUpdateInfo.setVisibility(View.VISIBLE);
            }
        });

        binding.editGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.profileGender.setEnabled(true);
                binding.btnProfileUpdateInfo.setVisibility(View.VISIBLE);
            }
        });

        binding.editPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.profilePhone.setEnabled(true);
                binding.btnProfileUpdateInfo.setVisibility(View.VISIBLE);
            }
        });

        binding.btnProfileUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = String.valueOf(binding.profileName.getText());
                String phone = String.valueOf(binding.profilePhone.getText());
                String gender = String.valueOf(binding.profileGender.getText());

                if(name.isEmpty() || phone.isEmpty() || gender.isEmpty() ||
                        (!gender.toLowerCase().equals("male") && !gender.toLowerCase().equals("female")))
                {
                    Snackbar.make(v, "Validation unsuccessful", Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    HashMap<String, Object> newData = new HashMap<>();
                    newData.put(AppConstants.NAME, name);
                    newData.put(AppConstants.PHONE, phone);
                    newData.put(AppConstants.GENDER, gender);

                    userDoc.update(newData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    Snackbar.make(requireView(), "Update successful", Snackbar.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Snackbar.make(requireView(), "Update unsuccessful", Snackbar.LENGTH_LONG).show();
                                }
                            });
                }
                updateProfile(invisibility.INVISIBLE_INFO);
            }
        });

        binding.btnProfileUpdatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String randomKey;
                String extension, fileName;
                StorageReference storage;
                final ProgressDialog progress;

                if(imagePath != null)
                {
                    randomKey = UUID.randomUUID().toString();
                    extension = imagePath.toString().substring(imagePath.toString().lastIndexOf('.'));
                    fileName = randomKey + extension;
                    storage = FirebaseStorage.getInstance().getReference().child(AppConstants.PROFILE_PICTURE + "/" + fileName);

                    progress = new ProgressDialog(getContext());
                    progress.setTitle("Uploading image");
                    progress.show();

                    storage.putFile(imagePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                            progress.dismiss();
                            if(task.isComplete())
                            {
                                storage.getDownloadUrl().addOnCompleteListener(task1 -> {
                                    userDoc.update(AppConstants.PROFILE_PICTURE, task1.getResult().toString())
                                            .addOnCompleteListener(task2 -> {
                                                updateProfile(invisibility.INVISIBLE_PHOTO);
                                                Snackbar.make(requireView(), "Successfully uploaded", Snackbar.LENGTH_LONG).show();
                                                    })
                                            .addOnFailureListener(e -> {
                                                storage.delete();
                                                Snackbar.make(requireView(), "Update failed", Snackbar.LENGTH_LONG).show();
                                            });

                                });
                            }
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                            int progressPercent = (int)((snapshot.getBytesTransferred()/snapshot.getTotalByteCount()) * 100);
                            progress.setMessage(progressPercent + "%");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            progress.dismiss();
                            Snackbar.make(requireView(),"Upload failed", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        updateProfile(invisibility.INVISIBLE_BOTH);
    }

    public void updateProfile(invisibility choice)
    {
        if(session.getLoginStatus())
        {
            userDoc.get()
                    .addOnSuccessListener(documentSnapshot -> {

                        binding.profileName.setText((String)documentSnapshot.get(AppConstants.NAME));
                        binding.profileEmail.setText((String)documentSnapshot.get(AppConstants.EMAIL));
                        binding.profilePhone.setText((String)documentSnapshot.get(AppConstants.PHONE));
                        binding.profileGender.setText((String)documentSnapshot.get(AppConstants.GENDER));
                        if(documentSnapshot.contains(AppConstants.PROFILE_PICTURE))
                        {
                            Glide.with(requireContext())
                                    .load(documentSnapshot.getString(AppConstants.PROFILE_PICTURE))
                                    .error(android.R.drawable.ic_menu_report_image)
                                    .into(binding.profileImage);
                        }
                        ((HomeActivity)getActivity()).updateDrawer();
                    })
                    .addOnFailureListener(e -> Snackbar.make(requireView(), e.getMessage(), Snackbar.LENGTH_LONG).show());

        }
        else
            Snackbar.make(requireView(), "Invalid user ID", Snackbar.LENGTH_LONG).show();

        binding.profileName.setEnabled(false);
        binding.profilePhone.setEnabled(false);
        binding.profileGender.setEnabled(false);

        if(choice == invisibility.INVISIBLE_INFO)
            binding.btnProfileUpdateInfo.setVisibility(View.INVISIBLE);
        else if(choice == invisibility.INVISIBLE_PHOTO)
            binding.btnProfileUpdatePhoto.setVisibility(View.INVISIBLE);
        else
        {
            binding.btnProfileUpdateInfo.setVisibility(View.INVISIBLE);
            binding.btnProfileUpdatePhoto.setVisibility(View.INVISIBLE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            imagePath = data.getData();
            try {
                binding.profileImage.setImageURI(imagePath);
            }catch (Exception exc)
            {
                imagePath = null;
                Snackbar.make(requireView(),exc.getMessage(),Snackbar.LENGTH_LONG).show();
            }
        }
    }
}