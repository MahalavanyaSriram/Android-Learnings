package com.example.threadandasync;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.threadandasync.HeavyWork.getNumber;

/**
* a. Assignment : #06.
* b. File Name : MainActivity (com.example.threadandasync).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/

public class MainActivity extends AppCompatActivity {

    TextView complexity;
    SeekBar seekBar;
    Button threadButton;
    Button asyncButton;
    int complexityValue;

    private ArrayList<Double> numberList;
    ArrayAdapter adapter;

    ExecutorService threadPool;

    ProgressBar progressBar;
    TextView progressNum;
    TextView progressDen;
    TextView average;
    ListView listView;
    TextView averageLabel;
    TextView div;

    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        complexity = findViewById(R.id.complexity);
        complexity.setText(String.valueOf(0));
        seekBar = findViewById(R.id.seekBar);
        threadButton = findViewById(R.id.threadButton);
        asyncButton = findViewById(R.id.asyncButton);
        progressBar = findViewById(R.id.progressBar);
        progressNum = findViewById(R.id.progressNum);
        progressDen = findViewById(R.id.progressDen);
        average = findViewById(R.id.average);
        listView = findViewById(R.id.listView);
        averageLabel = findViewById(R.id.avgLabel);
        div = findViewById(R.id.div);

        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case AppConstants.START_KEY:
                        setVisibility();
                        setInitialValue();
                        Toast.makeText(MainActivity.this,R.string.thread_started,Toast.LENGTH_SHORT).show();
                        disableButtons();
                        break;

                    case AppConstants.STATUS_PROGRESS:
                        progressNum.setText(String.valueOf(msg.getData().getInt(AppConstants.PROGRESS_KEY)+1));
                        progressBar.setProgress(Integer.parseInt(String.valueOf(msg.getData().getInt(AppConstants.PROGRESS_KEY)+1)));
                        numberList.add(msg.getData().getDouble(AppConstants.DATA_KEY));
                        adapter.notifyDataSetChanged();
                        average.setText(String.valueOf(getAverage(numberList)));
                        break;

                    case AppConstants.STOP_KEY:
                        Toast.makeText(MainActivity.this,R.string.thread_completed,Toast.LENGTH_SHORT).show();
                        enableButtons();
                        break;

                }

                return false;
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                complexity.setText(String.valueOf(progress));
                complexityValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        threadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(complexityValue > 0){
                    threadPool = Executors.newFixedThreadPool(2);
                    threadPool.execute(new DoWork(complexityValue, handler));
                }
                else {
                    Toast.makeText(MainActivity.this,R.string.select_complexity,Toast.LENGTH_SHORT).show();
                    setInVisibility();
                }

            }
        });
        asyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(complexityValue > 0){
                    new DoWorkAsyncTask().execute(complexityValue);
                }
                else{
                    Toast.makeText(MainActivity.this,R.string.select_complexity ,Toast.LENGTH_SHORT).show();
                    setInVisibility();
                }
            }
        });

    }
    public double getAverage(ArrayList<Double> list){
        double sum =0.0;
        for(Double number : list){
            sum+=number;
        }
        return (double)sum/list.size();
    }
    public void disableButtons(){
        threadButton.setAlpha(.5f);
        threadButton.setClickable(false);
        asyncButton.setAlpha(.5f);
        asyncButton.setClickable(false);
    }
    public void enableButtons(){
        threadButton.setClickable(true);
        asyncButton.setClickable(true);
        threadButton.setAlpha(1f);
        asyncButton.setAlpha(1f);
    }

    public void setInitialValue(){
        progressNum.setText(String.valueOf(0));
        progressBar.setProgress(0);
        progressBar.setMax(complexityValue);
        progressDen.setText(String.valueOf(complexityValue));
        numberList = new ArrayList<>();
        average.setText(String.valueOf(0.0));
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, numberList);
        listView.setAdapter(adapter);
    }
    public void setVisibility(){
        listView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressNum.setVisibility(View.VISIBLE);
        progressDen.setVisibility(View.VISIBLE);
        average.setVisibility(View.VISIBLE);
        averageLabel.setVisibility(View.VISIBLE);
        div.setVisibility(View.VISIBLE);

    }
    public void setInVisibility(){
        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        progressNum.setVisibility(View.INVISIBLE);
        progressDen.setVisibility(View.INVISIBLE);
        average.setVisibility(View.INVISIBLE);
        averageLabel.setVisibility(View.INVISIBLE);
        div.setVisibility(View.INVISIBLE);

    }

    class DoWorkAsyncTask extends AsyncTask<Integer,Integer, ArrayList<Double>> {

        @Override
        protected void onPreExecute() {
            disableButtons();
            setVisibility();
            setInitialValue();
            Toast.makeText(MainActivity.this,R.string.async_started ,Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<Double> doInBackground(Integer... params) {
            int input = params[0];
            for(int i=0;i<input;i++){
                double number = getNumber();
                numberList.add(number);
                publishProgress(i);
            }
            return numberList;
        }

        @Override
        protected void onPostExecute(ArrayList<Double> doubles) {
            numberList = doubles;
            enableButtons();
            Toast.makeText(MainActivity.this,R.string.async_completed,Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]+1);
            progressNum.setText(String.valueOf(values[0]+1));

            adapter.notifyDataSetChanged();
            average.setText(String.valueOf(getAverage(numberList)));


        }
    }
}
