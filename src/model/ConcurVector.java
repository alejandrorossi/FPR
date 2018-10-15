package model;

public class ConcurVector {
    // El array con los elementos del vector
    private double[] elements;
    private int threads;

    /** Constructor del ConcurVector.
     * @param size, la longitud del vector.
     * @param threads, cantidad ḿaxima a utilizar.
     * @precondition size && threads  > 0. */
    public ConcurVector(int size, int threads) {
        elements = new double[size];
        this.threads = threads;
    }

    /** Retorna la longitud del vector; es decir, su dimension. */
    public int dimension() {
        return elements.length;
    }


    /** Retorna el elemento del vector en la posicion i.
     * @param i, la posicion del elemento a ser retornado.
     * @precondition 0 <= i < dimension(). */
    public double get(int i) {
        return elements[i];
    }


    /** Pone el valor d en la posicion i del vector.
     * @param i, la posicion a setear.
     * @param d, el valor a ser asignado en la posicion i.
     * @precondition 0 <= i < dimension. */
    public void set(int i, double d) {
        elements[i] = d;
    }

    /**
     * carga un buffer con todos los elementos del vector.
     *
     * IMPORTANTE: provisorio, deberia ser implementado de la misma forma que sum, concurrente.
     *
     * @param buffer
     */
    private void load(Buffer buffer) {
        for (Double e: elements)
            buffer.add(e);
    }

    /**De aquí en adelante, los métodos deben ser resueltos
     * de manera concurrente
     */

    /** Obtiene la suma de todos los valores del vector. */
    public double sum() {
        // cuantos resultados voy a tener, inicialmente
        // la misma cantidad que de threads trabajando
        int results = threads;

        // la cantidad de elementos que tendra el buffer de input,
        // inicialmente tanta como elememtnos en el vector
        int inputSize = elements.length;

        // la cantidad de elementos que cada worker debera sacar del buffer
        int elemsPerWorker = inputSize / threads;

        // buffer de resultados, inicialmente creado con tanta cantidad como elementos
        // tiene el vector
        Buffer buffer = new Buffer(inputSize);

        // cargo el buffer
        load(buffer);

        // mientras que tenga mas que 1 resultado
        while (results < 1) {
            // creo el pool
            ThreadPool pool = new ThreadPool(results, elemsPerWorker);

            // arranco la suma y actualizo el buffer al que tendra los resultados
            // en la proxima iteracion
            buffer = pool.sum(buffer);

            // actualizo las variables
            inputSize = results;
            
        }

        // ahora que tengo solo 1 resultado, lo devuelvo
        return buffer.poll();
    }
}
