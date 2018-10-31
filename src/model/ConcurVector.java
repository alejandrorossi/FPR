package model;

public class ConcurVector {

    private double[] elements;// El array con los elementos del vector
    private int threads;

    private ThreadPool tpool;

    /**
     * @param size, la longitud del vector.
     * @param threads, cantidad ḿaxima a utilizar.
     * @precondition size && threads  > 0. */
    public ConcurVector(int size, int threads) {
        elements = new double[size];
        this.threads = threads;
        tpool = new ThreadPool(size, threads);
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

    /** Carga un buffer con todos los elementos del vector.
     * @param buffer
     */
    private void load(Buffer buffer) {
        for (Double e: elements)
            buffer.add(e);
    }

    /**De aquí en adelante, los métodos deben ser resueltos
     * de manera concurrente
     */

    /** Pone el valor d en todas las posiciones del vector.
     * @param d, el valor a ser asignado. */
    public void set(double d) {
        this.tpool.set(d, this);
    }

    /** Copia los valores de otro vector sobre este vector.
     * @param v, el vector del que se tomaran los valores nuevos.
     * @precondition dimension() == v.dimension(). */
    public void assign(ConcurVector v) {
        this.tpool.assign(this, v);
    }

    /** Copia algunos valores de otro vector sobre este vector.
     * Un vector mascara indica cuales valores deben copiarse.
     * @param mask, vector que determina si una posicion se debe copiar.
     * @param v, el vector del que se tomaran los valores nuevos.
     * @precondition dimension() == mask.dimension() && dimension() == v.dimension(). */
    public void assign(ConcurVector mask, ConcurVector v) {
        tpool.assign(this, mask, v);
    }

    /** Suma los valores de este vector con los de otro (uno a uno).
     * @param v, el vector con los valores a sumar.
     * @precondition dimension() == v.dimension(). */
    public void add(ConcurVector v) {
        this.tpool.add(this, v);
    }

    /** Multiplica los valores de este vector con los de otro
     *  (uno a uno).
     * @param v, el vector con los valores a multiplicar.
     * @precondition dimension() == v.dimension(). */
    public  void mul(ConcurVector v) {
        this.tpool.mul(this, v);
    }


    /** Obtiene la suma de todos los valores del vector. */
    public  double sum() {
        // cuantos resultados voy a tener, inicialmente
        // la misma cantidad que de threads trabajando
        int results = this.threads;

        // buffer de resultados, inicialmente creado con tanta cantidad como elementos
        // tiene el vector
        Buffer b = new Buffer(this.elements.length);

        // cargo el buffer
        this.load(b);

        while (results >= 1) {
            // arranco la suma y actualizo el buffer al que tendra los resultados
            // en la proxima iteracion
            b = this.tpool.sum(b);
            b.waitTillFull();

            results = b.size();
            this.tpool.setSize(results);
        }

        return b.poll();
    }

    /** Obtiene el valor promedio en el vector. */
    public double mean() {
        double total = this.sum();
        return total / this.dimension();
    }

    /** Retorna el producto de este vector con otro.
     * El producto vectorial consiste en la suma de los productos de cada coordenada.
     * @param cv, el vector a usar para realizar el producto.
     * @precondition dimension() == v.dimension(). */
    public double prod(ConcurVector cv) {
        this.mul(cv);
        return this.sum();
    }



    /** Obtiene el valor maximo en el vector. */
    public double max() {
        int results = this.threads;
        Buffer b = new Buffer(this.elements.length);
        this.load(b);

        while (results >= 1) {
            b = this.tpool.max(b);
            b.waitTillFull();

            results = b.size();
        }

        return b.poll();
    }

    /** Retorna la norma del vector.
     *  Recordar que la norma se calcula haciendo la raiz cuadrada de la
     *  suma de los cuadrados de sus coordenadas.
     */
    public double norm() {
        return Math.sqrt(this.prod(this));
    }
}
