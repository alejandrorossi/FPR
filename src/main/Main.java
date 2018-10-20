package main;

import model.ConcurVector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ConcurVector cv = new ConcurVector(12, 6);

        cv.set(0, 1);
        cv.set(1, 1);
        cv.set(2, 123);
        cv.set(3, 1);
        cv.set(4, 1);
        cv.set(5, 1);
        cv.set(6, 5);
        cv.set(7, 6);
        cv.set(8, 5);
        cv.set(9, 1);
        cv.set(10, 1);
        cv.set(11, 2);

        cv.set(99);

        ConcurVector cv2 = new ConcurVector(12, 6);

        cv2.set(0, 1);
        cv2.set(1, 1);
        cv2.set(2, 123);
        cv2.set(3, 1);
        cv2.set(4, 1);
        cv2.set(5, 1);
        cv2.set(6, 5);
        cv2.set(7, 6);
        cv2.set(8, 5);
        cv2.set(9, 1);
        cv2.set(10, 1);
        cv2.set(11, 2);

        cv.assign(cv2);

        Thread.sleep(500);

        for (int i = 0; i < cv.dimension(); i++)
            System.out.println(cv.get(i));
    }
}
