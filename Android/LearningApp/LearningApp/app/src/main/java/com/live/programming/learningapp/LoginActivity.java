package com.live.programming.learningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.live.programming.learningapp.common.LocalSession;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout emailLayout, passLayout;
    TextInputEditText emailInput, passInput;
    Button btnLogin;
    LocalSession session;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailLayout = findViewById(R.id.email_layout);
        emailInput = findViewById(R.id.email_input);
        passLayout = findViewById(R.id.pwd_layout);
        passInput = findViewById(R.id.pwd_input);
        btnLogin = findViewById(R.id.btn_login);
        session = new LocalSession(LoginActivity.this);

        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> {
            emailLayout.setError(null);
            passLayout.setError(null);

            String email = String.valueOf(emailInput.getText()).trim();
            String pass = String.valueOf(passInput.getText()).trim();
            if(validateFields(email, pass)){
                //Login procedures to followed here
                auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(task -> {
                           if (task.isSuccessful())
                           {
                               FirebaseUser fUser = auth.getCurrentUser();
                               if(fUser.isEmailVerified())
                               {
                                   callSnackbar("Login successful");
                                   session.setLoginStatus();
                                   startActivity(new Intent(LoginActivity.this, DrawerHomeActivity.class));
                                   LoginActivity.this.finish();
                               }
                               else
                               {
                                   callSnackbar("Kindly verify your email");
                               }
                           }
                           else
                           {
                               callSnackbar("Login failed");
                           }
                        });
            }
        });

    }

    private void callSnackbar(String msg) {
        Snackbar.make(LoginActivity.this, findViewById(R.id.login_layout), msg, Snackbar.LENGTH_SHORT).show();
    }

    private boolean validateFields(String email, String pass) {
        if(email.isEmpty())
            emailLayout.setError("Please provide your email");
        else if(pass.isEmpty())
            passLayout.setError("Provide your password");
        else if(pass.length() < 6)
            passLayout.setError("Password should contain at least 6 characters");
        else
            return true;
        return false;
    }

    public void navOnSignUp(View view) {
        //code to route to signup activity
        startActivity(new Intent(LoginActivity.this,SigninActivity.class));
    }
}