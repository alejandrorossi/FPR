package model;

public class Worker implements Runnable {
    private Buffer buffer;

    public Worker(Buffer b) {
        this.buffer = b;
    }

    @Override
    public void run() {

    }
}
