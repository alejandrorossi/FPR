package model;

import java.util.LinkedList;

public class Buffer {

    private int size;
    private LinkedList<Double> list;

    /**
     * @param size buffers max capacity
     */
    public Buffer(int size) {
        this.size = size;
        this.list = new LinkedList<>();
    }

    /**
     * adds d to the buffer, blocks when capacity is full
     * @param d
     */
    public synchronized void add(Double d) {
        while (list.size() >= this.size) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(d);
        notifyAll();
    }

    public synchronized void waitTillFull() {
        while (list.size() < this.size) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * returns and removes the first element in the buffer, blocks when is empty
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

        Double ret = list.poll();
        notifyAll();
        return ret;
    }

    public int size(){ return this.list.size(); }

    public void printAll() {
        System.out.println("asd");
        for (Double x:list)
            System.out.println(x);

    }
}
