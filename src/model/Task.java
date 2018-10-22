package model;

public class Task {
    public VectorTask type;
    public int cantValues;

    public Task(VectorTask type, int cv){
        this.type = type;
        this.cantValues = cv;
    }

    public Task(VectorTask set) {
        this.type = set;
    }
}
