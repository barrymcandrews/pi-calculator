package com.bmcandrews.practice;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public Thread thread = new Thread(this::collectData);
    public long samples;
    public long m = 0;

    public Main(long samples) {
        this.samples = samples;
    }

    public static void main(String[] args) throws InterruptedException {
	    long numberOfThreads = Long.valueOf(args[0]);
	    long numberOfSamples = Long.valueOf(args[1]);
	    long samplesPerThread = numberOfSamples / numberOfThreads;

	    System.out.println("Number of Threads:\t" + numberOfThreads);
        System.out.println("Number of Samples:\t" + numberOfSamples);

        ArrayList<Main> mains = new ArrayList<Main>();
        for (int i = 0; i < numberOfThreads; i++) {
            Main m = new Main(samplesPerThread);
            mains.add(m);
            m.thread.start();
        }

        for(int i = 0; i < mains.size(); i++)
            mains.get(i).thread.join();

        long mTotal = 0;
        for (int i = 0; i < mains.size(); i++) {
            mTotal += mains.get(i).m;
        }

        double pi = ((double)mTotal / (double)numberOfSamples) * 4.0;
        System.out.println("Calculated Pi:\t" + pi);

    }

    public void collectData() {
        Random random = new Random();
        for (int i = 0; i < this.samples; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if ((x * x) + (y * y) <= 1) {
                this.m++;
            }
        }
    }
}
