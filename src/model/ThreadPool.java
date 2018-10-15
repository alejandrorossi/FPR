package model;

public class ThreadPool {

    private int threads;
    private int elements;

    /**
     *
     * @param threads the ammount of threads (workers) to initialize per task
     * @param elements the ammount of elements each worker takes from input buffer
     */
    public ThreadPool(int threads, int elements) {
        this.threads = threads;
        this.elements = elements;
    }

    /**
     * sums all elememnts in input buffer
     *
     * elements size is equal to this.threads
     *
     * @param input element service to sum
     * @return a buffer with partial results as elements
     */
    public Buffer sum(Buffer input) {
        // create output buffer
        Buffer output = new Buffer(threads);

        // repeat threads times
        for (int i = 0; i < threads; i++) {
            // create a worker
            Worker worker = new Worker(elements, input, output);

            // create a thread
            Thread thread = new Thread(worker);

            // run worker thread
            thread.start();
        }

        return output;
    }
}
