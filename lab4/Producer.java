package lab4;

public class Producer extends Thread{

    int max_portion;
    int op;
    int id;
    boolean uniform;


    public Producer(int portion, int id, boolean randomization){
        this.max_portion = portion;
        this.op = 0;
        this.id = id;
        this.uniform = randomization;
    }

    public void run() {
        Source.end_proc++;
        if(uniform)
            while(Source.work){
                Source.queue.put(Source.getPortion(1, this.max_portion), this);
            }
        else
            while(Source.work){
                Source.queue.put(Source.getUnfairPortion(1, this.max_portion), this);
            }
        Source.end_proc--;
    }

}
