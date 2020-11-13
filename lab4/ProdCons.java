package lab4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProdCons {
    private int max;
    private int queue;
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition firstProd = lock.newCondition();
    private Condition restProd = lock.newCondition();
    private Condition firstKons = lock.newCondition();
    private Condition restKons = lock.newCondition();


    public ProdCons(int size){
        //this.queue = new LinkedList<>();
        this.max = size;
        this.queue = 0;
    }

    public void put(int amount, Producer prod){
        lock.lock();
        try{
            restProd.await();

            while( queue + amount > max) {
                if(!Source.work)
                    return;
                firstProd.await();
            }

            queue += amount;

            restProd.signal();
            firstKons.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void take(int amount, Consumer cons) {
        lock.lock();
        try{
            restKons.await();

            while(queue - amount < 0){
                if(!Source.work)
                    return;
                firstKons.await();
            }

            queue -= amount;

            restKons.signal();
            firstProd.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }   finally {
            lock.unlock();
        }
    }

    void startQueue(){
        lock.lock();
        restProd.signal();
        restKons.signal();
        lock.unlock();
    }

    void endQueue() {
        Source.work = false;

//        restProd.signalAll();
//        firstProd.signalAll();
//        restKons.signalAll();
//        firstKons.signalAll();


        while(Source.end_proc != 0 || Source.end_cons != 0){
            lock.lock();
            if(Source.end_cons > 0){
                restKons.signalAll();
                firstKons.signalAll();
            }
            if(Source.end_proc > 0) {
                restProd.signalAll();
                firstProd.signalAll();
            }
            lock.unlock();
        }
    }

    void restartQueue() {
        this.queue = 0;
    }
}
