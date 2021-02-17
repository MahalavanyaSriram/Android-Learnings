package com.example.inclass03;

/**
* a. Assignment : #03.
* b. File Name : ProfileActivity (com.example.inclass03).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(R.string.profileActivity_name);

        TextView name = findViewById(R.id.profileName);
        TextView email = findViewById(R.id.profileEmail);
        TextView id = findViewById(R.id.profileId);
        TextView dept = findViewById(R.id.profileDept);


        if(getIntent()!=null && getIntent().getExtras()!=null && getIntent().hasExtra(AppConstants.PROFILE_KEY)){
            Profile profile = getIntent().getParcelableExtra(AppConstants.PROFILE_KEY);
            name.setText(profile.getName());
            email.setText(profile.getEmail());
            id.setText(profile.getId());
            dept.setText(profile.getDepartment());
        }

    }
}