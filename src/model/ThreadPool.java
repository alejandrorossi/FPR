package model;


public class ThreadPool {

    private Buffer input;
    private Buffer output;
    private int size;
    private int threads;

    private int elements; //The amount of elements each worker takes from input buffer
    private int over = 0; //The amount of elements that some worker must take more
    private VectorTask typeTask;
    private Worker[] workers;

    /**
     * @param threads the amount of threads (workers) to initialize per task
     * @param task type task
     */
    public ThreadPool(int threads, VectorTask task) {
        this.threads = threads;
        this.typeTask = task;
        this.output = new Buffer(this.threads);
        this.workers= new Worker[size]; //TODO: inicializar bien
    }

    private void calculateElemsForWorkers(){
        this.elements = this.size / this.threads;
        this.over = this.size % this.threads;
    }

    private void initWorkers(VectorTask vTask){
        this.size = this.input.size();

        this.calculateElemsForWorkers();

        for(int i = 0; i < this.threads; ++i){
            Task t;
            if(i == 0){
                t = new Task(vTask, this.elements + this.over);
            }else{
                t = new Task(vTask, this.elements);
            }

            Worker w = new Worker(t, this.input, this.output);

            Thread wt = new Thread(w);
            wt.start(); //todo: agregarlos a la lista
            /*luego de que se agregan a la lista se recorren para darle start a todos
            * deberiamos tener un metodo de recalcularTrabajo() que haga que los workers creados agarren mas elementos.
            * Al principio de to do se fija si no hay workers entonces hace el initWorkers() y si hay workers en
            * this.workers ejecuta el recalcularTrabajo()
            *
            * como solo hay un threadpool por operacion deberiamos mandar desde concurvector el start y el recalcularTrabajo()
            *
            * recalcularTrabajo() vendría a ser la segunda pasada en las operaciones que lo requieran
            * */


        }
    }

    /**
     * sums all elements in the buffer
     * @return a buffer with partial results
     */
    public Buffer sum(Buffer b) {

        this.input = b;

        this.initWorkers(VectorTask.SUM);

        return this.output;
    }



    public Buffer max(Buffer b) {
        this.input = b;
        this.initWorkers(VectorTask.MAX);
        return this.output;
    }


    /**
     * sets d in all elements of vector
     * @param d
     * @param vector
     */
    public void set(double d, ConcurVector vector) {
        int elemsPerWorker = vector.dimension() / threads;
        int threadIndex = 0;
        for (int i = 0; i < threads; i++) {
            Worker worker = new Worker(elemsPerWorker, threadIndex, d, vector, new Task(VectorTask.SET));
            Thread thread = new Thread(worker);
            thread.start();
            threadIndex += elemsPerWorker;
        }
    }

    /**
     * suma los valores en v1
     */
    public void add(ConcurVector v1, ConcurVector v2) {
        int elemsPerWorker = v1.dimension() / threads;
        int threadIndex = 0;
        for (int i = 0; i < threads; i++) {
            Worker worker = new Worker(elemsPerWorker, threadIndex, v1, v2, new Task(VectorTask.ADD));
            Thread thread = new Thread(worker);
            thread.start();
            threadIndex += elemsPerWorker;
        }
    }

    public void assign(ConcurVector v1, ConcurVector v2) {
        int elemsPerWorker = v1.dimension() / threads;
        int threadIndex = 0;
        for (int i = 0; i < threads; i++) {
            Worker worker = new Worker(elemsPerWorker, threadIndex, v1, v2, new Task(VectorTask.ASSIGN));
            Thread thread = new Thread(worker);
            thread.start();
            threadIndex += elemsPerWorker;
        }
    }

    public void assign(ConcurVector v1, ConcurVector mask, ConcurVector v2) {
        int elemsPerWorker = v1.dimension() / threads;
        int threadIndex = 0;
        for (int i = 0; i < threads; i++) {
            Worker worker = new Worker(elemsPerWorker, threadIndex, v1, mask, v2, new Task(VectorTask.ASSIGN_MASK));
            Thread thread = new Thread(worker);
            thread.start();
            threadIndex += elemsPerWorker;
        }
    }

    public void mul(ConcurVector v1, ConcurVector v2) {
        int elemsPerWorker = v1.dimension() / threads;
        int threadIndex = 0;
        for (int i = 0; i < threads; i++) {
            Worker worker = new Worker(elemsPerWorker, threadIndex, v1, v2, new Task(VectorTask.MUL));
            Thread thread = new Thread(worker);
            thread.start();
            threadIndex += elemsPerWorker;
        }
    }
}
