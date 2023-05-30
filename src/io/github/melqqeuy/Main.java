package io.github.melqqeuy;

import java.util.Arrays;

public class Main {

    private static final int SIZE = 10_000_000;
    private static final int HALF_SIZE = SIZE/2;
    private static final float[] arr1 = new float[SIZE];
    private static final float[] arr2 = new float[SIZE];


    public static void main(String[] args) {
        for (int i = 0; i < arr1.length; i++) arr1[i] = 1f;
        long beginTime = System.nanoTime();
        calcArray(arr1);
        float deltaTime = (System.nanoTime() - beginTime) * 1e-9f;
        System.out.println(deltaTime + " sec.");

        for (int i = 0; i < arr2.length; i++) arr2[i] = 1f;
        beginTime = System.nanoTime();
        calcArrayTwoThreads(arr2);
        deltaTime = (System.nanoTime() - beginTime) * 1e-9f;
        System.out.println(deltaTime + " sec.");

        if (Arrays.equals(arr1, arr2)) {
            System.out.println("Массивы одинаковые");
        }
        else {
            System.out.println("Массивы разные");
        }
    }

    private static void calcArray(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    private static void calcArrayTwoThreads(float[] arr) {
        final  float[] a1 = new float[HALF_SIZE];
        System.arraycopy(arr, 0, a1, 0, HALF_SIZE);
        CalcThread thread1 = new CalcThread(a1, 0);

        final float[] a2 = new float[HALF_SIZE];
        System.arraycopy(arr, HALF_SIZE, a2, 0, HALF_SIZE);
        CalcThread thread2 = new CalcThread(a2, HALF_SIZE);

        try {
            thread1.join();;
            thread2.join();;
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.arraycopy(a1, 0, arr,0, HALF_SIZE);
        System.arraycopy(a2, 0, arr, HALF_SIZE, HALF_SIZE);
    }

}
