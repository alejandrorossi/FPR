package model;

public class ConcrVector {
    // El array con los elementos del vector
    private double[] elements;

    private ThreadPool tpool;

    /** Constructor del SeqVector.
     * @param size, la longitud del vector.
     * @param threads, cantidad de threads workers.
     * @param load, diferencia en cantidad de trabajos entre workers.
     * @precondition size > 0 */
    public ConcrVector(int size, int threads, int load) {
        this.elements = new double[size];
        this.tpool = new ThreadPool(size, threads, load);
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


    ///////////////////////////////////////////////////////////////////
    /**De aquí en adelante, los métodos deben ser resueltos
     * de manera concurrente
     */

    /** Pone el valor d en todas las posiciones del vector.
     * @param d, el valor a ser asignado. */
    public void set(double d) {
        for (int i = 0; i < dimension(); ++i)
            elements[i] = d;
    }
}
