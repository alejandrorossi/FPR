package main;

import model.SeqVector;

public class Main {

    public static void main(String[] args) {
        SeqVector sv = new SeqVector(3);

        sv.set(0, 1);
        sv.set(1, 2);
        sv.set(2, 3);

        System.out.println(sv.sum());
    }
}
