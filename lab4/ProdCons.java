package lab4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProdCons {
    private int max;
    private Queue<Integer> queue = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition firstProd = lock.newCondition();
    private Condition restProd = lock.newCondition();
    private Condition firstKons = lock.newCondition();
    private Condition restKons = lock.newCondition();


    public ProdCons(int size){
        //this.queue = new LinkedList<>();
        this.max = size;
    }

    public void put(int amount){
        lock.lock();
        try{
            restProd.await();

            while( queue.size() + amount > max) {
                firstProd.await();
            }

            for(int i = 0; i < amount; i++)
                queue.add(1);

            restProd.signal();
            firstKons.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void take(int amount) {
        lock.lock();
        try{
            restKons.await();

            while(queue.size() - amount < 0){
                firstKons.await();
            }

            for(int i = 0; i < amount; i++)
               queue.remove();

            restKons.signal();
            firstProd.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }   finally {
            lock.unlock();
        }
    }

    public void start(){
        lock.lock();
        restProd.signal();
        restKons.signal();
        lock.unlock();
    }
}
