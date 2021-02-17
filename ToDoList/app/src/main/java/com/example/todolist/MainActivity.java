package com.example.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
* a. Assignment : #HW02.
* b. File Name : MainActivity (com.example.todolist).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
public class MainActivity extends AppCompatActivity {

    public static final int CREATE_REQ_CODE = 100;
    public static final int DISPLAY_REQ_CODE = 200;

    private  static final String TAG = "MainActivity";

    ArrayList<Task> taskList;
    TextView taskListCount;
    TextView taskName;
    TextView dueDate;
    TextView priority;
    String[] nameArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_activity_name);

        Button create = findViewById(R.id.createTaskButton);
        Button view = findViewById(R.id.viewTaskButton);

        taskList = new ArrayList<>();
        taskListCount = findViewById(R.id.taskList);
        taskName = findViewById(R.id.taskName);
        dueDate = findViewById(R.id.dueDate);
        priority = findViewById(R.id.priority);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startActivityForResult(intent, CREATE_REQ_CODE);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameArray = new String[taskList.size()];
                taskList.stream()
                        .map(Task::getName)
                        .collect(Collectors.toList()).toArray(nameArray);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle(R.string.select_task_label).setItems(taskList.stream()
                        .map(Task::getName)
                        .collect(Collectors.toList()).toArray(nameArray), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, DisplayTaskActivity.class);
                        intent.putExtra(AppConstants.DISPLAY_TASK_KEY, taskList.get(which));
                        startActivityForResult(intent,DISPLAY_REQ_CODE);

                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertBuilder.create().show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CREATE_REQ_CODE){
            if(resultCode == RESULT_OK){
                if(data != null && data.hasExtra(AppConstants.NEW_TASK_KEY)){
                    Log.d(TAG, "onCreateActivityResult: RESULT_OK");
                    taskList.add((Task) data.getSerializableExtra(AppConstants.NEW_TASK_KEY));
                    taskList = sortAndUpdate(taskList);
                    Toast.makeText(MainActivity.this,R.string.task_created_successfully, Toast.LENGTH_SHORT).show();
                }
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(MainActivity.this,R.string.no_task_created, Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode == DISPLAY_REQ_CODE){
            if(resultCode == RESULT_OK){
                if(data != null && data.hasExtra(AppConstants.DELETE_TASK_KEY)){
                    Log.d(TAG, "onDeleteActivityResult: RESULT_OK");
                    taskList.remove((Task) data.getSerializableExtra(AppConstants.DELETE_TASK_KEY));
                    taskList = sortAndUpdate(taskList);
                    Toast.makeText(MainActivity.this,R.string.task_deleted_successfully, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public ArrayList<Task> sortAndUpdate(ArrayList<Task> taskList){
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        updateView(taskList);
        return taskList;
    }
    public void updateView(ArrayList<Task> taskList){
        taskListCount.setText(new StringBuilder().append(getResources().getString(R.string.task_count_label_1)).append(" ").append(taskList.size()).append(" ").append(getResources().getString(R.string.task_count_label_2)).toString());
        if(taskList.size() != 0){
            taskName.setText(taskList.get(0).getName());
            try {
                dueDate.setText(Util.dateFormatter(taskList.get(0).getDate()));
            } catch (ParseException parseException) {
                Log.e(TAG, "updateView: ", parseException);
            }
            priority.setText(taskList.get(0).getPriority());
        }
        else{
            taskName.setText(R.string.none);
            dueDate.setText(null);
            priority.setText(null);
        }
    }
}
