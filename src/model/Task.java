package model;

public class Task {
    public VectorTask type;

    public ConcurVector vectorDestination;
    public ConcurVector vectorOrigin;

    public Buffer input;
    public Buffer output;

    public double value;

    public ConcurVector mask;

    public Task(){}

    public void setSum(Buffer i, int t){
        this.type = VectorTask.SUM;
        this.input = i;
        this.output = new Buffer(t);
    }

    public void setMax(Buffer i, int t){
        this.type = VectorTask.MAX;
        this.input = i;
        this.output = new Buffer(t);
    }

    public void setAssign(ConcurVector v1, ConcurVector v2) {
        this.type = VectorTask.ASSIGN;
        this.vectorDestination = v1;
        this.vectorOrigin = v2;
    }

    public void setSet(double d, ConcurVector vector) {
        this.type = VectorTask.SET;
        this.value = d;
        this.vectorDestination = vector;
    }

    public void setAdd(ConcurVector v1, ConcurVector v2) {
        this.type = VectorTask.ADD;
        this.vectorDestination = v1;
        this.vectorOrigin = v2;
    }

    public void setAssignMask(ConcurVector v1, ConcurVector mask, ConcurVector v2) {
        this.type = VectorTask.ASSIGN_MASK;
        this.vectorDestination = v1;
        this.mask = mask;
        this.vectorOrigin = v2;
    }

    public void setMul(ConcurVector v1, ConcurVector v2) {
        this.type = VectorTask.MUL;
        this.vectorDestination = v1;
        this.vectorOrigin = v2;
    }
}
