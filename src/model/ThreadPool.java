package model;

public class ThreadPool {

    private int threads;
    private int elements;

    // tells if last worker to be instanciated should take one more element
    private boolean uneven = false;

    /**
     *
     * @param threads the ammount of threads (workers) to initialize per task
     * @param elements the ammount of elements each worker takes from input buffer
     */
    public ThreadPool(int threads, int elements) {
        this.threads = threads;
        this.elements = elements;

        if (threads % elements != 0)
            setUneven();
    }

    /**
     * specifies the pool that elements are un even and last worker should take
     * one more element than the rest
     */
    private void setUneven() {
        uneven = true;
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

            // if uneven and last element, tells worker to take one more element
            if (i == threads && uneven)
                worker.plusOne();

            // create a thread
            Thread thread = new Thread(worker);

            // run worker thread
            thread.start();
        }

        return output;
    }
}
