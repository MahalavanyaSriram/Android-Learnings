package com.example.threadandasync;

import java.util.Random;


/**
* a. Assignment : #06.
* b. File Name : HeavyWork (com.example.threadandasync).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
public class HeavyWork {
	public static final long DELAY_MILLI_SECS = 2000;

	public static double getNumber(){
		addSomeDelay(DELAY_MILLI_SECS);
		Random rand = new Random();
		return rand.nextDouble();
	}

	private static void addSomeDelay(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}