package lab3;

public class Consumer extends Thread{

    int amount;
    int op;
    int id;

    public Consumer(int amount, int id){
        this.amount = amount;
        this.op = 0;
        this.id = id;
    }
    public void run() {
        while(Source.work){
            Source.queue.take(amount);
            this.op++;
        }
    }
}
