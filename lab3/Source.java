package lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Source {

    static ProdCons queue = new ProdCons(10000);
    static boolean work = true;
    static int end_proc = 0;
    static int end_cons = 0;

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

    public static List<String> runExample(int buf_size, int pk_config, boolean randomization) throws InterruptedException {
        Producer[] p_threads = new Producer[pk_config];
        Consumer[] c_threads = new Consumer[pk_config];

        queue = new lab3.ProdCons(buf_size);

        Source.work = true;

        for (int i = 0; i < pk_config; i++) {
            p_threads[i] = new lab3.Producer(buf_size / 2 - 1, i, randomization); //random number

            c_threads[i] = new lab3.Consumer(buf_size / 2 - 1, i, randomization); //random number
        }

        for (int i = 0; i < pk_config; i++) {
            c_threads[i].start();
            p_threads[i].start();
        }

        sleep(10000);

        queue.endQueue();
        queue.restartQueue();

        List<String> list = new ArrayList<>();

        for (int i = 0; i < pk_config; i++) {
            list.add(String.valueOf(buf_size) + "," + String.valueOf(true) + "," + String.valueOf(pk_config) + "," + String.valueOf(false) + "," + String.valueOf(randomization) + "," + String.valueOf(p_threads[i].op) + "\n");
            list.add(String.valueOf(buf_size) + "," + String.valueOf(false) + "," + String.valueOf(pk_config) + "," + String.valueOf(false) + "," + String.valueOf(randomization) + "," + String.valueOf(c_threads[i].op) + "\n");
        }

        return list;
    }

    public static void main(String []args) throws InterruptedException {

        String filename = "prodcons_data.csv";

        lab4.FileHandler.createFile(filename);

        int[] buf_size = {10000, 100000};
        int[] pk_config = {100, 1000};
        boolean[] randomization = {true, false};

        for(int b : buf_size)
            for(int pk : pk_config)
                for(boolean r : randomization) {
                    List<String> list = runExample(b, pk, r);
                    lab4.FileHandler.writeToFile(list, filename);

                }

//        List<String> list = runExample(100000, 1000, true);
        System.out.println("end");
        System.out.println(end_proc);
        System.out.println(end_cons);
    }

}
