package com.live.programming.learningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.live.programming.learningapp.common.AppConstants;

import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {

    //references to the views
    ScrollView srcView;
    Button submit;
    EditText firstName, lastName, email, password, rePassword, univName, course;
    RadioGroup gender;

    //Creating a map object to store the details of a person
    HashMap<String, Object> personDetails;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //Binding reference to the views
        srcView = findViewById(R.id.scroll_layout);
        firstName = findViewById(R.id.txt_firstname);
        lastName = findViewById(R.id.txt_lastname);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        rePassword = findViewById(R.id.txt_re_password);
        univName = findViewById(R.id.txt_univ_name);
        course = findViewById(R.id.txt_course);
        gender = findViewById(R.id.radio_group_gender);
        submit = findViewById(R.id.btn_submit);

        //Instantiating the map object
        personDetails = new HashMap<String, Object>();

        //initialising firebase auth service
        auth = FirebaseAuth.getInstance();

        //Event listener for radio group
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId)
                {
                    case R.id.radio_button_male:
                        personDetails.put(AppConstants.GENDER,"male");
                        break;
                    case R.id.radio_button_female:
                        personDetails.put(AppConstants.GENDER,"female");
                }
            }
        });

        //event listener for button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetching data from view elements to record object
                personDetails.put(AppConstants.F_NAME, String.valueOf(firstName.getText()).trim());
                personDetails.put(AppConstants.L_NAME, String.valueOf(lastName.getText()).trim());
                personDetails.put(AppConstants.EMAIL, String.valueOf(email.getText()).trim());
                personDetails.put(AppConstants.PASSWORD, String.valueOf(password.getText()));
                personDetails.put(AppConstants.RE_PASSWORD, String.valueOf(rePassword.getText()));
                personDetails.put(AppConstants.UNIVERSITY, String.valueOf(univName.getText()).trim());
                personDetails.put(AppConstants.COURSE, String.valueOf(course.getText()).trim());

                //validating information
                if(validate(personDetails))    //on success
                {
                    auth.createUserWithEmailAndPassword(String.valueOf(personDetails.get(AppConstants.EMAIL)),
                            String.valueOf(personDetails.get(AppConstants.PASSWORD)))
                            .addOnCompleteListener(task -> {
                                if(task.isSuccessful())
                                {
                                    auth.getCurrentUser().sendEmailVerification();
                                    personDetails.put(AppConstants.UID, auth.getUid());
                                    personDetails.put(AppConstants.NAME, personDetails.get(AppConstants.F_NAME) +
                                            " " + personDetails.get(AppConstants.L_NAME));
                                    personDetails.remove(AppConstants.F_NAME);
                                    personDetails.remove(AppConstants.L_NAME);
                                    personDetails.remove(AppConstants.PASSWORD);
                                    personDetails.remove(AppConstants.RE_PASSWORD);

                                    createProfile(personDetails);
                                }
                                else
                                {
                                    Snackbar.make(SigninActivity.this,srcView,task.getException().getMessage(),Snackbar.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(e -> Snackbar.make(SigninActivity.this,srcView,e.getMessage(),Snackbar.LENGTH_LONG).show());

                    firstName.setText("");
                    lastName.setText("");
                    email.setText("");
                    password.setText("");
                    rePassword.setText("");
                    univName.setText("");
                    course.setText("");
                    gender.clearCheck();
                }
                else    //on validation fail
                    Snackbar.make(SigninActivity.this,srcView,"Unsuccessful",Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void createProfile(HashMap<String, Object> userMap)
    {
        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
        fireStore.collection("accounts")
                .document(String.valueOf(userMap.get(AppConstants.UID)))
                .set(userMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        Snackbar.make(SigninActivity.this,srcView,"Successful",Snackbar.LENGTH_LONG).show();
                        startActivity(new Intent(SigninActivity.this, LoginActivity.class));
                        SigninActivity.this.finish();
                    }
                    else
                    {
                        Snackbar.make(SigninActivity.this,srcView,"Profile creation unsuccessful",Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    //method for validation
    private boolean validate(Map<String, Object> rec){
        //validating first name
        if(String.valueOf(rec.get(AppConstants.F_NAME)).isEmpty())
        {
            firstName.setError("Required field");
            return false;
        }
        else if(String.valueOf(rec.get(AppConstants.F_NAME)).split(" ").length != 1)
        {
            firstName.setError("Enter only one word");
            return false;
        }

        //validating last name
        if(String.valueOf(rec.get(AppConstants.L_NAME)).isEmpty())
        {
            lastName.setError("Required field");
            return false;
        }
        else if(String.valueOf(rec.get(AppConstants.L_NAME)).split(" ").length != 1)
        {
            lastName.setError("Enter only one word");
            return false;
        }

        //validating email
        if(String.valueOf(rec.get(AppConstants.EMAIL)).isEmpty())
        {
            email.setError("required field");
            return false;
        }
        else if(!characterValidation(String.valueOf(rec.get(AppConstants.EMAIL)),new char[]{'!','#',' '}))
        {
            email.setError("Invalid character");
            return false;
        }

        //validating password
        if(String.valueOf(rec.get(AppConstants.PASSWORD)).isEmpty())
        {
            password.setError("required field");
            return false;
        }
        else if(String.valueOf(rec.get(AppConstants.PASSWORD)).length() < 6)
        {
            password.setError("minimum 6 characters");
            return false;
        }

        //validating retyped password
        if(String.valueOf(rec.get(AppConstants.RE_PASSWORD)).isEmpty())
        {
            rePassword.setError("required field");
            return false;
        }
        else if(! (String.valueOf(rec.get(AppConstants.RE_PASSWORD))
                .equals(String.valueOf(rec.get(AppConstants.PASSWORD)))))
        {
            rePassword.setError("Does not match");
            return false;
        }

        //validating university name
        if(!String.valueOf(rec.get(AppConstants.UNIVERSITY)).isEmpty())
            if(!characterValidation(String.valueOf(rec.get(AppConstants.UNIVERSITY)),new char[]{'!','@','#','?'}))
            {
                univName.setError("Invalid character");
                return false;
            }

        //validating course name
        if(!String.valueOf(rec.get(AppConstants.COURSE)).isEmpty())
            if(!characterValidation(String.valueOf(rec.get("course")),new char[]{'!','@','#','?'}))
            {
                course.setError("Invalid character");
                return false;
            }
        return true;
    }

    //method for checking invalid characters in a string
    private boolean characterValidation(String str, char[] ch){
        if(str == null || ch == null)
            return false;
        for(char c:ch){
            if(str.indexOf(c) != -1)
                return false;
        }
        return true;
    }
}