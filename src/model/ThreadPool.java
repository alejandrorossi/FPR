package model;


public class ThreadPool {

    private int size;
    private int threads;

    private int elements; //The amount of elements each worker takes from input buffer
    private int over = 0; //The amount of elements that some worker must take more

    private Worker[] workers;
    private Task task = new Task();

    /**
     * @param threads the amount of threads (workers) to initialize per task
     */
    public ThreadPool(int size, int threads) {
        this.size = size;
        this.threads = threads;
        this.createWorkers();
    }

    public void setSize(int size) {
        this.size = size;
    }

    /* TODO: mejorar calculo para varias pasadas.
    * cuando se hacen varias pasadas (ej: sum y max), no termina nunca,
    * intente cambiar cantidad de threads pero no funciona.
    */
    private void calculateElemsForWorkers(){
        int diff = 0;
        if(this.size == this.threads) {
            while (diff < 2) {
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
        for (Worker w : this.workers) {

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

    /**
     * sums all elements in the buffer
     * @return a buffer with partial results
     */
    public Buffer sum(Buffer b) {
        this.task = new Task();
        this.task.setSum(b, this.threads);
        this.runWorkers();
        return this.task.output;
    }

    public Buffer max(Buffer b) {
        this.task.setMax(b, this.threads);
        this.runWorkers();
        return this.task.output;
    }

    public void set(double d, ConcurVector vector) {
        this.task.setSet(d, vector);
        this.runWorkers();
    }

    public void add(ConcurVector v1, ConcurVector v2) {
        this.task.setAdd(v1, v2);
        this.runWorkers();
    }

    public void assign(ConcurVector v1, ConcurVector v2) {
        this.task.setAssign(v1, v2);
        this.runWorkers();
    }

    public void assign(ConcurVector v1, ConcurVector mask, ConcurVector v2) {
        this.task.setAssignMask(v1, mask, v2);
        this.runWorkers();
    }

    public void mul(ConcurVector v1, ConcurVector v2) {
        this.task.setMul(v1, v2);
        this.runWorkers();
    }
}
