package main;

import model.ConcurVector;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        //TODO: probar otros metodos del ConcurVector!
        //Algunos metodos no andan! jaja

        ConcurVector cv = new ConcurVector(4, 4);
        cv.set(1);

        ConcurVector cvOrigen = new ConcurVector(4, 2);
        cvOrigen.set(2);

        ConcurVector cvMask = new ConcurVector(4, 2);
        cvMask.set(0, -1);
        cvMask.set(1, -1);
        cvMask.set(2, 0);
        cvMask.set(3, 0);

        //cv.assign(cvMask, cvOrigen);

        Thread.sleep(500);

        for (int i = 0; i < cv.dimension(); i++)
            System.out.println("En posicion "+i+ ": "+ cv.get(i));

        //System.out.println(cv.sum());
    }
}
