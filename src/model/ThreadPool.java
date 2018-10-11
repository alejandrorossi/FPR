package model;

public class ThreadPool {
    private Buffer buffer;
    private int threads;
    private int load;

    public ThreadPool(int size, int threads, int load){
        this.buffer = new Buffer(size);
        this.threads = threads;
        this.load = load;

        this.initWorkers();
    }

    private void initWorkers(){
        for(int i = 0; i < this.threads; ++i){
            //Creacion de workers.
        }
    }

}
