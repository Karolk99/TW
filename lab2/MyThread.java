package lab2;

public class MyThread extends Thread{
    private final int number;

    public MyThread(int n) {
        this.number = n;
    }

    public void run() {
        synchronized (Source.monitor) {

            for(int i=0; i < 5; i++){
                while (Source.counter + 1 != this.number) {
                    try {
                        Source.monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.print(this.number);

                Source.counter++;
                Source.counter %= 3;
                Source.monitor.notifyAll();
            }
        }
    }
}
