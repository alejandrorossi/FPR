package model;

/**
 * IMPORTANTE: esta clase se debe correr con start, no con run!
 */
public class Worker implements Runnable {

    private int elements;

    private Buffer input;
    private Buffer output;

    /**
     *
     * @param elements the ammount of elements to take from the buffer
     * @param input element service
     * @param output buffer to output the result only once
     */
    public Worker(int elements, Buffer input, Buffer output) {
        this.elements = elements;
        this.input = input;
        this.output = output;
    }

    /**
     * for now, just sums elements
     */
    @Override
    public void run() {
        sum();
    }

    /**
     * tells the worker to take one more element from the buffer
     * IMPORTANT: this method should be called before starting the thread
     * PRECONDITION: asumes action can be done and this method will be called only once
     */
    public void plusOne() {
        elements++;
    }

    /**
     * takes elements from input buffers, sums them and adds result into output buffer
     */
    public void sum() {
        Double result = 0.0;
        Double element;

        for (int i = 0; i < elements; i++) {
            element = input.poll();
            result += element;

            // concurrency test based on execution time
            System.out.println("++++++++Thread ID " + Thread.currentThread().getId() + " excecuted sum():");
            System.out.println("prev result: " + (result - element));
            System.out.println("element: " + element);
            System.out.println("result: " + result);
            System.out.println("++++++++");
        }

        output.add(result);

        // concurrency test based on execution time
        System.out.println("++++++++Thread ID " + Thread.currentThread().getId() + " added to output buffer.");
        System.out.println("result: " + result);
        System.out.println("++++++++");
    }
}
