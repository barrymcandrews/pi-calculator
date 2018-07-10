package com.bmcandrews.practice;

import java.util.concurrent.ThreadLocalRandom;

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

        Main[] mains = new Main[(int)numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            mains[i] = new Main(samplesPerThread);
            mains[i].thread.start();
        }

        long mTotal = 0;
        for (int i = 0; i < mains.length; i++) {
            mains[i].thread.join();
            mTotal += mains[i].m;
        }

        double pi = ((double)mTotal / (double)numberOfSamples) * 4.0;
        System.out.println("Calculated Pi:\t" + pi);
    }

    public void collectData() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < this.samples; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if ((x * x) + (y * y) <= 1) {
                this.m++;
            }
        }
    }
}
