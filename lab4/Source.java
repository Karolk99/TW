package lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Source {

    static ProdCons queue;
    static boolean work = true;
    private static Random random = new Random();
    static int end_proc = 0;
    static int end_cons = 0;

    public static int getPortion(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static int getUnfairPortion(int min, int max){
       if (random.nextInt(5) == 4)
           return random.nextInt(max - min) + min;

       return random.nextInt(max/3 - min) + min;
    }


    public static List<String> runExample(int buf_size, int pk_config, boolean randomization) throws InterruptedException {

        Source.work = true;

        Producer[] p_threads = new Producer[pk_config];
        Consumer[] c_threads = new Consumer[pk_config];

        queue = new ProdCons(buf_size);

        for (int i = 0; i < pk_config; i++) {
            p_threads[i] = new Producer(buf_size / 2 - 1, i, randomization); //random number

            c_threads[i] = new Consumer(buf_size / 2 - 1, i, randomization); //random number
        }

        for (int i = 0; i < pk_config; i++) {
            p_threads[i].start();

            c_threads[i].start();
        }

        queue.startQueue();

        sleep(1000);

        queue.endQueue();
        queue.restartQueue();

        List<String> list = new ArrayList<>();

        for (int i = 0; i < pk_config; i++) {
            list.add(String.valueOf(buf_size) + "," + String.valueOf(true) + "," + String.valueOf(pk_config) + "," + String.valueOf(true) + "," + String.valueOf(randomization) + "," + String.valueOf(p_threads[i].op) + "\n");
            list.add(String.valueOf(buf_size) + "," + String.valueOf(false) + "," + String.valueOf(pk_config) + "," + String.valueOf(true) + "," + String.valueOf(randomization) + "," + String.valueOf(c_threads[i].op) + "\n");
        }

        return list;
    }


    public static void printResults(int cons, int prod, Producer[] p_threads, Consumer[] c_threads){
        for(int i = 0; i < prod; i++)
            System.out.println("Producer ID: " + p_threads[i].id + "   number of operations: "+ p_threads[i].op);

        for(int i = 0; i < cons; i++)
            System.out.println("Consumer ID: " + c_threads[i].id + "   number of operations: "+ c_threads[i].op);
    }


    public static void main(String []args) throws InterruptedException {

        String filename = "prodcons_data.csv";

        FileHandler.createFile(filename);

        int[] buf_size = {10000, 100000};
        int[] pk_config = {100, 1000};
        boolean is_fair = true;
        boolean[] randomization = {true, false};

            for(int b : buf_size)
                for(int pk : pk_config)
                    for(boolean r : randomization) {
                        List<String> list = runExample(b, pk, r);
                        FileHandler.writeToFile(list, filename);
                    }


        List<String> list = runExample(100000, 1000, false);
//        FileHandler.writeToFile(list, filename);
        System.out.println("end");
        System.out.println(Source.end_proc);
        System.out.println(Source.end_cons);

    }


}
