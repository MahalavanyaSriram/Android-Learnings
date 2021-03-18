package com.example.threadandasync;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


/**
* a. Assignment : #06.
* b. File Name : DoWork (com.example.threadandasync).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
public class DoWork implements Runnable{
    int iterations;
    Handler handler;

    DoWork(int iterations, Handler handler){
        this.iterations = iterations;
        this.handler = handler;
    }

    @Override
    public void run() {
        Message startMessage = new Message();
        startMessage.what = AppConstants.START_KEY;
        handler.sendMessage(startMessage);

        for(int i =0 ; i< this.iterations ; i++){

            Double number = HeavyWork.getNumber();
            Message message = new Message();
            message.what = AppConstants.STATUS_PROGRESS;
            Bundle bundle = new Bundle();
            bundle.putInt(AppConstants.PROGRESS_KEY, (Integer) i);
            bundle.putDouble(AppConstants.DATA_KEY, number);
            message.setData(bundle);
            handler.sendMessage(message);

        }
        Message stopMessage = new Message();
        stopMessage.what = AppConstants.STOP_KEY;
        handler.sendMessage(stopMessage);


    }
}
