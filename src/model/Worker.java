package model;

/**
 * IMPORTANTE: esta clase se debe correr con start, no con run!
 */
public class Worker implements Runnable {

    private int elements;

    private Buffer input;
    private Buffer output;

    /**
     *
     * @param elements the amount of elements to take from the buffer
     * @param input element service
     * @param output buffer to output the result only once
     */
    public Worker(int elements, Buffer input, Buffer output) {
        this.elements = elements;
        this.input = input;
        this.output = output;
    }

    /**
     * for now, just sums elements
     */
    @Override
    public void run() {
        sum();
    }

    /**TODO: el worker debe finalizar ejecucion al encontrar elemento invalido
    podemos hacerlo que en este metodo evalue validez y ya dispare una exception, o manejar el corte de ejecucion
    desde el metodo que llame a este  */
    private boolean elemIsValid(double d) {
        //if d!int then exception
        return true;
    }


    private void sacarYagregar(double d){
        input.poll();
        output.add(d);
    }



    /**
     * tells the worker to take one more element from the buffer
     * IMPORTANT: this method should be called before starting the thread
     * PRECONDITION: assumes action can be done and this method will be called only once
     */
    public void plusOne() {
        elements++;
    }

    /**
     * takes elements from input buffers, sums them and adds result into output buffer
     */
    public void sum() {
        Double result = 0.0;
        Double element;

        for (int i = 0; i < elements; i++) {
            element = input.poll();
            result += element;

            // concurrency test based on execution time
            System.out.println("++++++++Thread ID " + Thread.currentThread().getId() + " executed sum():");
            System.out.println("prev result: " + (result - element));
            System.out.println("element: " + element);
            System.out.println("result: " + result);
            System.out.println("++++++++");
        }

        output.add(result);

        // concurrency test based on execution time
        System.out.println("++++++++Thread ID " + Thread.currentThread().getId() + " added to output buffer.");
        System.out.println("result: " + result);
        System.out.println("++++++++");
    }


    /** Puts d value in all vector's positions.
     * @param d, value to be assigned. */
    public void set(double d) {
        elemIsValid( d);
        for (int i = 0; i < elements; ++i){
            sacarYagregar(d);
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
        // for (int i = elements; i > 0; --i){ //para concurVector
            if (mask.get(i) >= 0)  //si es igual o mayor a 0  copia
                sacarYagregar(v.get(i));
    }




}
