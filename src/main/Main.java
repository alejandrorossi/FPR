package main;

import model.ConcurVector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ConcurVector cv = new ConcurVector(10, 5);
        cv.set(1);

        Thread.sleep(500);

        for (int i = 0; i < cv.dimension(); i++)
            System.out.println(cv.get(i));

        System.out.println(cv.sum());
    }
}
