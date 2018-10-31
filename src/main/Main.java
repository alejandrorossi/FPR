package main;

import model.ConcrVector;
import model.ConcurVectorFantasma;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        //TODO: probar otros metodos del ConcurVectorFantasma!

        ConcrVector cv = new ConcrVector(5, 1);
        cv.set(1);

        ConcrVector cvOrigen = new ConcrVector(5, 3);
        cvOrigen.set(2);

        ConcrVector cvMask = new ConcrVector(5, 2);
        cvMask.set(0, -1);
        cvMask.set(1, -1);
        cvMask.set(2, 0);
        cvMask.set(3, 0);
        cvMask.set(4, 0);

        cv.assign(cvMask, cvOrigen);

        for (int i = 0; i < cv.dimension(); i++)
            System.out.println("En posicion "+i+ ": "+ cv.get(i));

        System.out.println(cv.sum());
    }
}
