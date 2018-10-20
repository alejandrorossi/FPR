package model;

/**
 * IMPORTANTE: esta clase se debe correr con start, no con run!
 */
public class Worker implements Runnable {

    private ConcurVector mask;
    private ConcurVector vector2;
    private ConcurVector vector;
    private double d;
    private int threadIndex;
    private int elemsPerWorker;
    private double setVal;
    long threadId = Thread.currentThread().getId();
    private Buffer input;
    private Buffer output;
    private Task task;

    /**
     *
     * @param task the amount of elements to take from the buffer and the type task
     * @param input element service
     */
    public Worker(Task task, Buffer input, Buffer output) {
        this.input = input;
        this.output = output;
        this.task = task;
    }

    public Worker(int elemsPerWorker, int threadIndex, double d, ConcurVector vector, Task task) {
        this.elemsPerWorker = elemsPerWorker;
        this.threadIndex = threadIndex;
        this.d = d;
        this.vector = vector;
        this.task = task;
    }

    public Worker(int elemsPerWorker, int threadIndex, ConcurVector v1, ConcurVector v2, Task task) {
        this.elemsPerWorker = elemsPerWorker;
        this.threadIndex = threadIndex;
        this.vector = v1;
        this.vector2 = v2;
        this.task = task;
    }

    public Worker(int elemsPerWorker, int threadIndex, ConcurVector v1, ConcurVector mask, ConcurVector v2, Task task) {
        this.elemsPerWorker = elemsPerWorker;
        this.threadIndex = threadIndex;
        this.vector = v1;
        this.vector2 = v2;
        this.mask = mask;
        this.task = task;
    }


    /**
     * for now, just sums elements
     */
    @Override
    public void run() {
        switch (task.type) {
            case SET:
                this.set(d, vector, threadIndex, elemsPerWorker);
                break;
            case SUM:
                this.sum();
                break;
            case MAX:
                this.max();
                break;
            case ADD:
                this.add();
                break;
            case ASSIGN:
                this.assign();
                break;
            case ASSIGN_MASK:
                this.assign_mask();
                break;
            case MUL:
                this.mul();
                break;
        }
    }


    /**
     * takes elements from input buffers, sums them and adds result into output buffer
     */
    public void sum() {
        double result = 0.0;

        for (int i = 0;i < task.cantValues; i++) {
            double x = this.input.poll();
            result += x;
        }

        this.output.add(result);
    }



    private void max() {
        double result = 0.0;

        for (int i = 0;i < task.cantValues; i++) {
            double x = this.input.poll();
            result = Math.max(x, result);
        }

        this.output.add(result);
    }


    /** Puts d value in all vector's positions.
     * @param d, value to be assigned. */
    public void set(double d, ConcurVector output, int index, int cant) {
        for (int i = 0; i < cant; i++) {
            output.set(index + i, d);
        }
    }


    private void add() {
        for (int i = 0; i < elemsPerWorker; i++) {
            vector.set(threadIndex + i, vector.get(i + threadIndex) + vector2.get(i + threadIndex));
        }
    }


    /** Copies the values of another vector to this one
     * @precondition dimension() == v.dimension(). */
    public void assign() {
        for (int i = 0; i < elemsPerWorker; i++) {
            vector.set(threadIndex + i, vector2.get(i + threadIndex));
        }
    }

    /** Copies some calues of another vector into this one.
     * Un vector mascara indica cuales valores deben copiarse.
     * @precondition dimension() == mask.dimension() && dimension() == v.dimension(). */
    private void assign_mask() {
        for (int i = 0; i < elemsPerWorker; i++) {
            if (mask.get(i + threadIndex) >= 0)
                vector.set(threadIndex + i, vector2.get(i + threadIndex));
        }
    }

    /** Copies the values of another vector to this one
     * @precondition dimension() == v.dimension(). */
    public void mul() {
        for (int i = 0; i < elemsPerWorker; i++) {
            vector.set(threadIndex + i, vector.get(i + threadIndex) * vector2.get(i + threadIndex));
        }
    }

}
