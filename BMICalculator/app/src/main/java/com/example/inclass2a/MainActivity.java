package com.example.inclass2a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText lb = findViewById(R.id.lbEditText);
        EditText feet = findViewById(R.id.feetEditText);
        EditText inches = findViewById(R.id.inchesEditText);;
        TextView bmi = findViewById(R.id.bmi);
        TextView category = findViewById(R.id.category);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inchesValue;
                if (lb.getText().toString().isEmpty() && !feet.getText().toString().isEmpty()) {
                    lb.setError("Field can not be empty");
                    Toast.makeText(getApplicationContext(), "Enter Weight", Toast.LENGTH_LONG).show();
                }
                else if (feet.getText().toString().isEmpty() && !lb.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter Height", Toast.LENGTH_LONG).show();
                    feet.setError("Field can not be empty");
                }
                else if ((feet.getText().toString().isEmpty() && lb.getText().toString().isEmpty()) || (feet.getText().toString().isEmpty() && lb.getText().toString().isEmpty() && inches.getText().toString().isEmpty())){
                    Toast.makeText(getApplicationContext(),"Enter Weight and height", Toast.LENGTH_LONG).show();
                    feet.setError("Height can not be empty");
                    lb.setError("Weight can not be empty");

                }
                else {
                int feetValue = Integer.parseInt(String.valueOf(feet.getText()));
                if(!inches.getText().toString().isEmpty())
                    inchesValue  = (feetValue * 12)+ Integer.parseInt(String.valueOf(inches.getText()));
                else
                    inchesValue = (feetValue * 12);
                Log.d("In class 2a", "inchesValue: "+inchesValue);
                double calculated_BMI = ((double)(Float.parseFloat(String.valueOf(lb.getText())) / (inchesValue * inchesValue)) * 703);
                Log.d("In class 2a", "bmi: "+calculated_BMI);
                bmi.setText(R.string.bmiMessage + String.valueOf((double) (Math.round(calculated_BMI * 100.0)/100.0)));

                    if (calculated_BMI < 18.5)
                        category.setText(R.string.uderweightMessage);
                    else if (calculated_BMI <= 24.9)
                        category.setText(R.string.normalweightMessage);
                    else if (calculated_BMI <= 29.9)
                        category.setText(R.string.overweightMesssage);
                    else
                        category.setText(R.string.obeseMessage);

                    Toast.makeText(getApplicationContext(), "BMI Calculated", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}