package lab4;

public class Consumer extends Thread{

    int max_portion;
    int op;
    int id;
    boolean uniform;

    public Consumer(int portion, int id, boolean randomization){
        this.max_portion = portion;
        this.op = 0;
        this.id = id;
        this.uniform = randomization;
    }

    public void run() {
        Source.end_cons++;
        if(uniform)
            while(Source.work){
                Source.queue.take(Source.getPortion(1, this.max_portion), this);
            }
        else
            while(Source.work){
                Source.queue.take(Source.getUnfairPortion(1, this.max_portion), this);
            }
        Source.end_cons--;
    }
}
