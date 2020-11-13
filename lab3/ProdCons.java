package lab3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ProdCons {
    private int max;
    private int queue;
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    ProdCons(int size){
        this.max = size;
        this.queue = 0;
    }

    void put(Producer prod){
        lock.lock();
        try{
            while(queue + prod.amount > max) {
                if(!Source.work)
                    return;
                notFull.await();
            }
            queue += prod.amount;
            notEmpty.signalAll();
            prod.op++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void take(Consumer cons) {
        lock.lock();
        try{
            while(queue - cons.amount < 0){
                if(!Source.work)
                    return;
                notEmpty.await();
            }
            queue -= cons.amount;
            notFull.signalAll();
            cons.op++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }   finally {
            lock.unlock();
        }
    }

    void restartQueue() {
        this.queue = 0;
    }

    void endQueue() {
        Source.work = false;
        lock.lock();
            notFull.signalAll();
            notEmpty.signalAll();
        lock.unlock();
    }
}
