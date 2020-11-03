package lab3;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Source {

    static ProdCons queue = new ProdCons(1000);
    static boolean work = true;

    public static int getPortion(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static void printResults(int cons, int prod, Producer[] p_threads, Consumer[] c_threads){
        for(int i = 0; i < prod; i++)
            System.out.println("Producer ID: " + p_threads[i].id + "  portion size: " + p_threads[i].amount + "   number of operations: "+ p_threads[i].op);

        for(int i = 0; i < cons; i++)
            System.out.println("Consumer ID: " + c_threads[i].id + "  portion size: " + c_threads[i].amount + "   number of operations: "+ c_threads[i].op);
    }

    public static void main(String []args) throws InterruptedException {

        int cons = 5;
        int prod = 5;
        int min = 1;
        int max = 500;

        Producer[] p_threads = new Producer[prod];
        Consumer[] c_threads = new Consumer[cons];

        for (int i = 0; i < prod; i++) {
            p_threads[i] = new Producer(getPortion(min, max), i); //random number
        }
        for (int i = 0; i < cons; i++) {
            c_threads[i] = new Consumer(getPortion(min, max), i); //random number
        }

        for (int i = 0; i < prod; i++) {
            p_threads[i].start();
        }
        for (int i = 0; i < cons; i++) {
            c_threads[i].start();
        }

        sleep(10000);

        Source.work = false;

        /*
        for (int i = 0; i < prod; i++) {

            p_threads[i].join();
        }
        for (int i = 0; i < cons; i++) {
            c_threads[i].join();
        }
        */

        printResults(cons, prod, p_threads, c_threads);
    }


}
