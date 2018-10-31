package model;

public interface Vector {
    int dimension();
    double get(int i);
    void set(int i, double d);
    void set(double d);
    void assign(Vector v);
    void assign(Vector mask, Vector v);
    void add(Vector v);
    void mul(Vector v);
    double sum();
    double mean();
    double prod(Vector cv);
    double max();
    double norm();
}
