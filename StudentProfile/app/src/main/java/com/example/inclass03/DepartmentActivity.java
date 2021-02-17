package com.example.inclass03;

/**
* a. Assignment : #03.
* b. File Name : DepartmentActivity (com.example.inclass03).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DepartmentActivity extends AppCompatActivity {

    RadioGroup group;
    Button select;
    Button cancel;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        setTitle(R.string.deptActivity_name);

        group = findViewById(R.id.radioGroup);
        select = findViewById(R.id.selectButton2);
        cancel = findViewById(R.id.cancelButton);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton = findViewById(group.getCheckedRadioButtonId());
                Intent intent = new Intent();
                intent.putExtra(AppConstants.DEPARTMENT_KEY,radioButton.getText());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();

            }
        });

    }

}