import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author caiyuping
 * @date 2026/2/6 22:23
 * @description: 公平锁和非公平锁
 */

class Ticket{//资源类，模拟3个售票员卖完50张票
    private int number = 50;
    //ReentrantLock lock = new ReentrantLock();//非公平锁
    ReentrantLock lock = new ReentrantLock(true);//公平锁
    public void sale(){
        lock.lock();//获取锁
        try{
            if(number>0){
                System.out.println(Thread.currentThread().getName()+"卖出第："+(number--)+" 还剩下："+number);
            }
        }finally {
            lock.unlock();//释放锁
        }
    }
}

public class _05_FairAndUnfairLock {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        /**
         * 可以看出，使用的是非公平锁时，每个线程获取到资源的次数的机会是不同的（不公平）（先来先得）=====>可能导致线程饥饿
         * 使用公平锁时，每个线程获取到资源机会的概率是相同的（排队获取）
         */
        new Thread(()->{for (int i=0;i<55;i++){ticket.sale();}},"A").start();
        new Thread(()->{for (int i=0;i<55;i++){ticket.sale();}},"B").start();
        new Thread(()->{for (int i=0;i<55;i++){ticket.sale();}},"C").start();
    }
}

/**
 * 为什么会有公平锁和非公平锁的设计？
 * 为什么默认是非公平锁？
 *
 *  1. 恢复挂起的线程到真正锁的获取还是有时间差的，从开发人员的角度来说，这个时间不长，
 *  但是对于CPU来说，这个时间差还是比较明显，所以非公平锁能跟家充分的利用CPU的时间片，可以尽量减少CPU的空闲状态。
 *
 *  2. 使用多线程根重要的考量点就是线程的切换，
 *  当时使用非公平锁时，当一个线程请求锁获取同步状态，然后释放同步状态，所以刚释放的线程在此刻再获取锁的概率比较大。所以大大减低了线程的开销
 */
