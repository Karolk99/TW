package lab3;

public class Producer extends Thread{

    int amount;
    int op;
    int id;
    boolean uniform;

    public Producer(int max_portion, int id, boolean randomization){
        if(randomization)
            this.amount = lab4.Source.getPortion(1, max_portion);
        else
            this.amount = lab4.Source.getUnfairPortion(1, max_portion);

        this.op = 0;
        this.id = id;
        this.uniform = randomization;
    }
    public void run() {
        while(Source.work){
            Source.queue.put(this);
        }

        Source.end_proc++;
    }
}
