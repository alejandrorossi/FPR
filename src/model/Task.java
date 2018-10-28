package model;

public class Task {
    public VectorTask type;
    public int cantValues;

    public Task(VectorTask type, int cantValues){
        this.type = type;
        this.cantValues = cantValues;
    }

    public Task(VectorTask type) {
        this.type = type;
    }
}
