package model;

public class ConcrVector implements Vector {

    private Vector vector;

    public ConcrVector(int size, int threads) {
        if (threads == 1)
            this.vector = new SeqVector(size);
        else
            this.vector = new ConcurVectorFantasma(size, threads);
    }

    @Override
    public int dimension() {
        return this.vector.dimension();
    }

    @Override
    public double get(int i) {
        return this.vector.get(i);
    }

    @Override
    public void set(int i, double d) {
        this.vector.set(i, d);
    }

    @Override
    public void set(double d) {
        this.vector.set(d);
    }

    @Override
    public void assign(Vector v) {
        this.vector.assign(v);
    }

    @Override
    public void assign(Vector mask, Vector v) {
        this.vector.assign(mask, v);
    }

    @Override
    public void add(Vector v) {
        this.vector.add(v);
    }

    @Override
    public void mul(Vector v) {
        this.vector.mul(v);
    }

    @Override
    public double sum() {
        return this.vector.sum();
    }

    @Override
    public double mean() {
        return this.vector.mean();
    }

    @Override
    public double prod(Vector cv) {
        return this.vector.prod(cv);
    }

    @Override
    public double max() {
        return this.vector.max();
    }

    @Override
    public double norm() {
        return this.vector.norm();
    }
}
