package com.example.tipcalculator;

/**
* a. Assignment : #HW01.
* b. File Name : MainActivity (com.example.tipcalculator).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String INPUT_VALIDATION_MESSAGE = "Enter Bill Total";

    TextView tipResult;
    TextView totalResult;
    EditText userInput;
    RadioGroup radioGroup;
    SeekBar seekBar;
    TextView sliderValue;
    Integer seekBarProgress;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userInput = findViewById(R.id.userInput);
        tipResult = findViewById(R.id.tipResult);
        totalResult = findViewById(R.id.totalResult);
        Button exit = findViewById(R.id.exit);
        seekBar = findViewById(R.id.seekBar);
        radioGroup = findViewById(R.id.radioGroup);
        sliderValue = findViewById(R.id.sliderValue);

        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG,"beforeTextChanged:");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG,"onTextChanged:");
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(isInputSanitized()){
                    Log.d(TAG, "Calling calculate tip and total - change in billing amount");
                    calculateTipAndTotalWithRadioGroup(radioGroup.getCheckedRadioButtonId());
                }else{
                    tipResult.setText(null);
                    totalResult.setText(null);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "Calling calculate tip and total - change in tips");
                calculateTipAndTotalWithRadioGroup(checkedId);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "onProgressChanged:");
                seekBarProgress = progress;
                sliderValue.setText(String.valueOf(progress) + "%");
                if (isInputSanitized() && radioGroup.getCheckedRadioButtonId() == R.id.customRadio) {
                    Double billAmount = Double.parseDouble(userInput.getText().toString());
                    double tipAmount = calculateTip(billAmount,seekBarProgress);
                    displayTipAndTotal(tipAmount, calculateTotal(billAmount, tipAmount));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG,"onStartTrackingTouch:");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG,"onStopTrackingTouch:");

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void calculateTipAndTotalWithRadioGroup(int checkedId){
        if(isInputSanitized()) {
            Double billAmount = Double.parseDouble(userInput.getText().toString());
            Double tipAmount = 0.0;
            Log.d(TAG, "On Check Change Listener:" );
            if(checkedId == R.id.tenPercent){
                tipAmount = calculateTip(billAmount,10);
            }else if(checkedId == R.id.fifteenPercent){
                tipAmount = calculateTip(billAmount,15);
            }else if(checkedId== R.id.eighteenPercent) {
                tipAmount = calculateTip(billAmount,18);
            }
            else if(checkedId == R.id.customRadio){
                seekBarProgress = seekBar.getProgress();
                tipAmount = calculateTip(billAmount,seekBarProgress);
            }
            displayTipAndTotal(tipAmount, calculateTotal(billAmount, tipAmount));
        }
    }

    public void displayTipAndTotal(Double tip, Double totalAmount){
        DecimalFormat decimalFormatter = new DecimalFormat("#.0");
        tipResult.setText(decimalFormatter.format(tip));
        totalResult.setText(decimalFormatter.format(totalAmount));
    }

    public boolean isInputSanitized(){
        if(userInput.getText()==null || userInput.getText().toString()==null || userInput.getText().toString().isEmpty()){
            Toast.makeText(this, INPUT_VALIDATION_MESSAGE, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public double calculateTip(double billAmount , int tipPercent){
        return (double)(billAmount * tipPercent) / 100;
    }

    public double calculateTotal(double billAmount ,double tip){
        return (double)(billAmount + tip) ;
    }


}