package model;

public class Buffer {
    private int size = 0;
    private Object[] data;
    private int begin = 0, end = 0;

    public Buffer(int s){
      this.size = s;
      this.data = new Object[this.size];
    }

    private boolean isEmpty(){ return this.begin == this.end ; }
    private boolean isFull(){ return this.next(this.begin) == this.end ; }
    private int next(int i){ return (i +1) % this.size; }

    public synchronized void push(Object obj){
        while ( this.isFull()) {
          try{ wait(); } catch(InterruptedException e){}
        }
        this.data[begin] = obj;
        this.begin = this.next(begin);
        notifyAll();
    }

    public synchronized Object pop(){
        while(this.isEmpty()){
          try{ wait(); } catch(InterruptedException e){}
        }
        Object result = this.data[this.end];
        end = this.next(this.end);
        notifyAll();
        return result;
    }
}
