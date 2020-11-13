package lab3;

public class Consumer extends Thread{

    int amount;
    int op;
    int id;

    public Consumer(int max_portion, int id, boolean randomization){
        if(randomization)
            this.amount = lab4.Source.getPortion(1, max_portion);
        else
            this.amount = lab4.Source.getUnfairPortion(1, max_portion);

        this.op = 0;
        this.id = id;

    }

    public void run() {
        while(Source.work){
            Source.queue.take(this);
        }
        Source.end_cons++; // z jakiegoś powodu średnio jeden na 4000 consumerow nie dodaje wartości???
    }
}
