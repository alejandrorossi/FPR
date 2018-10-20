package main;

import model.ConcurVector;

public class Main {

    public static void main(String[] args) {
        ConcurVector cv = new ConcurVector(12, 6);

        cv.set(0, 1);
        cv.set(1, 1);
        cv.set(2, 1);
        cv.set(3, 1);
        cv.set(4, 1);
        cv.set(5, 1);
        cv.set(6, 1);
        cv.set(7, 6);
        cv.set(8, 1);
        cv.set(9, 1);
        cv.set(10, 1);
        cv.set(11, 2);

        System.out.println("resultado final: " + cv.sum());
    }
}
