package model;

import java.util.LinkedList;

public class Buffer {
    // buffer size
    private int size;

    // buffer implementation
    private LinkedList<Double> list = new LinkedList<>();

    /**
     *
     * @param size buffers max capacity
     */
    public Buffer(int size) {
        this.size = size;
    }

    /**
     * adds d to the buffer
     * @param d
     */
    public synchronized void add(Double d) {
        while (list.size() + 1 > this.size) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        list.add(d);
        notifyAll();
    }

    /**
     * returns and removes the first element in the buffer
     * @return
     */
    public synchronized Double poll() {
        while (list.size() < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        notifyAll();
        return list.poll();
    }
}
