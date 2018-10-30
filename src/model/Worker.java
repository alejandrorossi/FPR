package model;


public class Worker implements Runnable {

    private int threadIndex;
    private int elemsPerWorker;
    private Task task;

    public Worker(){}

    public void setData(int threadIndex, int elements, Task task) {
        this.threadIndex = threadIndex;
        this.elemsPerWorker = elements;
        this.task = task;
    }

    @Override
    public void run() {
        switch (task.type) {
            case SET:
                this.set();
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

    public void sum() {
        double result = 0.0;

        for (int i = 0;i < elemsPerWorker; i++) {
            double x = this.task.input.poll();
            result += x;
        }

        this.task.output.add(result);
    }

    private void max() {
        double result = 0.0;

        for (int i = 0;i < elemsPerWorker; i++) {
            double x = this.task.input.poll();
            result = Math.max(x, result);
        }

        this.task.output.add(result);
    }

    /** Puts d value in all vector's positions.*/
    public void set() {
        for (int i = 0; i < this.elemsPerWorker; i++) {
            this.task.vectorDestination.set(this.threadIndex + i, this.task.value);
        }
    }


    private void add() {
        for (int i = 0; i < elemsPerWorker; i++) {
            this.task.vectorDestination.set(threadIndex + i,
                    this.task.vectorDestination.get(i + threadIndex) + this.task.vectorOrigin.get(i + threadIndex));
        }
    }

    /** Copies the values of another vector to this one
     * @precondition dimension() == v.dimension(). */
    public void assign() {
        for (int i = 0; i < this.elemsPerWorker; i++) {
            this.task.vectorDestination.set(this.threadIndex + i, this.task.vectorOrigin.get(i + this.threadIndex));
        }
    }

    /** Copies some values of another vector into this one.
     * Un vector mascara indica cuales valores deben copiarse.
     * @precondition dimension() == mask.dimension() && dimension() == v.dimension(). */
    private void assign_mask() {
        for (int i = 0; i < this.elemsPerWorker; i++) {
            if (this.task.mask.get(i + this.threadIndex) >= 0)
                this.task.vectorDestination.set(this.threadIndex + i,
                        this.task.vectorOrigin.get(i + this.threadIndex));
        }
    }

    /** Copies the values of another vector to this one
     * @precondition dimension() == v.dimension(). */
    public void mul() {
        for (int i = 0; i < this.elemsPerWorker; i++) {
            this.task.vectorDestination.set(threadIndex + i,
                    this.task.vectorDestination.get(i + this.threadIndex) * this.task.vectorOrigin.get(i + this.threadIndex));
        }
    }
}
