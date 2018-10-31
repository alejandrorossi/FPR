package model;

public class Task {
    public VectorTask type;

    public ConcrVector vectorDestination;
    public ConcrVector vectorOrigin;

    public Buffer input;
    public Buffer output;

    public double value;

    public ConcrVector mask;

    public Task(){}

    public void setSum(Buffer i){
        this.type = VectorTask.SUM;
        this.input = i;
    }

    public void setMax(Buffer i){
        this.type = VectorTask.MAX;
        this.input = i;
    }

    public void setAssign(ConcrVector v1, ConcrVector v2) {
        this.type = VectorTask.ASSIGN;
        this.vectorDestination = v1;
        this.vectorOrigin = v2;
    }

    public void setSet(double d, ConcrVector ConcrVector) {
        this.type = VectorTask.SET;
        this.value = d;
        this.vectorDestination = ConcrVector;
    }

    public void setAdd(ConcrVector v1, ConcrVector v2) {
        this.type = VectorTask.ADD;
        this.vectorDestination = v1;
        this.vectorOrigin = v2;
    }

    public void setAssignMask(ConcrVector v1, ConcrVector mask, ConcrVector v2) {
        this.type = VectorTask.ASSIGN_MASK;
        this.vectorDestination = v1;
        this.mask = mask;
        this.vectorOrigin = v2;
    }

    public void setMul(ConcrVector v1, ConcrVector v2) {
        this.type = VectorTask.MUL;
        this.vectorDestination = v1;
        this.vectorOrigin = v2;
    }

    public void setBufferOutput(int threads) {
        this.output = new Buffer(threads);
    }
}
