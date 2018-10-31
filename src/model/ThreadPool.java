package model;


public class ThreadPool {

    private int size;
    private int threads;

    private int elements; //The amount of elements each worker takes from input buffer
    private int over = 0; //The amount of elements that some worker must take more

    private Worker[] workers;
    private Task task = new Task();

    /** @param threads the amount of threads (workers) to initialize per task
     */
    public ThreadPool(int size, int threads) {
        this.size = size;
        this.threads = threads;
        this.createWorkers();
    }

    public void setSize(int size) {
        this.size = size;
    }

    private void calculateElemsForWorkers(){
        int diff = 0;

        if(this.size == this.threads) {
            while (diff < 2 && this.threads > 1) {
                this.threads -= 1;
                diff = this.size - this.threads;
            }
        }

        this.elements = this.size / this.threads;
        this.over = this.size % this.threads;
    }

    private void createWorkers(){
        this.workers = new Worker[this.threads];
        for(int i = 0; i < this.threads; ++i){
            this.workers[i] = new Worker();
        }
    }

    private void runWorkers(){
        this.calculateElemsForWorkers();
        int threadIndex = 0;
        //Cuando se hacen varias pasadas hay casos en los que hay diferencias en el size
        //del bufferOutput y esto hace que en el metodo waitTillFull() entre en el wait()
        //para siempre, por eso pasamos este valor en este momento.
        this.task.setBufferOutput(this.threads);
        for(int i = 0; i < this.threads; i++) {
            Worker w = this.workers[i];
            if (threadIndex > 0) {
                w.setData(threadIndex, this.elements , this.task);
                threadIndex += this.elements;
            } else {
                w.setData(threadIndex, this.elements + this.over, this.task);
                threadIndex += elements + over;
            }

            Thread thread = new Thread(w);
            thread.start();
        }
    }


    public Buffer sum(Buffer b) {
        this.task = new Task();
        this.task.setSum(b);
        this.runWorkers();
        return this.task.output;
    }

    public Buffer max(Buffer b) {
        this.task = new Task();
        this.task.setMax(b);
        this.runWorkers();
        return this.task.output;
    }

    public void set(double d, Vector vector) {
        this.task = new Task();
        this.task.setSet(d, vector);
        this.runWorkers();
    }

    public void add(Vector v1, Vector v2) {
        this.task = new Task();
        this.task.setAdd(v1, v2);
        this.runWorkers();
    }

    public void assign(Vector v1, Vector v2) {
        this.task = new Task();
        this.task.setAssign(v1, v2);
        this.runWorkers();
    }

    public void assign(Vector v1, Vector mask, Vector v2) {
        this.task = new Task();
        this.task.setAssignMask(v1, mask, v2);
        this.runWorkers();
    }

    public void mul(Vector v1, Vector v2) {
        this.task = new Task();
        this.task.setMul(v1, v2);
        this.runWorkers();
    }
}
