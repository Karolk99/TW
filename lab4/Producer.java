package lab4;

public class Producer extends Thread{

    int max_portion;
    int op;
    int id;

    public Producer(int portion, int id){
        this.max_portion = portion;
        this.op = 0;
        this.id = id;
    }
    public void run() {
        while(Source.work){
            Source.queue.put(Source.getPortion(1, this.max_portion));
            this.op++;
        }
    }

    public int random_no(){
        return 1;
    }
}
