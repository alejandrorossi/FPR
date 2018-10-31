package model;

public class ConcrVector {

    private double[] elements;// El array con los elementos del vector
    private int threads;

    private ThreadPool tpool;

    public boolean working = false;

    /**
     * @param size, la longitud del vector.
     * @param threads, cantidad ḿaxima a utilizar.
     * @precondition size && threads  > 0. */
    public ConcrVector(int size, int threads) {
        elements = new double[size];
        this.threads = threads;
        tpool = new ThreadPool(size, threads, this);
    }

    /** Retorna la longitud del vector; es decir, su dimension. */
    public int dimension() {
        return elements.length;
    }


    /** Retorna el elemento del ConcrVector en la posicion i.
     * @param i, la posicion del elemento a ser retornado.
     * @precondition 0 <= i < dimension(). */
    public double get(int i) {
        return elements[i];
    }


    /** Pone el valor d en la posicion i del vector.
     * @param i, la posicion a setear.
     * @param d, el valor a ser asignado en la posicion i.
     * @precondition 0 <= i < dimension. */
    public synchronized void set(int i, double d) {
        elements[i] = d;
    }

    /** Carga un buffer con todos los elementos del vector.
     * @param buffer
     */
    public void load(Buffer buffer) {
        for (Double e: elements)
            buffer.add(e);
    }

    /**De aquí en adelante, los métodos deben ser resueltos
     * de manera concurrente
     */

    /** Pone el valor d en todas las posiciones del vector.
     * @param d, el valor a ser asignado. */
    public synchronized void set(double d) {
        superWait(VectorTask.SET);
        this.tpool.set(d, this);
    }

    private void superWait(VectorTask vtask) {
        while (working && vtask != this.tpool.task.type){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.working = true;
    }

    /** Copia los valores de otro ConcrVector sobre este vector.
     * @param v, el ConcrVector del que se tomaran los valores nuevos.
     * @precondition dimension() == v.dimension(). */
    public synchronized void assign(ConcrVector v) {
        superWait(VectorTask.ASSIGN);
        this.tpool.assign(this, v);
    }

    /** Copia algunos valores de otro ConcrVector sobre este vector.
     * Un ConcrVector mascara indica cuales valores deben copiarse.
     * @param mask, ConcrVector que determina si una posicion se debe copiar.
     * @param v, el ConcrVector del que se tomaran los valores nuevos.
     * @precondition dimension() == mask.dimension() && dimension() == v.dimension(). */
    public synchronized void assign(ConcrVector mask, ConcrVector v) {
        superWait(VectorTask.ASSIGN_MASK);
        tpool.assign(this, mask, v);
    }

    /** Suma los valores de este ConcrVector con los de otro (uno a uno).
     * @param v, el ConcrVector con los valores a sumar.
     * @precondition dimension() == v.dimension(). */
    public synchronized void add(ConcrVector v) {
        superWait(VectorTask.ADD);
        this.tpool.add(this, v);
    }

    /** Multiplica los valores de este ConcrVector con los de otro
     *  (uno a uno).
     * @param v, el ConcrVector con los valores a multiplicar.
     * @precondition dimension() == v.dimension(). */
    public  synchronized void mul(ConcrVector v) {
        superWait(VectorTask.MUL);
        this.tpool.mul(this, v);
    }


    /** Obtiene la suma de todos los valores del vector. */
    public  synchronized double sum() {
        // cuantos resultados voy a tener, inicialmente
        // la misma cantidad que de threads trabajando
        int results = this.threads;

        // buffer de resultados, inicialmente creado con tanta cantidad como elementos
        // tiene el vector
        Buffer b = new Buffer(this.elements.length);

        // cargo el buffer
        this.load(b);

        while (results > 1) {
            // arranco la suma y actualizo el buffer al que tendra los resultados
            // en la proxima iteracion
            b = this.tpool.sum(b);
            b.waitTillFull();

            results = b.size();
            this.tpool.setSize(results);
        }

        if (results == 1) {
            b = this.tpool.sum(b);
            b.waitTillFull();
        }

        return b.poll();
    }

    /** Obtiene el valor promedio en el vector. */
    public synchronized double mean() {
        double total = this.sum();
        return total / this.dimension();
    }

    /** Retorna el producto de este ConcrVector con otro.
     * El producto vectorial consiste en la suma de los productos de cada coordenada.
     * @param cv, el ConcrVector a usar para realizar el producto.
     * @precondition dimension() == v.dimension(). */
    public synchronized double prod(ConcrVector cv) {
        this.mul(cv);
        return this.sum();
    }

    /** Obtiene el valor maximo en el vector. */
    public synchronized double max() {
        int results = this.threads;
        Buffer b = new Buffer(this.elements.length);
        this.load(b);

        while (results > 1) {
            b = this.tpool.max(b);
            b.waitTillFull();

            results = b.size();
            this.tpool.setSize(results);
        }

        return b.poll();
    }

    /** Retorna la norma del vector.
     *  Recordar que la norma se calcula haciendo la raiz cuadrada de la
     *  suma de los cuadrados de sus coordenadas.
     */
    public synchronized double norm() {
        return Math.sqrt(this.prod(this));
    }

    public synchronized void finishWoking() {
        this.working = false;
        notifyAll();
    }
}
