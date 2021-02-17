package com.example.todolist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
* a. Assignment : #HW02.
* b. File Name : CreateTaskActivity (com.example.todolist).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
public class CreateTaskActivity extends AppCompatActivity {

    private  static final String TAG = "Create Task Activity";

    Task task;
    EditText taskName;
    TextView setDate;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        setTitle(R.string.create_activity_name);
        task = new Task();

        Button setDateButton = findViewById(R.id.setDateButton);
        taskName =findViewById(R.id.enterTaskName);
        setDate = findViewById(R.id.setDate);
        radioGroup = findViewById(R.id.radioGroup);
        Button submit = findViewById(R.id.submitButton);
        Button cancel = findViewById(R.id.cancelButton);

        Calendar calender = new GregorianCalendar();
        calender.setTime(new Date());
        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(CreateTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        setDate.setText(new StringBuffer().append(checkDigit(month+1)).append("/").append(checkDigit(dayOfMonth)).append("/").append(year).toString());
                    }
                },calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH));
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                datePicker.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(isInputSanitized()) {
                    radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                    task.setName(taskName.getText().toString());
                    try {
                        task.setDate(Util.dateFormatter(setDate.getText().toString()));
                    } catch (ParseException parseException) {
                        Log.e(TAG, "updateView: ", parseException);
                    }
                    task.setPriority(radioButton.getText().toString());
                    intent.putExtra(AppConstants.NEW_TASK_KEY, task);
                    setResult(RESULT_OK, intent);
                    Log.d(TAG, "sending back");
                    finish();
                }
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

    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }

    public boolean isInputSanitized(){
        if(taskName.getText()==null || taskName.getText().toString()==null || taskName.getText().toString().isEmpty()){
            Toast.makeText(this, R.string.task_empty_toast, Toast.LENGTH_SHORT).show();
            return false;
        }
        else  if(setDate.getText() == null || setDate.getText().toString() == null || setDate.getText().toString().isEmpty()){
            Toast.makeText(this, R.string.date_empty_toast, Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

}