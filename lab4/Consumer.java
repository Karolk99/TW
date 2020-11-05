package lab4;

public class Consumer extends Thread{

    int max_portion;
    int op;
    int id;

    public Consumer(int portion, int id){
        this.max_portion = portion;
        this.op = 0;
        this.id = id;
    }
    public void run() {
        while(Source.work){
            Source.queue.take(Source.getPortion(1, this.max_portion));
            this.op++;
        }
    }

    public int random_no(){
        return 1;
    }

}
