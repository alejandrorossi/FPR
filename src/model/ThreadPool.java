package model;

public class ThreadPool {

    private Buffer input;
    private Buffer output;
    private int size;
    private int threads;

    private int elements; //The amount of elements each worker takes from input buffer
    private int over = 0; //The amount of elements that some worker must take more
    private VectorTask typeTask;

    /**
     *
     * @param threads the amount of threads (workers) to initialize per task
     * @param task type task
     */
    public ThreadPool(int threads, VectorTask task) {
        this.threads = threads;
        this.typeTask = task;
        this.output = new Buffer(this.threads);
    }

    private void calculateElemsForWorkers(){
        //f(this.size < this.threads){ this.threads = this.size /2; }

        this.elements = this.size / this.threads;
        this.over = this.size % this.threads;
    }

    private void initWorkers(){
        this.size = this.input.size();

        this.calculateElemsForWorkers();

        for(int i = 0; i < this.threads; ++i){
            Task t;
            if(i == 0){
                t = new Task(this.typeTask.ordinal(), this.elements + this.over);
            }else{
                t = new Task(this.typeTask.ordinal(), this.elements);
            }

            Worker w = new Worker(t, this.input, this.output);

            Thread wt = new Thread(w);
            wt.start();
        }
    }

    /**
     * sums all elements in the buffer
     * @return a buffer with partial results
     */
    public Buffer sum(Buffer b) {

        this.input = b;

        this.initWorkers();

        return this.output;
    }




}
