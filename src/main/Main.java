package main;

import model.ConcurVector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ConcurVector cv = new ConcurVector(4, 2);

        cv.set(0, 1);
        cv.set(1, 1);
        cv.set(2, 2);
        cv.set(3, 5);


//        ConcurVector cv2 = new ConcurVector(4, 2);
//
//        cv2.set(0, 2);
//        cv2.set(1, 2);
//        cv2.set(2, 2);
//        cv2.set(3, 2);
//        cv.mul(cv2);

        Thread.sleep(500);

//        for (int i = 0; i < cv.dimension(); i++)
            System.out.println(cv.max());
    }
}
