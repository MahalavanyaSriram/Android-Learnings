package com.example.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;

/**
* a. Assignment : #HW02.
* b. File Name : DisplayTaskActivity (com.example.todolist).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
public class DisplayTaskActivity extends AppCompatActivity {

    public static final String TAG = "Display Task Activity";

    TextView displayName;
    TextView displayDate;
    TextView displayPriority;
    Button displayCancel;
    Button displayDelete;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);
        setTitle(R.string.display_activity_name);

        displayName = findViewById(R.id.displayName);
        displayDate = findViewById(R.id.displayDate);
        displayPriority = findViewById(R.id.displayPriority);
        displayCancel = findViewById(R.id.displayCancelButton);
        displayDelete = findViewById(R.id.displayDeleteButton);

        if(getIntent()!=null && getIntent().getExtras()!=null && getIntent().hasExtra(AppConstants.DISPLAY_TASK_KEY)){
            task = (Task) getIntent().getSerializableExtra(AppConstants.DISPLAY_TASK_KEY);
            displayName.setText(task.getName());
            try {
                displayDate.setText(Util.dateFormatter(task.getDate()));
            } catch (ParseException parseException) {
                Log.e(TAG, "onCreate: ",parseException );
            }
            displayPriority.setText(task.getPriority());
        }

        displayCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        displayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DisplayTaskActivity.this)
                        .setTitle(R.string.delete_task_label)
                        .setMessage(R.string.delete_task_msg)
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra(AppConstants.DELETE_TASK_KEY, task);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
                builder.show();

            }
        });


    }
}
