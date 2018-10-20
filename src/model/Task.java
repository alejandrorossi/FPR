package model;

public class Task {
    public VectorTask type;
    public int cantValues;
    private int cant = 0;
    public double[] values;

    public Task(VectorTask type, int cv){
        this.type = type;
        this.cantValues = cv;
        this.values = new double[cv];
    }

    public Task(VectorTask set) {
        this.type = set;
    }

    public void setValue(double d){
        this.values[cant] = d;
        this.cant++;
    }

    public boolean isFullValues(){ return cantValues == cant; }
}
