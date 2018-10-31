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
        int index = 0;
        for (int i = 0; i < this.elemsPerWorker; i++) {
            index = this.threadIndex + i;
            this.task.vectorDestination.set(index, this.task.value);
        }

        if (index + 1 == this.task.vectorDestination.dimension())
            this.task.vectorDestination.finishWoking();
    }


    private void add() {
        int index = 0;
        for (int i = 0; i < elemsPerWorker; i++) {
            index = this.threadIndex + i;
            this.task.vectorDestination.set(index,
                    this.task.vectorDestination.get(index) + this.task.vectorOrigin.get(index));
        }

        if (index + 1 == this.task.vectorDestination.dimension())
            this.task.vectorDestination.finishWoking();
    }

    /** Copies the values of another vector to this one
     * @precondition dimension() == v.dimension(). */
    public void assign() {
        int index = 0;
        for (int i = 0; i < this.elemsPerWorker; i++) {
            index = this.threadIndex + i;
            this.task.vectorDestination.set(index, this.task.vectorOrigin.get(index));
        }

        if (index + 1 == this.task.vectorDestination.dimension())
            this.task.vectorDestination.finishWoking();
    }

    /** Copies some values of another vector into this one.
     * Un vector mascara indica cuales valores deben copiarse.
     * @precondition dimension() == mask.dimension() && dimension() == v.dimension(). */
    private void assign_mask() {
        int index = 0;
        for (int i = 0; i < this.elemsPerWorker; i++) {
            index = this.threadIndex + i;
            if (this.task.mask.get(index) >= 0)
                this.task.vectorDestination.set(index,
                        this.task.vectorOrigin.get(index));
        }

        if (index + 1 == this.task.vectorDestination.dimension())
            this.task.vectorDestination.finishWoking();
    }

    /** Copies the values of another vector to this one
     * @precondition dimension() == v.dimension(). */
    public void mul() {
        int index = 0;
        for (int i = 0; i < this.elemsPerWorker; i++) {
            index = this.threadIndex + i;
            this.task.vectorDestination.set(index,
                    this.task.vectorDestination.get(index) * this.task.vectorOrigin.get(index));
        }

        if (index + 1 == this.task.vectorDestination.dimension())
            this.task.vectorDestination.finishWoking();
    }
}
