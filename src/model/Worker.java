package model;

/**
 * IMPORTANTE: esta clase se debe correr con start, no con run!
 */
public class Worker implements Runnable {

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
        }
    }

    /**TODO: el worker debe finalizar ejecucion al encontrar elemento invalido
    podemos hacerlo que en este metodo evalue validez y ya dispare una exception, o manejar el corte de ejecucion
    desde el metodo que llame a este  */
    private boolean elemIsValid(double d) {
        //if d!int then exception
        return true;
    }


    private void sacarYagregar(double d){
//        input.poll();
//        output.add(d);
    }



    /**
     * tells the worker to take one more element from the buffer
     * IMPORTANT: this method should be called before starting the thread
     * PRECONDITION: assumes action can be done and this method will be called only once
     */
    public void plusOne() {
//        elements++;
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


    /** Puts d value in all vector's positions.
     * @param d, value to be assigned. */
    public void set(double d, ConcurVector output, int index, int cant) {
        for (int i = 0; i < cant; i++) {
            output.set(index + i, d);
        }
    }

    /** Copies the values of another vector to this one
     * @param v, vector to copy.
     * @precondition dimension() == v.dimension(). */
    public void assign(SeqVector v) { //TODO: ver si el tipo de parametro será este u otro concurVector
        for (int i = v.dimension(); i > 0; --i){ //para secVector
        //for (int i = elements; i > 0; --i){ //para concurVector
            sacarYagregar(v.get(i));
        }
    }

    /** Copies some calues of another vector into this one.
     * Un vector mascara indica cuales valores deben copiarse.
     * @param mask, vector que determina si una posicion se debe copiar.
     * @param v, el vector del que se tomaran los valores nuevos.
     * @precondition dimension() == mask.dimension() && dimension() == v.dimension(). */
    public void assign(SeqVector mask, SeqVector v) {
        for (int i = v.dimension(); i > 0; --i) //para secVector
//         for (int i = elements; i > 0; --i){ //para concurVector
            if (mask.get(i) >= 0)  //si es igual o mayor a 0  copia
                sacarYagregar(v.get(i));
    }




}
