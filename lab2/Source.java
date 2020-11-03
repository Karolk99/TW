package lab2;

public class Source {

    static int counter = 0;
    static final Object monitor = new Object();

    public static void main(String []args) throws InterruptedException {
        int n = 3;
        MyThread[] threads = new MyThread[n];
        for (int i = 0; i < n; i++) {
            threads[i] = new MyThread(i+1);
        }

        for (int i = 0; i < n; i++) {
            threads[i].start();
        }

        for (int i = 0; i < n; i++) {
            threads[i].join();
        }

        }
    }
