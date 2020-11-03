package lab3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProdCons {
    private int max;
    private Queue<Integer> queue = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public ProdCons(int size){
        //this.queue = new LinkedList<>();
        this.max = size;
    }

    public void put(int amount){
        lock.lock();
        try{
            while( queue.size() + amount > max) {
                notFull.await();
            }
            for(int i = 0; i < amount; i++)
                queue.add(1);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void take(int amount) {
        lock.lock();
        try{
            while(queue.size() - amount < 0){
                notEmpty.await();
            }
            for(int i = 0; i < amount; i++)
               queue.remove();
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }   finally {
            lock.unlock();
        }
    }
}
