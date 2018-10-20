package main;

import model.ConcurVector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ConcurVector cv = new ConcurVector(100, 20);
        for(int i = 0; i < cv.dimension(); i++)
            cv.set(i,i);

        System.out.println(cv.norm());

//        Thread.sleep(500);

//        for (int i = 0; i < cv.dimension(); i++)
//            System.out.println(cv.get(i));
    }
}
