package model;

public class ConcurVector {
    // El array con los elementos del vector
    private double[] elements;
    private int threads;

    private ThreadPool tpool;

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
        int results = this.threads;

        // buffer de resultados, inicialmente creado con tanta cantidad como elementos
        // tiene el vector
        Buffer b = new Buffer(this.elements.length);

        // cargo el buffer
        this.load(b);

        while (results >= 1) {
            System.out.println("Results: " + results);
            // creo el pool
            ThreadPool pool = new ThreadPool(results ,VectorTask.SUM);

            // arranco la suma y actualizo el buffer al que tendra los resultados
            // en la proxima iteracion
            b = pool.sum(b);
            System.out.println(b);

            b.waitTillFull();

            results = results / 2;
        }

        System.out.println(b);
        return b.poll();
    }
}
