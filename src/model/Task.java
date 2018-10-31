package model;

public class Task {
    public VectorTask type;

    public Vector vectorDestination;
    public Vector vectorOrigin;

    public Buffer input;
    public Buffer output;

    public double value;

    public Vector mask;

    public Task(){}

    public void setSum(Buffer i){
        this.type = VectorTask.SUM;
        this.input = i;
    }

    public void setMax(Buffer i){
        this.type = VectorTask.MAX;
        this.input = i;
    }

    public void setAssign(Vector v1, Vector v2) {
        this.type = VectorTask.ASSIGN;
        this.vectorDestination = v1;
        this.vectorOrigin = v2;
    }

    public void setSet(double d, Vector vector) {
        this.type = VectorTask.SET;
        this.value = d;
        this.vectorDestination = vector;
    }

    public void setAdd(Vector v1, Vector v2) {
        this.type = VectorTask.ADD;
        this.vectorDestination = v1;
        this.vectorOrigin = v2;
    }

    public void setAssignMask(Vector v1, Vector mask, Vector v2) {
        this.type = VectorTask.ASSIGN_MASK;
        this.vectorDestination = v1;
        this.mask = mask;
        this.vectorOrigin = v2;
    }

    public void setMul(Vector v1, Vector v2) {
        this.type = VectorTask.MUL;
        this.vectorDestination = v1;
        this.vectorOrigin = v2;
    }

    public void setBufferOutput(int threads) {
        this.output = new Buffer(threads);
    }
}
