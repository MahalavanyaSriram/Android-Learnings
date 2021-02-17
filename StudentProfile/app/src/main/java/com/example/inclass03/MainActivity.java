package com.example.inclass03;

/**
* a. Assignment : #03.
* b. File Name : MainActivity (com.example.inclass03).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private final static int REQ_CODE = 100;
    private final static String TAG = "Main Activity";

    public String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    public String nameRegex = "^[a-zA-Z][a-zA-Z0-9.,]+$";

    TextView dept;
    EditText name;
    EditText email;
    EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.mainActivity_name);

        Button selectButton  = findViewById(R.id.selectButton);
        Button submitButton  = findViewById(R.id.registrationButton);

        dept = findViewById(R.id.dept);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        id = findViewById(R.id.id);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DepartmentActivity.class);
                startActivityForResult(intent , REQ_CODE);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInputSanitized()){
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra(AppConstants.PROFILE_KEY, new Profile(name.getText().toString(),email.getText().toString(),
                            id.getText().toString(),dept.getText().toString()));
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        if(REQ_CODE == 100){
            if(resultCode == RESULT_OK ){
                if(data != null && data.hasExtra(AppConstants.DEPARTMENT_KEY)){
                    Log.d(TAG, "onActivityResult: RESULT_OK");
                    dept.setText(data.getStringExtra(AppConstants.DEPARTMENT_KEY));
                }
            }
            else if(resultCode == RESULT_CANCELED){
                if(isTextFieldsEmpty(dept)){
                    Toast.makeText(this, R.string.deparment_nochange, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, R.string.deparment_selected_retained, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean isInputSanitized(){
        if(isTextFieldsEmpty(name)){
            Toast.makeText(this, R.string.name_empty_toast, Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidName(name.getText().toString())){
            Toast.makeText(this, R.string.name_invalid_toast, Toast.LENGTH_SHORT).show();
            return false;
        }else if(isTextFieldsEmpty(email)){
            Toast.makeText(this, R.string.email_empty_toast, Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidEmailAddress(email.getText().toString())){
            Toast.makeText(this, R.string.email_invalid_toast, Toast.LENGTH_SHORT).show();
            return false;
        }else if(isTextFieldsEmpty(id)){
            Toast.makeText(this, R.string.id_empty_toast, Toast.LENGTH_SHORT).show();
            return false;
        }else if(isTextFieldsEmpty(dept)){
            Toast.makeText(this, R.string.deparment_empty_toast, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isTextFieldsEmpty(View view){
        if(view instanceof EditText){
            EditText textField = (EditText) view;
            return (textField.getText()==null || textField.getText().toString()==null || textField.getText().toString().isEmpty());
        }else{
            TextView textView = (TextView)view;
            return (textView.getText()==null || textView.getText().toString() ==null || textView.getText().toString().isEmpty());
        }
    }

    public boolean isValidEmailAddress(String email) {

        return Pattern.compile(emailRegex).matcher(email).matches();
    }
    public boolean isValidName(String name){
        return Pattern.compile(nameRegex).matcher(name).matches();
    }

}