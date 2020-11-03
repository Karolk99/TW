package lab3;

public class Producer extends Thread{

    int amount;
    int op;
    int id;

    public Producer(int amount, int id){
        this.amount = amount;
        this.op = 0;
        this.id = id;
    }
    public void run() {
        while(Source.work){
            Source.queue.put(amount);
            this.op++;
        }
    }
}
