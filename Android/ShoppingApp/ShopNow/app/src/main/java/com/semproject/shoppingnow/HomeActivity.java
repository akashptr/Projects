package com.semproject.shoppingnow;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.semproject.shoppingnow.databinding.ActivityHomeBinding;

import java.io.ObjectStreamField;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    NavigationView navigationView;
    private ActivityHomeBinding binding;
    private LocalSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);

        drawer = binding.drawerLayout;
        navigationView = binding.navView;

        session = new LocalSession(HomeActivity.this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.menu_home, R.id.menu_sign_up, R.id.menu_sign_in,
                R.id.menu_profile, R.id.menu_logout, R.id.menu_order)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        updateDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateDrawer() {
        if(session.getUserId() != null)
        {
            String userId = session.getUserId();
            FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
            fireStore.collection(AppConstants.COLLECTION_ACCOUNT).document(userId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if(documentSnapshot.contains(AppConstants.PROFILE_PICTURE))
                        {
                                Glide.with(this)
                                        .load(documentSnapshot.getString(AppConstants.PROFILE_PICTURE))
                                        .into(((ImageView)navigationView.getHeaderView(0).findViewById(R.id.image_profile_pic)));
                            navigationView.getHeaderView(0).findViewById(R.id.image_profile_pic).setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            navigationView.getHeaderView(0).findViewById(R.id.image_profile_pic).setVisibility(View.INVISIBLE);
                        }

                        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.text_user_name))
                                .setText(documentSnapshot.getString(AppConstants.NAME));
                        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.text_user_email))
                                .setText(documentSnapshot.getString(AppConstants.EMAIL));
                        navigationView.getMenu().findItem(R.id.menu_sign_up).setVisible(false);
                        navigationView.getMenu().findItem(R.id.menu_sign_in).setVisible(false);
                        navigationView.getMenu().findItem(R.id.menu_profile).setVisible(true);
                        navigationView.getMenu().findItem(R.id.menu_logout).setVisible(true);
                        navigationView.getMenu().findItem(R.id.menu_order).setVisible(true);
                    }).addOnFailureListener(e -> Snackbar.make(HomeActivity.this, binding.appBarHome.getRoot(), e.getMessage(), Snackbar.LENGTH_LONG).show());
        }
        else
        {
            navigationView.getHeaderView(0).findViewById(R.id.image_profile_pic).setVisibility(View.INVISIBLE);
            ((TextView)navigationView.getHeaderView(0).findViewById(R.id.text_user_name))
                    .setText("Sign in to see your name");
            ((TextView)navigationView.getHeaderView(0).findViewById(R.id.text_user_email))
                    .setText("Sign in to see your email");
            navigationView.getMenu().findItem(R.id.menu_sign_up).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_sign_in).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.menu_order).setVisible(false);
        }

    }
}