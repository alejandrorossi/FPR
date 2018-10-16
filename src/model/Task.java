package model;

public class Task {
    public int type;
    public int cantValues;
    private int cant = 0;
    public double[] values;

    public Task(int t, int cv){
        this.type = t;
        this.cantValues = cv;
        this.values = new double[cv];
    }

    public void setValue(double d){
        this.values[cant] = d;
        this.cant++;
    }

    public boolean isFullValues(){ return cantValues == cant; }
}
