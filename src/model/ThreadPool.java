package model;


import java.util.Arrays;

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



    /** todo: no se bien cuando llamar este metodo, deberia ser antes de cada pasada
     * si la cantidad de elementos  es menor al doble que de cantidad de workers, entonces en cada pasada
     * calcular cantidad de workers en los que se va a repartir el trabajo:
     * 		SI ES PAR: la cantidad de workers sera la mitad de elementos
     * 		SI ES IMPAR: la cantidad de workers va a ser la mitad elementos redondeado para abajo
     */
    public void calcularWorkers(){

        int cantWorkers = 0;
        if (this.elements < (this.workers.length *2 )){

            if(this.elements%2==0){
                cantWorkers = this.elements/2;
            }else{
                cantWorkers= (int)Math.floor(this.elements /2);
            }

            Arrays.copyOf(workers, cantWorkers); //crea una copia del array con el largo de la cant de workers
        }


    }


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
